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
    // Carga inicial de productos
    this.cargarProductos();

    // Carga de colecciones para el select
    this.coleccionService.obtenerColecciones().subscribe({
      next: (datos) => {
        this.colecciones = datos;
      },
      error: (err) => {
        console.error('Error al cargar colecciones:', err);
      }
    });
  }


  obtenerImagenUrl(funko: Producto, index: number = 0): string {

    // funko.imagenes?.[index] -> Intenta acceder al array y al índice. Si no existe, devuelve undefined.
    // Si el objeto imagen existe, intenta acceder a la propiedad url.
    // Si todo lo anterior resulta en null/undefined, devuelve el placeholder.
    return funko.imagenes?.[index]?.url ?? this.PLACEHOLDER_IMG_URL;
  }

  aplicarFiltros() {
    this.cargarProductos(true);
  }

  cargarProductos(resetear: boolean = false) {
    if (this.cargando) return; // Evitar doble petición
    this.cargando = true;

    if (resetear) {
      this.paginaActual = 0;
      this.listaProductos = [];
    }

    // Extraer ID de colección para enviarlo al Backend
    let coleccionId: number | undefined = undefined;
    if (this.filtros.colaboracion && this.filtros.colaboracion !== '') {
      coleccionId = parseInt(this.filtros.colaboracion);
    }

    // Llamada al Servicio
    this.productoService.obtenerProductos(this.paginaActual, 20, coleccionId).subscribe({
      next: (respuesta: any) => {
        let nuevosProductos = respuesta.content;

        // Aplicamos estos filtros a los datos que acaban de llegar

        if (this.filtros.rangoPrecio) {
          const [minStr, maxStr] = this.filtros.rangoPrecio.split('-');
          const min = parseFloat(minStr);
          const max = parseFloat(maxStr);

          nuevosProductos = nuevosProductos.filter((p: Producto) =>
            p.precio !== undefined && p.precio >= min && p.precio <= max
          );
        }

        // Actualizar la lista principal
        this.listaProductos = [...this.listaProductos, ...nuevosProductos];

        // Reordenamos TODA la lista (lo anterior + lo nuevo)
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
