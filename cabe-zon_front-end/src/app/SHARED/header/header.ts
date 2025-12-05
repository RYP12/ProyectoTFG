import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Carrito } from '../../BUNDLES/CarritoBundle/pages/carrito/carrito';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Producto, ProductoService } from '../../SERVICES/productoService';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    RouterLink,
    MatDialogModule,
    CommonModule,
    FormsModule
  ],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header implements OnInit {

  // Ya no necesitamos 'todosLosProductos' porque buscaremos en servidor
  resultadosBusqueda: Producto[] = [];
  textoBusqueda: string = '';

  constructor(
    private dialog: MatDialog,
    private productoService: ProductoService
  ) {}

  ngOnInit() {
    // Ya no cargamos todos los productos al inicio.
    // Esto hace que la página cargue más rápido.
  }

  buscarProducto() {
    const texto = this.textoBusqueda.trim();

    // 1. Si el texto está vacío, limpiamos la lista y no llamamos al servidor
    if (!texto) {
      this.resultadosBusqueda = [];
      return;
    }

    // 2. Llamada al backend usando tu nuevo método
    this.productoService.buscarPorTermino(texto).subscribe({
      next: (data: Producto[]) => {
        this.resultadosBusqueda = data;
        console.log('Resultados de búsqueda:', data);
      },
      error: (err) => {
        console.error('Error buscando productos:', err);
        this.resultadosBusqueda = [];
      }
    });
  }

  limpiarBusqueda() {
    this.textoBusqueda = '';
    this.resultadosBusqueda = [];
  }

  openModal(obj: any = {}) {
    this.dialog.open(Carrito, {
      data: obj,
      minWidth: 'auto'
    });
  }
}
