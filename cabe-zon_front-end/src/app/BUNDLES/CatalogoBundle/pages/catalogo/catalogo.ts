import {Component, OnInit} from '@angular/core';
import {ProductoService, Producto} from '../../../../SERVICES/productoService';
import {Header} from '../../../../SHARED/header/header';
import { CommonModule } from '@angular/common';
import {Footer} from '../../../../SHARED/footer/footer';
import {FormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {Coleccion, ColeccionService} from '../../../../SERVICES/coleccion-service';
import {CarritoService} from '../../../../SERVICES/carrito-service';
import {Observable, of} from 'rxjs';

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
  productosOriginales: Producto[] = []; // Guardamos la lista original
  colecciones: Coleccion[] = [];

  // URL de reserva si el producto no tiene imagen (ajusta la ruta si es necesario)
  private readonly PLACEHOLDER_IMG_URL: string = 'assets/img/placeholder.png';

  // RECIBE LOS FILTROS DEL HTML
  filtros = {

    orden: '',
    rangoPrecio: '',
    colaboracion: ''

  };

  constructor(private productoService: ProductoService,
              private carritoService: CarritoService,
              private coleccionService: ColeccionService) { }

  ngOnInit() {
    this.productoService.obtenerProductos().subscribe({
      next: (datos) => {
        this.listaProductos = datos;
        this.productosOriginales = datos; // Hacemos una copia de seguridad
        console.log(this.listaProductos);
      },
      error: (err) => {
        console.log(err);
      }
    })

    this.coleccionService.obtenerColecciones().subscribe({
      next: (datos) => {
        this.colecciones = datos;
      },
      error: (err) => {
        console.log(err);
      }
    })
  }


  obtenerImagenUrl(funko: Producto, index: number): string {
    // 1. Verifica si el array 'imagenes' existe
    // 2. Verifica si el array es lo suficientemente largo para el índice solicitado
    // 3. Verifica si el elemento en ese índice tiene una 'url'
    if (funko.imagenes && funko.imagenes.length > index && funko.imagenes[index].url) {
      return funko.imagenes[index].url;
    }
    // Si falla, devuelve la URL de reserva
    return this.PLACEHOLDER_IMG_URL;
  }

  aplicarFiltros() {
    // PASO 1: DEFINIR EL ORIGEN DE LOS DATOS (Pattern: Source Normalization)
    // Declaramos una variable que SIEMPRE será un Observable
    let fuenteDatos$: Observable<Producto[]>;

    // Verificamos si el usuario seleccionó una colección
    if (this.filtros.colaboracion && this.filtros.colaboracion !== '') {
      const idColeccionSeleccionada = parseInt(this.filtros.colaboracion);

      // --- CAMBIO CLAVE: Lógica para Many-to-Many ---
      // Filtramos en memoria buscando dentro de la LISTA de colecciones del producto
      const filtrados = this.productosOriginales.filter(p => {
        // Validación de seguridad: ¿Tiene lista de colecciones?
        if (!p.colecciones|| p.colecciones.length === 0) {
          return false; // Si no tiene colecciones, no pasa el filtro
        }

        // .some() devuelve true si AL MENOS UNA colección coincide con el ID buscado
        return p.colecciones.some(c => c.id === idColeccionSeleccionada);
      });

      // Envolvemos el resultado (vacío o con datos) en un Observable
      fuenteDatos$ = of(filtrados);

    } else {
      // Si no hay filtro de colección, la fuente son todos los originales
      fuenteDatos$ = of(this.productosOriginales);
    }

    // PASO 2: SUSCRIBIRSE Y PROCESAR (Pipeline unificado)

    fuenteDatos$.subscribe({
      next: (productosBase) => {
        // Trabajamos con una copia para inmutabilidad
        let resultado = [...productosBase];

        // --- FILTRO 1: RANGO DE PRECIO (Local) ---
        if (this.filtros.rangoPrecio) {
          const [minStr, maxStr] = this.filtros.rangoPrecio.split('-');
          const min = parseFloat(minStr);
          const max = parseFloat(maxStr);

          resultado = resultado.filter(p =>
            p.precio !== undefined && p.precio >= min && p.precio <= max
          );
        }

        // --- FILTRO 2: ORDENACIÓN (Local) ---
        if (this.filtros.orden) {
          resultado.sort((a, b) => {
            const precioA = a.precio || 0;
            const precioB = b.precio || 0;
            return this.filtros.orden === 'asc' ? precioA - precioB : precioB - precioA;
          });
        }

        // PASO 3: ACTUALIZAR LA VISTA
        this.listaProductos = resultado;
        console.log(`Filtros aplicados. Mostrando ${resultado.length} productos.`);
      },
      error: (err) => {
        console.error('Error al aplicar filtros:', err);
      }
    });
  }
  // METODO TEMPORAL PARA ARRANCAR EL PROYECTO
  protected agregarAlCarrito(funko: Producto) {
    this.carritoService.agregarProducto(funko);
    alert('¡Funko añadido al carrito!');
  }

}
