import asyncio
import aiohttp
from bs4 import BeautifulSoup
from googletrans import Translator
import time
import random

# CONFIGURACIÓN
BASE_URL = "https://funko.com"
CATEGORIA_URL = f"{BASE_URL}/es/category/"
PRODUCTOS_POR_PAGINA = 20
CONCURRENCIA_MAXIMA = 10  # Número de peticiones simultáneas (ajústalo con cuidado)

# Instancia global del traductor
translator = Translator()

# Cabeceras rotativas básicas para evitar bloqueos simples
HEADERS = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
    "Accept-Language": "es-ES,es;q=0.9,en;q=0.8"
}


async def obtener_html(session, url):
    """Realiza la petición asíncrona y devuelve el objeto BeautifulSoup."""
    try:
        async with session.get(url, headers=HEADERS, timeout=15) as respuesta:
            respuesta.raise_for_status()
            html = await respuesta.text()
            return BeautifulSoup(html, "html.parser")
    except Exception as e:
        print(f"⚠️ Error obteniendo {url}: {e}")
        return None


async def traducir_texto(texto):
    """
    La traducción puede ser síncrona o asíncrona dependiendo de la versión de googletrans.
    Manejamos ambos casos para evitar el RuntimeWarning y asegurar la ejecución.
    """
    if not texto:
        return None

    try:
        # 1. Detectar si translator.translate es nativamente asíncrona (tu caso actual)
        if asyncio.iscoroutinefunction(translator.translate):
            traduccion = await translator.translate(texto, src="en", dest="es")
            return traduccion.text
        else:
            # 2. Si es síncrona, usar executor para no bloquear el bucle principal
            loop = asyncio.get_running_loop()
            traduccion = await loop.run_in_executor(None, lambda: translator.translate(texto, src="en", dest="es"))

            # 3. Defensa extra: si el executor devolvió una corutina (versión híbrida/confusa), la esperamos
            if asyncio.iscoroutine(traduccion):
                traduccion = await traduccion

            return traduccion.text
    except Exception:
        return texto  # Si falla la traducción, devolvemos el original


async def obtener_enlaces_productos(session):
    """Recorre la paginación para obtener todos los enlaces."""
    enlaces = []
    start = 0

    print("🔍 Buscando enlaces de productos...")

    while True:
        url = f"{CATEGORIA_URL}?prefn1=includedCountries&prefv1=ES&start={start}&sz={PRODUCTOS_POR_PAGINA}"
        soup = await obtener_html(session, url)

        if not soup:
            break

        productos = soup.find_all("div", class_="product")
        if not productos:
            break

        nuevos_enlaces = []
        for producto in productos:
            enlace_tag = producto.find("a", class_="image-link")
            if enlace_tag and enlace_tag.get("href"):
                link = enlace_tag["href"]
                if link.startswith("/"):
                    link = BASE_URL + link
                nuevos_enlaces.append(link)

        enlaces.extend(nuevos_enlaces)
        print(f"   ➡️ Página start={start}: {len(nuevos_enlaces)} productos encontrados.")

        start += PRODUCTOS_POR_PAGINA
        # Pequeña pausa asíncrona para no saturar en la fase de descubrimiento
        await asyncio.sleep(0.5)

    return enlaces


async def extraer_datos_funko(sem, session, url):
    """
    Extrae datos de un producto individual.
    El semáforo limita cuántas de estas funciones corren a la vez.
    """
    async with sem:  # Entra solo si hay hueco en el semáforo
        soup = await obtener_html(session, url)
        if not soup:
            return None

        funko = {
            "nombre": None,
            "precio": None,
            "descripcion": None,
            "foto_funko": None,
            "foto_caja": None,
            "coleccion": None,
            "box_number": None,
            "exclusivo": False,
            "url": url
        }

        # --- Extracción (Lógica idéntica a tu script original adaptada) ---

        # Nombre
        nombre_tag = soup.find("h1", class_="h2 product-name")
        if nombre_tag:
            funko["nombre"] = nombre_tag.get_text(strip=True)

        # Precio
        precio_tag = soup.select_one(".sales .value") or soup.select_one(".price .value")
        if precio_tag:
            precio = precio_tag.get("content")
            if not precio:
                precio = precio_tag.get_text(strip=True).replace("€", "").replace(",", ".").strip()
            funko["precio"] = precio

        # Colección
        coleccion_tag = soup.find("a", class_="product-license")
        if coleccion_tag:
            funko["coleccion"] = coleccion_tag.get_text(strip=True)

        # Box Number
        box_tag = soup.find("div", class_="product-boxNumber")
        if box_tag:
            funko["box_number"] = box_tag.get_text(strip=True).replace("Box Number:", "").strip()

        # Exclusivo
        exclusivo_tag = soup.find("div", class_="exclusive-flag")
        funko["exclusivo"] = bool(exclusivo_tag)

        # Fotos
        fotos = soup.find_all("img", class_="carousel-slide-image")
        if len(fotos) >= 1:
            funko["foto_funko"] = fotos[0].get("src")
        if len(fotos) >= 2:
            funko["foto_caja"] = fotos[1].get("src")

        # Descripción y Traducción
        desc_tag = soup.find("div", class_="long-description")
        if desc_tag:
            p_tag = desc_tag.find("p")
            texto_original = p_tag.get_text(" ", strip=True) if p_tag else desc_tag.get_text(" ", strip=True)

            # Llamamos a la función de traducción asíncrona
            funko["descripcion"] = await traducir_texto(texto_original)

        print(f"✅ Procesado: {funko['nombre'] or 'Desconocido'}")
        return funko


async def main():
    start_time = time.time()

    # ---------------------------------------------------------
    # FIX SSL: Ignorar verificación de certificados SSL
    # Necesario para evitar errores SSLCertVerificationError en local/macOS
    # ---------------------------------------------------------
    connector = aiohttp.TCPConnector(ssl=False)

    # Crear una sesión TCP persistente con el conector modificado
    async with aiohttp.ClientSession(connector=connector) as session:
        # 1. Obtener todos los enlaces (secuencial por página, pero rápido)
        enlaces = await obtener_enlaces_productos(session)
        print(f"\n🔗 Total enlaces encontrados: {len(enlaces)}")

        if not enlaces:
            print("❌ No se encontraron enlaces. Finalizando.")
            return

        print("🚀 Iniciando extracción masiva asíncrona...\n")

        # 2. Preparar tareas concurrentes
        sem = asyncio.Semaphore(CONCURRENCIA_MAXIMA)  # Controla el ritmo
        tareas = [extraer_datos_funko(sem, session, enlace) for enlace in enlaces]

        # 3. Ejecutar todo a la vez y esperar resultados
        resultados = await asyncio.gather(*tareas)

        # Filtrar nulos por errores
        resultados = [r for r in resultados if r is not None]

    duration = time.time() - start_time

    # Imprimir resumen
    print(f"\n🏁 Finalizado en {duration:.2f} segundos.")
    print(f"📦 Total Funkos extraídos: {len(resultados)}")

    # Mostrar muestra de 3 resultados
    for f in resultados[:3]:
        print(f"- {f['nombre']} ({f['precio']}€)")


if __name__ == "__main__":
    # Ejecuta el bucle de eventos (fix para Windows si es necesario)
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        print("\n🛑 Detenido por el usuario.")