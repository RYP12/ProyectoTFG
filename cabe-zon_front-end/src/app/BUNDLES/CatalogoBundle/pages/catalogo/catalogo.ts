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
  productosOriginales: Producto[] = [];
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
    // 1. CARGA INICIAL: Usamos solo la función de paginación
    this.cargarProductos();

    // 2. CARGA DE COLECCIONES (Para el filtro)
    this.coleccionService.obtenerColecciones().subscribe({
      next: (datos) => {
        this.colecciones = datos;
      },
      error: (err) => {
        console.log(err);
      }
    });

    // HE ELIMINADO EL BLOQUE CONFLICTIVO DE 'obtenerProductos().subscribe' AQUÍ
  }

  obtenerImagenUrl(funko: Producto, index: number): string {
    if (funko.imagenes && funko.imagenes.length > index && funko.imagenes[index].url) {
      return funko.imagenes[index].url;
    }
    return this.PLACEHOLDER_IMG_URL;
  }

  // IMPORTANTE: Este filtrado es local (Frontend).
  // Al usar paginación, solo filtrará lo que hayas cargado en pantalla hasta el momento.
  aplicarFiltros() {
    let fuenteDatos$: Observable<Producto[]>;

    if (this.filtros.colaboracion && this.filtros.colaboracion !== '') {
      const idColeccionSeleccionada = parseInt(this.filtros.colaboracion);

      const filtrados = this.productosOriginales.filter(p => {
        if (!p.colecciones|| p.colecciones.length === 0) {
          return false;
        }
        return p.colecciones.some(c => c.id === idColeccionSeleccionada);
      });

      fuenteDatos$ = of(filtrados);

    } else {
      fuenteDatos$ = of(this.productosOriginales);
    }

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

  cargarProductos(){
    this.cargando = true;

    // Asumimos que tu servicio ahora acepta la página como argumento
    this.productoService.obtenerProductos(this.paginaActual).subscribe({
      next: (respuesta: any) => { // Tipado 'any' temporal si no tienes interfaz de Paginación creada
        // Concatenamos lo nuevo
        this.listaProductos = [...this.listaProductos, ...respuesta.content];

        // ACTUALIZAMOS LA COPIA DE SEGURIDAD PARA LOS FILTROS
        // Ojo: Esto solo contiene lo que se ha visto hasta ahora
        this.productosOriginales = [...this.listaProductos];

        this.esUltimaPagina = respuesta.last;
        this.cargando = false;
        console.log("Productos cargados:", this.listaProductos);
      },
      error: (error) => {
        console.log('Error al cargar productos: ', error);
        this.cargando = false;
      }
    })
  }

  verMas(){
    if (!this.esUltimaPagina && !this.cargando) {
      this.paginaActual++;
      this.cargarProductos();
    }
  }
}
