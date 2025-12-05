import {Component, OnInit, signal} from '@angular/core';
import {RouterLink} from '@angular/router';
import {PageResponse, Producto, ProductoService} from '../../../../SERVICES/productoService';

@Component({
  selector: 'app-owner-control-productos',
  imports: [
    RouterLink
  ],
  templateUrl: './owner-control-productos.html',
  styleUrl: './owner-control-productos.css',
})
export class OwnerControlProductos implements OnInit {
  productos = signal<Producto[]>([]);
  paginaActual = signal<number>(1);
  totalPaginas = signal<number>(1);

  itemsPorPagina = 5;

  constructor(private productoService: ProductoService) {}

  ngOnInit() {
    this.cargarProductos();
  }

  cargarProductos() {
    const pagina = this.paginaActual();

    this.productoService.obtenerProductosAdmin(pagina - 1, this.itemsPorPagina).subscribe({
      next: (data: any) => {
        if (Array.isArray(data)) {
          const totalItems = data.length;
          const paginasCalculadas = Math.ceil(totalItems / this.itemsPorPagina);
          this.totalPaginas.set(paginasCalculadas || 1);

          const inicio = (pagina - 1) * this.itemsPorPagina;
          const fin = inicio + this.itemsPorPagina;
          const productosRecortados = data.slice(inicio, fin);

          this.productos.set(productosRecortados);
        }
        else if (data.content) {
          this.productos.set(data.content);
          this.totalPaginas.set(data.totalPages);
        }
      },
      error: (error) => console.log('Error al cargar productos Admin:', error)
    });
  }

  paginaAnterior(){
    if (this.paginaActual() > 1) {
      this.paginaActual.set(this.paginaActual() - 1);
      this.cargarProductos();
    }
  }

  paginaPosterior() {
    if (this.paginaActual() < this.totalPaginas()) {
      this.paginaActual.set(this.paginaActual() + 1);
      this.cargarProductos();
    }
  }
}
