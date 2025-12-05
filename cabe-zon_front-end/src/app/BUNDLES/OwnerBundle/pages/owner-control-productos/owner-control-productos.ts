import {Component, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import {PageResponse, Producto, ProductoService} from '../../../../SERVICES/productoService';
import {log} from 'node:util';

@Component({
  selector: 'app-owner-control-productos',
  imports: [
    RouterLink
  ],
  templateUrl: './owner-control-productos.html',
  styleUrl: './owner-control-productos.css',
})
export class OwnerControlProductos implements OnInit {
  listaProductos: Producto[] = [];

  paginaActual: number = 0;
  tamanyoPagina: number = 5; // Tu requisito: de 5 en 5
  totalPaginas: number = 0;
  totalElementos: number = 0;
  esPrimera: boolean = true;
  esUltima: boolean = false;

  constructor(private productoService: ProductoService) {}

  ngOnInit() {
    this.cargarProductos();
  }

  cargarProductos() {
    this.productoService.obtenerProductosAdmin(this.paginaActual, this.tamanyoPagina).subscribe({
      next: (response: PageResponse<Producto>) => {
        this.listaProductos = response.content;
        this.totalPaginas = response.totalPages;
        this.totalElementos = response.totalElements;
        this.esPrimera = response.first;
        this.esUltima = response.last;
        this.paginaActual = response.number;
      },
      error: (error) => console.log('Error al cargar productos Admin:', error)
    });
  }

  paginaAnterior(){
    if(!this.esPrimera){
      this.paginaActual--;
      this.cargarProductos()
    }
  }

  paginaPosterior() {
    if(!this.esUltima){
      this.paginaActual++;
      this.cargarProductos()
    }
  }
}
