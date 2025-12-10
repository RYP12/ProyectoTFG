import asyncpg
from decimal import Decimal, InvalidOperation
import os
import re

# =========================================================
# CONFIGURACIÓN DE LA BASE DE DATOS
# =========================================================
DB_USER = os.getenv("DB_USER", "daw")
DB_PASS = os.getenv("DB_PASS", "2daw")
DB_HOST = os.getenv("DB_HOST", "serverye.ddns.net")
DB_PORT = os.getenv("DB_PORT", "5009")
DB_NAME = os.getenv("DB_NAME", "cabezon")

DB_DSN = f"postgresql://{DB_USER}:{DB_PASS}@{DB_HOST}:{DB_PORT}/{DB_NAME}"

pool = None  # se inicializa en init_db_pool()


# =========================================================
# FUNCIONES AUXILIARES
# =========================================================

def parse_price_to_decimal(precio_raw):
    """Convierte un precio tipo '€15.99' o '15,99 €' a Decimal."""
    if not precio_raw:
        return None
    s = re.sub(r"[^\d.,-]", "", str(precio_raw)).strip()
    if not s:
        return None
    s = s.replace(",", ".")
    if s.count(".") > 1:
        parts = s.split(".")
        s = "".join(parts[:-1]) + "." + parts[-1]
    try:
        return Decimal(s)
    except InvalidOperation:
        return None


def parse_box_number(box_raw):
    """Extrae el número del código de caja si existe."""
    if not box_raw:
        return None
    digits = re.search(r"(\d+)", str(box_raw))
    return int(digits.group(1)) if digits else None


# =========================================================
# FUNCIONES DE CONEXIÓN
# =========================================================

async def init_db_pool():
    """Inicializa el pool de conexiones asyncpg."""
    global pool
    pool = await asyncpg.create_pool(dsn=DB_DSN, min_size=1, max_size=5)
    print("✅ Pool de base de datos inicializado.")
    return pool


async def close_db_pool():
    """Cierra el pool de conexiones."""
    global pool
    if pool:
        await pool.close()
        print("🧹 Pool cerrado.")


# =========================================================
# FUNCIÓN PRINCIPAL: GUARDAR FUNKO EN LA BD
# =========================================================

async def save_funko(funko):
    """
    Inserta producto, imágenes y colecciones relacionadas en la BD.
    funko: dict con claves:
        - nombre
        - precio
        - descripcion
        - box_number
        - exclusivo
        - foto_funko
        - foto_caja
        - coleccion
    """
    global pool
    if pool is None:
        raise RuntimeError("❌ Pool no inicializado. Llama a init_db_pool() antes.")

    async with pool.acquire() as conn:
        async with conn.transaction():
            precio_dec = parse_price_to_decimal(funko.get("precio"))
            codigo_producto = parse_box_number(funko.get("box_number"))
            nombre = funko.get("nombre") or "SIN_NOMBRE"
            descripcion = funko.get("descripcion")

            # ================================
            # 1️⃣ Insertar producto
            # ================================
            producto_id = await conn.fetchval(
                """
                INSERT INTO cabezon.producto 
                    (nombre, descripcion, precio, codigo_producto, exclusivo, stock)
                VALUES ($1, $2, $3, $4, $5, 25)
                RETURNING id
                """,
                nombre, descripcion, precio_dec, codigo_producto, bool(funko.get("exclusivo", False))
            )

            # ================================
            # 2️⃣ Insertar imágenes
            # ================================
            if funko.get("foto_funko"):
                await conn.execute(
                    "INSERT INTO cabezon.imagen (nombre, url, id_producto) VALUES ($1, $2, $3)",
                    f"Foto Funko {nombre}", funko["foto_funko"], producto_id
                )

            if funko.get("foto_caja"):
                await conn.execute(
                    "INSERT INTO cabezon.imagen (nombre, url, id_producto) VALUES ($1, $2, $3)",
                    f"Foto Caja {nombre}", funko["foto_caja"], producto_id
                )

            # ================================
            # 3️⃣ Colección (crear si no existe)
            # ================================
            coleccion_nombre = funko.get("coleccion")
            if coleccion_nombre:
                coleccion_nombre = coleccion_nombre.strip()
                coleccion_id = await conn.fetchval(
                    "SELECT id FROM cabezon.coleccion WHERE nombre = $1 LIMIT 1",
                    coleccion_nombre
                )
                if not coleccion_id:
                    coleccion_id = await conn.fetchval(
                        "INSERT INTO cabezon.coleccion (nombre, numero_de_productos) VALUES ($1, 0) RETURNING id",
                        coleccion_nombre
                    )

                await conn.execute(
                    """
                    INSERT INTO cabezon.coleccion_producto (id_coleccion, id_producto)
                    VALUES ($1, $2)
                    ON CONFLICT DO NOTHING
                    """,
                    coleccion_id, producto_id
                )

                # Actualizar numero_de_productos de la coleccion:
                await conn.execute(
                 """
                 UPDATE cabezon.coleccion
                 SET numero_de_productos = (
                   SELECT COUNT(*) FROM cabezon.coleccion_producto cp WHERE cp.id_coleccion = cabezon.coleccion.id
                 )
                 WHERE id = $1
                 """,
                 coleccion_id
                )

            return producto_id