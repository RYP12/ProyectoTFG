import {Component, OnInit} from '@angular/core';
import {Producto, ProductoService} from '../../../../SERVICES/productoService';
import {Pedido, PedidoService} from '../../../../SERVICES/pedido-service';
import {ClienteService} from '../../../../SERVICES/cliente-service';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {CarritoService} from '../../../../SERVICES/carrito-service';
import {FormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {CommonModule} from '@angular/common';
import {Coleccion, ColeccionService} from '../../../../SERVICES/coleccion-service';
import {Observable, of} from 'rxjs';

@Component({
  selector: 'app-edicion-limitada',
  imports: [
    Header,
    Footer,
    FormsModule,
    CommonModule,
    RouterLink
  ],
  templateUrl: './edicion-limitada.html',
  styleUrl: './edicion-limitada.css',
})
export class EdicionLimitada implements OnInit {
  listaProductos: Producto[] = [];
  productosOriginales: Producto[] = [];
  colecciones: Coleccion[] = [];

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
    this.productoService.getExclusivos().subscribe({
      next: (datos) => {
        this.listaProductos = datos;
        this.productosOriginales = datos; // Hacemos una copia de seguridad
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

  aplicarFiltros() {
    // PASO 1: DEFINIR EL ORIGEN DE LOS DATOS (Pattern: Source Normalization)
    // Declaramos una variable que SIEMPRE será un Observable
    let fuenteDatos$: Observable<Producto[]>;

// --- FILTRO POR COLECCIÓN ---
    if (this.filtros.colaboracion != null && this.filtros.colaboracion !== '') {
      // Convertimos a número para evitar errores de tipos
      const idColeccionSeleccionada = Number(this.filtros.colaboracion);

      // Filtramos productos en memoria
      const filtrados = this.productosOriginales.filter(p => {
        // Validación: tiene colecciones
        if (!p.colecciones || p.colecciones.length === 0) return false;

        // Comprobamos si alguna colección coincide
        return p.colecciones.some(c => c.id === idColeccionSeleccionada);
      });

      fuenteDatos$ = of(filtrados);

    } else {
      // Si no hay filtro, tomamos todos los productos
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
