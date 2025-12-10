import re

def corregir_espacios(texto):
    """
    Añade un espacio después de '.', '!' o '?' si no hay ya uno,
    evitando modificar números decimales.
    """
    # Espacio después de puntos, excluyendo decimales (ej. 4.2)
    texto = re.sub(r'(?<!\d)([.!?])(?!\s|\d)', r'\1 ', texto)
    # Limpiar dobles espacios
    texto = re.sub(r'\s{2,}', ' ', texto)
    return texto.strip()


def extraer_descripcion(soup, translator):
    """
    Extrae la descripción del Funko, aplicando reglas avanzadas
    para ignorar <p> irrelevantes y corregir espacios.
    """
    desc_div = soup.find("div", class_="long-description")
    if not desc_div:
        return None

    # Buscar <p> relevantes
    p_tags = desc_div.find_all("p")
    texto_final = None

    for p in p_tags:
        # Ignorar <p> vacíos o solo con <span>/<strong> irrelevantes
        texto = p.get_text(" ", strip=True)
        if not texto or texto == "":
            continue
        # Si tiene <i> o <em>, lo consideramos válido
        if p.find("i") or p.find("em"):
            texto_final = texto
            break

    # Si no encontramos <p> con <i> o <em>, tomar todo el texto del div
    if not texto_final:
        texto_final = desc_div.get_text(" ", strip=True)

    # Traducción
    try:
        traduccion = translator.translate(texto_final, src="en", dest="es")
        texto_traducido = traduccion.text
    except Exception:
        texto_traducido = texto_final

    # Corregir espacios después de signos de puntuación
    texto_traducido = corregir_espacios(texto_traducido)

    return texto_traducido
