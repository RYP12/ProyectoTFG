import {Component, OnInit} from '@angular/core';
import {ProductoService, Producto} from '../../../../SERVICES/productoService';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-catalogo',
  imports: [
    Header,
    Footer,
    RouterLink
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

  ngOnInit() {
    this.productoService.obtenerProductos().subscribe({
      next: (datos) => {
        console.log('🔍 DATOS RECIBIDOS DEL BACKEND:', datos); // <--- MIRA LA CONSOLA DEL NAVEGADOR
        this.listaProductos = datos;
      },
      error: (err) => {
        console.log(err);
      }
    })
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

  }
}
