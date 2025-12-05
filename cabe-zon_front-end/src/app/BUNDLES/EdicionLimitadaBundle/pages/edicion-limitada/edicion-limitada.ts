import {Component, OnInit} from '@angular/core';
import {Producto, ProductoService} from '../../../../SERVICES/productoService';
import {CarritoService} from '../../../../SERVICES/carrito-service';
import {Coleccion, ColeccionService} from '../../../../SERVICES/coleccion-service';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {FormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {CommonModule} from '@angular/common';
import {Observable, of} from 'rxjs';

@Component({
  selector: 'app-edicion-limitada',
  standalone: true, // Asegúrate de que esto coincide con tu configuración (imports indica standalone)
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
  // Listas de datos
  listaProductos: Producto[] = [];
  productosOriginales: Producto[] = []; // "Master" para los filtros
  colecciones: Coleccion[] = [];

  // Configuración de imagenes
  private readonly PLACEHOLDER_IMG_URL: string = 'assets/img/placeholder.png';

  // Variables de Paginación (Nuevas)
  paginaActual: number = 0;
  tamanyoPagina: number = 20;
  esUltimaPagina: boolean = false;
  cargando: boolean = false;

  // Filtros del HTML
  filtros = {
    orden: '',
    rangoPrecio: '',
    colaboracion: ''
  };

  constructor(private productoService: ProductoService,
              private carritoService: CarritoService,
              private coleccionService: ColeccionService) { }

  async ngOnInit() {
    // 1. CARGA INICIAL PAGINADA DE EXCLUSIVOS
    this.cargarProductos();

    // 2. CARGAR SOLO COLECCIONES CON EXCLUSIVAS
    this.coleccionService.obtenerColeccionesExclusivas().subscribe({
      next: (datos) => {
        this.colecciones = datos;
        console.log("Colecciones VIP cargadas:", datos.length);
      },
      error: (err) => {
        console.error('Error al cargar colecciones:', err);
      }
    });
  }

  // --- LÓGICA DE PAGINACIÓN ADAPTADA ---

  cargarProductos() {
    if (this.cargando) return;
    this.cargando = true;

    // NOTA: Asegúrate de que tu servicio tenga este método aceptando paginación.
    // Si tu backend '/exclusivo' aun devuelve una List<> simple,
    // deberás actualizar el Controller para devolver Page<> o slicear aquí (no recomendado).
    this.productoService.getExclusivos(this.paginaActual, this.tamanyoPagina).subscribe({
      next: (respuesta: any) => {
        const nuevosProductos = respuesta.content; // Asumiendo estructura Spring Page
        this.esUltimaPagina = respuesta.last;

        // Añadimos a la lista visible
        this.listaProductos.push(...nuevosProductos);

        // IMPORTANTE: Añadimos también a la lista "backup" para que los filtros funcionen
        // sobre los nuevos productos cargados
        this.productosOriginales.push(...nuevosProductos);

        this.cargando = false;
        console.log(`Cargados ${nuevosProductos.length} productos exclusivos. Pagina: ${this.paginaActual}`);
      },
      error: (error) => {
        console.error('Error al cargar productos exclusivos: ', error);
        this.cargando = false;
      }
    });
  }

  // BOTON PARA AMPLIAR LA PAGINA CON LOS SIGUIENTES 20
  verMas() {
    if (!this.esUltimaPagina) {
      this.paginaActual++;
      this.cargarProductos();
    }
  }

  // --- LÓGICA DE VISTA Y FILTROS ---

  obtenerImagenUrl(funko: Producto, index: number): string {
    if (funko.imagenes && funko.imagenes.length > index && funko.imagenes[index].url) {
      return funko.imagenes[index].url;
    }
    return this.PLACEHOLDER_IMG_URL;
  }

  aplicarFiltros() {
    // 1. Origen: Usamos productosOriginales que contiene TODO lo cargado hasta ahora
    let fuenteDatos$: Observable<Producto[]>;

    if (this.filtros.colaboracion && this.filtros.colaboracion !== '') {
      const idColeccionSeleccionada = parseInt(this.filtros.colaboracion);

      const filtrados = this.productosOriginales.filter(p => {
        if (!p.colecciones || p.colecciones.length === 0) {
          return false;
        }
        return p.colecciones.some(c => c.id === idColeccionSeleccionada);
      });

      fuenteDatos$ = of(filtrados);
    } else {
      fuenteDatos$ = of(this.productosOriginales);
    }

    // 2. Procesamiento
    fuenteDatos$.subscribe({
      next: (productosBase) => {
        let resultado = [...productosBase];

        // Filtro Precio
        if (this.filtros.rangoPrecio) {
          const [minStr, maxStr] = this.filtros.rangoPrecio.split('-');
          const min = parseFloat(minStr);
          const max = parseFloat(maxStr);

          resultado = resultado.filter(p =>
            p.precio !== undefined && p.precio >= min && p.precio <= max
          );
        }

        // Filtro Orden
        if (this.filtros.orden) {
          resultado.sort((a, b) => {
            const precioA = a.precio || 0;
            const precioB = b.precio || 0;
            return this.filtros.orden === 'asc' ? precioA - precioB : precioB - precioA;
          });
        }

        // 3. Actualización
        this.listaProductos = resultado;
      },
      error: (err) => {
        console.error('Error al aplicar filtros:', err);
      }
    });
  }

  protected agregarAlCarrito(funko: Producto) {
    this.carritoService.agregarProducto(funko);
    alert('¡Funko añadido al carrito!');
  }
}

