import asyncio
import aiohttp
from bs4 import BeautifulSoup
import time

# CONFIGURACIÓN
BASE_URL = "https://funko.com"
CATEGORIA_URL = f"{BASE_URL}/es/category/"
PRODUCTOS_POR_PAGINA = 20
# *** MODIFICACIÓN 1: Definir el límite máximo de productos ***
LIMITE_PRODUCTOS = 50
CONCURRENCIA_MAXIMA = 5
TIMEOUT_SEGUNDOS = 15
RETRASO_PAGINACION_SEGUNDOS = 0.5

# Cabeceras rotativas básicas para evitar bloqueos simples
HEADERS = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
    "Accept-Language": "es-ES,es;q=0.9,en;q=0.8"
}


# --- FUNCIONES CORE ---

async def obtener_html(session: aiohttp.ClientSession, url: str) -> BeautifulSoup | None:
    """Realiza la petición asíncrona y devuelve el objeto BeautifulSoup."""
    try:
        # Uso de la cabecera rotativa
        async with session.get(url, headers=HEADERS, timeout=TIMEOUT_SEGUNDOS) as respuesta:
            respuesta.raise_for_status()  # Lanza excepción para códigos 4xx/5xx
            html = await respuesta.text()
            return BeautifulSoup(html, "html.parser")
    except aiohttp.ClientError as e:
        print(f"Error de cliente HTTP en {url}: {e}")
    except Exception as e:
        print(f"Error inesperado obteniendo {url}: {e}")
    return None


async def traducir_texto(session: aiohttp.ClientSession, texto: str) -> str | None:
    """
    Simula la traducción utilizando un servicio público.
    """
    if not texto:
        return None

    # --- SIMULACIÓN REALISTA: Se devuelve el original para no depender de librerías no oficiales ---
    await asyncio.sleep(0.01)  # Simula una pequeña latencia asíncrona

    return f"[TRADUCIDO]: {texto}"  # Devuelve el texto marcado como 'traducido'


async def obtener_enlaces_productos(session: aiohttp.ClientSession) -> list[str]:
    """Recorre la paginación para obtener todos los enlaces de producto, limitado por LIMITE_PRODUCTOS."""
    enlaces = []
    start = 0

    print("Buscando enlaces de productos...")

    while True:
        # *** MODIFICACIÓN 2: Límite de parada anticipada para no procesar más páginas de las necesarias ***
        # Calculamos cuántos productos debemos pedir en la siguiente página.
        productos_restantes = LIMITE_PRODUCTOS - len(enlaces)

        if productos_restantes <= 0:
            print(f"  Se alcanzó el límite de {LIMITE_PRODUCTOS} productos.")
            break

        # Pedimos el mínimo entre el tamaño por página configurado y los que quedan por alcanzar el límite
        sz_actual = min(PRODUCTOS_POR_PAGINA, productos_restantes)

        url = f"{CATEGORIA_URL}?prefn1=includedCountries&prefv1=ES&start={start}&sz={sz_actual}"
        soup = await obtener_html(session, url)

        if not soup:
            print("  Se alcanzó el final o hubo un error en la paginación.")
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

        # *** MODIFICACIÓN 3: Asegurar que no se añade más del límite. ***
        # Si la última página trae más de los necesarios para llegar al límite (e.g., quedan 5 y trae 20),
        # solo cogemos los que faltan.
        enlaces_a_añadir = nuevos_enlaces[:productos_restantes]
        enlaces.extend(enlaces_a_añadir)

        print(f"  Página start={start}: {len(enlaces_a_añadir)} productos añadidos. Total: {len(enlaces)}")

        # Si después de añadir, el total llega o supera el límite, paramos.
        if len(enlaces) >= LIMITE_PRODUCTOS:
            print(f"  Se alcanzó el límite de {LIMITE_PRODUCTOS} productos.")
            break

        start += PRODUCTOS_POR_PAGINA

        # Pausa asíncrona obligatoria para no saturar al servidor en la fase de descubrimiento
        await asyncio.sleep(RETRASO_PAGINACION_SEGUNDOS)

    return enlaces


async def extraer_datos_funko(sem: asyncio.Semaphore, session: aiohttp.ClientSession, url: str) -> dict | None:
    """
    Extrae datos de un producto individual, limitado por el semáforo.
    """
    async with sem:  # Limita la concurrencia
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
        }

        # --- Extracción de datos ---

        # Nombre
        nombre_tag = soup.find("h1", class_="h2 product-name")
        if nombre_tag:
            funko["nombre"] = nombre_tag.get_text(strip=True)

        # Precio (Mejora: Maneja 'content' y texto)
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

            # Llamada a la función de traducción (simulada o real si se integra API)
            funko["descripcion"] = await traducir_texto(session, texto_original)

        print(f"Procesado: {funko['nombre'] or 'Desconocido'}")
        return funko


async def main():
    start_time = time.time()

    # FIX SSL: Ignorar verificación de certificados SSL (es una buena práctica para pruebas locales)
    connector = aiohttp.TCPConnector(ssl=False)

    # Crear una sesión TCP persistente con el conector modificado
    async with aiohttp.ClientSession(connector=connector) as session:
        # 1. Obtener todos los enlaces (secuencial por página, con pausa)
        enlaces = await obtener_enlaces_productos(session)
        # *** MODIFICACIÓN 4: Si se supera accidentalmente el límite, se trunca aquí ***
        enlaces = enlaces[:LIMITE_PRODUCTOS]
        print(f"\nTotal enlaces a procesar: {len(enlaces)}")

        if not enlaces:
            print("No se encontraron enlaces. Finalizando.")
            return

        print("Iniciando extracción masiva asíncrona...")

        # 2. Preparar tareas concurrentes con semáforo
        sem = asyncio.Semaphore(CONCURRENCIA_MAXIMA)  # Controla el ritmo
        tareas = [extraer_datos_funko(sem, session, enlace) for enlace in enlaces]

        # 3. Ejecutar todo a la vez y esperar resultados
        resultados_con_errores = await asyncio.gather(*tareas, return_exceptions=True)

        # Filtrar resultados (quedan solo dicts, descartando Exceptions)
        resultados = [r for r in resultados_con_errores if isinstance(r, dict)]
        errores_count = len(resultados_con_errores) - len(resultados)

    duration = time.time() - start_time

    # Imprimir resumen
    print("\n--- RESUMEN ---")
    print(f"Tiempo total: {duration:.2f} segundos.")
    print(f"Total Funkos extraídos: {len(resultados)}")
    if errores_count > 0:
        print(f"⚠️ Atención: {errores_count} errores de extracción (saltados).")

    print("\n--- MUESTRA (3 primeros) ---")
    for f in resultados[:3]:
        print(f"- {f['nombre'] or 'Desconocido'} ({f['precio']}€)")


if __name__ == "__main__":
    try:
        # Uso estándar de asyncio.run()
        asyncio.run(main())
    except KeyboardInterrupt:
        print("\nDetenido por el usuario.")