import asyncio
import aiohttp
from bs4 import BeautifulSoup
from googletrans import Translator
import time
from db_funko import init_db_pool, save_funko, close_db_pool
from extraer_descripcion import *
import re
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


async def obtener_enlaces_productos(session):
    """Recorre la paginación para obtener todos los enlaces."""
    enlaces = []
    start = 0
    LIMITE = 500

    print("🔍 Buscando enlaces de productos...")

    while len(enlaces) < LIMITE:
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

        # --- nombre ---
        nombre_tag = soup.find("h1", class_="h2 product-name")
        if nombre_tag:
            funko["nombre"] = nombre_tag.get_text(strip=True)

        # --- precio ---
        precio = None

        # Primero intentamos obtener el precio rebajado (si existe)
        precio_tag = soup.select_one(".sales .value")

        # Si no hay descuento, tomamos el precio normal
        if not precio_tag:
            precio_tag = soup.select_one(".price .value")

        if precio_tag:
            # Intentamos obtener el valor del atributo 'content'
            precio = precio_tag.get("content")

            # Si el contenido no existe o es 'null', usamos el texto visible
            if not precio or precio.lower() == "null":
                precio = precio_tag.get_text(strip=True).replace("€", "").replace(",", ".").strip()

        # Validar y convertir a número restando 0.01 solo si es posible
        try:
            precio = float(precio) - 0.01
        except (ValueError, TypeError):
            precio = 17.99

        funko["precio"] = precio

        # --- descripcion ---
        funko["descripcion"] = extraer_descripcion(soup, translator)

        # --- fotos ---
        fotos = soup.find_all("img", class_="carousel-slide-image")
        if len(fotos) >= 1:
            funko["foto_funko"] = fotos[0].get("src")
        if len(fotos) >= 2:
            funko["foto_caja"] = fotos[1].get("src")

        # --- colección ---
        coleccion_tag = soup.find("a", class_="product-license product-license-style text-uppercase")
        if coleccion_tag:
            funko["coleccion"] = coleccion_tag.get_text(strip=True)

        # --- box_number ---
        box_tag = soup.find("div", class_="product-boxNumber mt-2")
        if box_tag:
            funko["box_number"] = box_tag.get_text(strip=True).replace("Box Number:", "").strip()
        else:
            # Si no se encuentra, buscamos dentro de product-id
            product_id_tag = soup.find("div", class_="product-id")
            if product_id_tag:
                id_span = product_id_tag.find("span", class_="id")
                if id_span:
                    funko["box_number"] = id_span.get_text(strip=True)

        # --- exclusivo ---
        exclusivo_tag = soup.find("div", class_=re.compile(r"(exclusive-flag|ultra-flag|web-exclusive-flag|grail-flag|legendary-flag)"))
        funko["exclusivo"] = True if exclusivo_tag else False

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
    for funko in resultados:
        print("\n🧸 FUNKO ------------------------")
        for clave, valor in funko.items():
            print(f"{clave}: {valor}")

    resultados = [r for r in resultados if r is not None]

    pool = await init_db_pool()
    guardados = 0
    for funko in resultados:
        try:
            prod_id = await save_funko(funko)
            print(f"💾 Guardado producto {prod_id}: {funko.get('nombre')}")
            guardados += 1
        except Exception as e:
            print(f"⚠️ Error guardando {funko.get('nombre')}: {e}")
    await close_db_pool()

    print(f"\n✅ Guardados {guardados} funkos correctamente.")


if __name__ == "__main__":
    # Ejecuta el bucle de eventos (fix para Windows si es necesario)
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        print("\n🛑 Detenido por el usuario.")