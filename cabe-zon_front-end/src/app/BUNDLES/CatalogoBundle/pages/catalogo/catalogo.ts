import {Component, OnInit} from '@angular/core';
import {ProductoService, Producto} from '../../../../SERVICES/productoService';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {CarritoService} from '../../../../SERVICES/carrito-service';
import {FormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-catalogo',
  imports: [
    Header,
    Footer,
    FormsModule,
    RouterLink
  ],
  templateUrl: './catalogo.html',
  styleUrl: './catalogo.css',
})
export class Catalogo implements OnInit {
  listaProductos: Producto[] = [];

  paginaActual: number = 0;
  esUltimaPagina: boolean = false;
  cargando: boolean = false;

  // RECIBE LOS FILTROS DEL HTML
  filtros = {

    orden: '',
    rangoPrecio: '',
    colaboracion: ''

  };

  constructor(private productoService: ProductoService,
              private carritoService: CarritoService) { }

  ngOnInit() {
    this.cargarProductos();
  }

  aplicarFiltros (){

    // RANGOS DE PRECIO

    let minimo: number | undefined;
    let maximo: number | undefined;

    switch (this.filtros.rangoPrecio) {
      case '0€ - 25€':
        minimo = 0;
        maximo = 25;
        break;
      case '25€ - 50€':
        minimo = 25;
        maximo = 50;
        break;
      case '50€ - 1000€':
        minimo = 50;
        maximo = 1000;
        break;

      default:
        // Si no se selecciona nada se queda 'undefined' los valores
        break;
    }

    const parametrosBack = {
      orden: this.filtros.orden,
      minimo: minimo,
      maximo: maximo,
      colaboracion: this.filtros.colaboracion
    }

    console.log('Enviando al backend:', parametrosBack);
  }
  // METODO TEMPORAL PARA ARRANCAR EL PROYECTO
  protected agregarAlCarrito(funko: Producto) {
    this.carritoService.agregarProducto(funko);
    alert('¡Funko añadido al carrito!');
  }

  cargarProductos(){
    this.cargando = true;
    // Llamamos al servicio pasando la pagina actual
    this.productoService.obtenerProductos(this.paginaActual).subscribe({
      next: (respuesta) => {
        // Concatenamos lo que ya teniamos con lo nuevo
        this.listaProductos = [...this.listaProductos, ...respuesta.content];
        // Actualizamos si es la ultima pagina par esconder el boton
        this.esUltimaPagina = respuesta.last;
        this.cargando = false;
        console.log(this.listaProductos);
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
