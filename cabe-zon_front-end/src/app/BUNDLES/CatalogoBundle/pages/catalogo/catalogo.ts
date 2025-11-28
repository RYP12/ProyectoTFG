import {Component, OnInit} from '@angular/core';
import {ProductoService, Producto} from '../../../../SERVICES/productoService';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {FormsModule} from '@angular/forms';
import {FiltrosService} from '../../../../SERVICES/filtros-service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-catalogo',
  imports: [
    Header,
    Footer,
    FormsModule
  ],
  templateUrl: './catalogo.html',
  styleUrl: './catalogo.css',
})
export class Catalogo implements OnInit {
  listaProductos: Producto[] = [];

  // RECIBE LOS FILTROS DEL HTML
  filtros = {

    orden: '',
    rangoPrecio: '',
    colaboracion: ''

  };

  constructor(private productoService: ProductoService) { }

  // FUNCIONES QUE SE EJECUTAN AL INICIAR LA PAGINA
  ngOnInit() {
    this.cargarTodos();
  }
  // MUESTRA TODOS LOS PRODUCTOS
  cargarTodos() {
    this.productoService.obtenerProductos().subscribe({
      next: (datos) => this.listaProductos = datos,
      error: (err) => console.error(err)
    });
  }


  aplicarFiltros (){

    let busquedaActiva = false;
    let min: number = 0;
    let max: number = 10000;

    // SI HAY UN RANGO SELECCIONADO, DEFINIMOS MAXIMOS Y MINIMOS
    if (this.filtros.rangoPrecio !== '') {
      busquedaActiva = true;
      switch (this.filtros.rangoPrecio) {
        case '0€ - 25€': min = 0; max = 25; break;
        case '25€ - 50€': min = 25; max = 50; break;
        case '50€ - 1000€': min = 50; max = 1000; break;
        default: busquedaActiva = false; break;
      }
    }

    const parametrosBack = {
      orden: this.filtros.orden,
      minimo: min,
      maximo: max,
      colaboracion: this.filtros.colaboracion
    }



    console.log('Enviando al backend:', parametrosBack);

  }


  // METODO TEMPORAL PARA ARRANCAR EL PROYECTO
  protected agregarAlCarrito(funko: Producto) {

  }
}
