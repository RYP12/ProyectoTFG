import { Component, OnInit } from '@angular/core';
import { ProductoService, Producto } from '../../../../SERVICES/productoService';
import { Header } from '../../../../SHARED/header/header';
import { CommonModule } from '@angular/common';
import { Footer } from '../../../../SHARED/footer/footer';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Coleccion, ColeccionService } from '../../../../SERVICES/coleccion-service';
import { CarritoService } from '../../../../SERVICES/carrito-service';

@Component({
  selector: 'app-catalogo',
  imports: [
    Header,
    Footer,
    FormsModule,
    CommonModule,
    RouterLink
  ],
  templateUrl: './catalogo.html',
  styleUrl: './catalogo.css',
})
export class Catalogo implements OnInit {
  listaProductos: Producto[] = [];
  colecciones: Coleccion[] = [];

  private readonly PLACEHOLDER_IMG_URL: string = 'assets/img/placeholder.png';

  paginaActual: number = 0;
  esUltimaPagina: boolean = false;
  cargando: boolean = false;

  filtros = {
    orden: '',
    rangoPrecio: '',
    colaboracion: ''
  };

  constructor(private productoService: ProductoService,
              private carritoService: CarritoService,
              private coleccionService: ColeccionService) { }

  ngOnInit() {
    // 1. Carga inicial de productos
    this.cargarProductos();

    // 2. Carga de colecciones para el select
    this.coleccionService.obtenerColecciones().subscribe({
      next: (datos) => {
        this.colecciones = datos;
      },
      error: (err) => {
        console.error('Error al cargar colecciones:', err);
      }
    });
  }

  obtenerImagenUrl(funko: Producto, index: number): string {
    if (funko.imagenes && funko.imagenes.length > index && funko.imagenes[index].url) {
      return funko.imagenes[index].url;
    }
    return this.PLACEHOLDER_IMG_URL;
  }

  /**
   * Se ejecuta cuando el usuario cambia CUALQUIER filtro (Colección, Precio, Orden).
   * Estrategia: "Reset & Reload". Reiniciamos la paginación para aplicar los criterios desde cero.
   */
  aplicarFiltros() {
    this.cargarProductos(true);
  }

  /**
   * Carga productos del backend aplicando paginación y filtro de colección.
   * @param resetear Si es true, borra la lista actual y empieza desde la página 0.
   */
  cargarProductos(resetear: boolean = false) {
    if (this.cargando) return; // Evitar doble petición
    this.cargando = true;

    if (resetear) {
      this.paginaActual = 0;
      this.listaProductos = [];
    }

    // 1. Extraer ID de colección para enviarlo al Backend
    let coleccionId: number | undefined = undefined;
    if (this.filtros.colaboracion && this.filtros.colaboracion !== '') {
      coleccionId = parseInt(this.filtros.colaboracion);
    }

    // 2. Llamada al Servicio (Paginación + Filtro Server-Side)
    // Nota: Asegúrate de haber actualizado tu servicio para aceptar el 3er parámetro 'coleccionId'
    this.productoService.obtenerProductos(this.paginaActual, 20, coleccionId).subscribe({
      next: (respuesta: any) => {
        let nuevosProductos = respuesta.content;

        // 3. Procesamiento Local (Precio y Orden)
        // Aplicamos estos filtros a los datos que acaban de llegar

        // --- Filtro de Precio (Local) ---
        if (this.filtros.rangoPrecio) {
          const [minStr, maxStr] = this.filtros.rangoPrecio.split('-');
          const min = parseFloat(minStr);
          const max = parseFloat(maxStr);

          nuevosProductos = nuevosProductos.filter((p: Producto) =>
            p.precio !== undefined && p.precio >= min && p.precio <= max
          );
        }

        // 4. Actualizar la lista principal
        this.listaProductos = [...this.listaProductos, ...nuevosProductos];

        // --- Ordenación (Local Global) ---
        // Reordenamos TODA la lista (lo anterior + lo nuevo) para mantener coherencia visual
        if (this.filtros.orden) {
          this.listaProductos.sort((a, b) => {
            const precioA = a.precio || 0;
            const precioB = b.precio || 0;
            return this.filtros.orden === 'asc' ? precioA - precioB : precioB - precioA;
          });
        }

        this.esUltimaPagina = respuesta.last;
        this.cargando = false;
        console.log(`Cargados ${nuevosProductos.length} productos. Total: ${this.listaProductos.length}`);
      },
      error: (error) => {
        console.error('Error al cargar productos: ', error);
        this.cargando = false;
      }
    });
  }

  verMas() {
    if (!this.esUltimaPagina && !this.cargando) {
      this.paginaActual++;
      this.cargarProductos(false); // false = NO borrar lo anterior, solo añadir
    }
  }

  protected agregarAlCarrito(funko: Producto) {
    this.carritoService.agregarProducto(funko);
    alert('¡Funko añadido al carrito!');
  }
}
