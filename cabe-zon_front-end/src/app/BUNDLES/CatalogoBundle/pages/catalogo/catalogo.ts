import {Component, OnInit} from '@angular/core';
import {ProductoService, Producto} from '../../../../SERVICES/productoService';
import {Header} from '../../../../SHARED/header/header';
import { CommonModule } from '@angular/common';
import {Footer} from '../../../../SHARED/footer/footer';
import {FormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';

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
        this.listaProductos = datos;
        this.productosOriginales = datos; // Hacemos una copia de seguridad
        console.log(this.listaProductos);
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  aplicarFiltros (){
    // Empezamos con la lista original para aplicar filtros
    let productosFiltrados = [...this.productosOriginales];

    // 1. FILTRO POR RANGO DE PRECIO (CLIENTE)
    if (this.filtros.rangoPrecio) {
      const [minStr, maxStr] = this.filtros.rangoPrecio.split('-');
      const min = parseFloat(minStr);
      const max = parseFloat(maxStr);
      productosFiltrados = productosFiltrados.filter(p => p.precio && p.precio >= min && p.precio <= max);
    }

    // Aquí podrías añadir el filtro por colaboración si tuvieras los datos en el producto
    // Ejemplo: if (this.filtros.colaboracion) { ... }

    // 2. ORDENACIÓN (CLIENTE)
    if (this.filtros.orden === 'asc') {
      productosFiltrados.sort((a, b) => (a.precio || 0) - (b.precio || 0));
    } else if (this.filtros.orden === 'desc') {
      productosFiltrados.sort((a, b) => (b.precio || 0) - (a.precio || 0));
    }

    // 3. ACTUALIZAR LA LISTA VISIBLE
    this.listaProductos = productosFiltrados;

    // Si no se aplica ningún filtro, mostramos todos los productos
    if (!this.filtros.rangoPrecio && !this.filtros.orden && !this.filtros.colaboracion) {
      this.listaProductos = [...this.productosOriginales];
    }
  }
  // METODO TEMPORAL PARA ARRANCAR EL PROYECTO
  protected agregarAlCarrito(funko: Producto) {

  }
}
