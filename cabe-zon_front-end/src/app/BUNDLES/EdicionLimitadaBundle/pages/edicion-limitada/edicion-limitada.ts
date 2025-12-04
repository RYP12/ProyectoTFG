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
    // 1. CARGAR SOLO PRODUCTOS EXCLUSIVOS
    // (Asegúrate de que este método en el servicio llame a un endpoint que filtre por 'exclusivo = true')
    this.productoService.getExclusivos().subscribe({
      next: (datos) => {
        this.listaProductos = datos;
        this.productosOriginales = datos; // La copia de seguridad solo contiene exclusivas
        console.log("Productos Exclusivos cargados:", datos.length);
      },
      error: (err) => {
        console.log(err);
      }
    });

    // 2. CARGAR SOLO COLECCIONES CON EXCLUSIVAS (¡CAMBIO IMPORTANTE!)
    // Usamos el método nuevo que creamos antes para que el desplegable
    // solo muestre opciones que darán resultados.
    this.coleccionService.obtenerColeccionesExclusivas().subscribe({
      next: (datos) => {
        this.colecciones = datos;
        console.log("Colecciones VIP cargadas:", datos.length);
      },
      error: (err) => {
        console.log(err);
      }
    });
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
    // PASO 1: DEFINIR EL ORIGEN DE LOS DATOS
    let fuenteDatos$: Observable<Producto[]>;

    // Verificamos si el usuario seleccionó una colección
    if (this.filtros.colaboracion && this.filtros.colaboracion !== '') {
      const idColeccionSeleccionada = parseInt(this.filtros.colaboracion);

      // Filtramos en memoria buscando dentro de la LISTA de colecciones del producto
      const filtrados = this.productosOriginales.filter(p => {
        if (!p.colecciones || p.colecciones.length === 0) {
          return false;
        }
        return p.colecciones.some(c => c.id === idColeccionSeleccionada);
      });

      fuenteDatos$ = of(filtrados);

    } else {
      // Si no hay filtro de colección, la fuente son todos los originales (que ya son solo exclusivas)
      fuenteDatos$ = of(this.productosOriginales);
    }

    // PASO 2: SUSCRIBIRSE Y PROCESAR
    fuenteDatos$.subscribe({
      next: (productosBase) => {
        let resultado = [...productosBase];

        // --- FILTRO 1: RANGO DE PRECIO ---
        if (this.filtros.rangoPrecio) {
          const [minStr, maxStr] = this.filtros.rangoPrecio.split('-');
          const min = parseFloat(minStr);
          const max = parseFloat(maxStr);

          resultado = resultado.filter(p =>
            p.precio !== undefined && p.precio >= min && p.precio <= max
          );
        }

        // --- FILTRO 2: ORDENACIÓN ---
        if (this.filtros.orden) {
          resultado.sort((a, b) => {
            const precioA = a.precio || 0;
            const precioB = b.precio || 0;
            return this.filtros.orden === 'asc' ? precioA - precioB : precioB - precioA;
          });
        }

        // PASO 3: ACTUALIZAR LA VISTA
        this.listaProductos = resultado;
      },
      error: (err) => {
        console.error('Error al aplicar filtros:', err);
      }
    });
  }

  protected agregarAlCarrito(funko: Producto) {
    this.carritoService.agregarProducto(funko);
    // Idealmente usa un Toast o notificación en lugar de alert
    alert('¡Funko añadido al carrito!');
  }
}
