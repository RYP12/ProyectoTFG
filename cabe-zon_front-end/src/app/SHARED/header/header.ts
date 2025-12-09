import { Component, OnInit } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Carrito } from '../../BUNDLES/CarritoBundle/pages/carrito/carrito';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Producto, ProductoService } from '../../SERVICES/productoService';
import { UsuarioService } from '../../SERVICES/usuario-service';

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

  resultadosBusqueda: Producto[] = [];
  textoBusqueda: string = '';

  nombreUsuario: string = 'CLIENTE';

  constructor(
    private dialog: MatDialog,
    private productoService: ProductoService,
    private router: Router,
    public usuarioService: UsuarioService
  ) {}

  ngOnInit() {
    this.usuarioService.nombreUsuario$.subscribe(nombre => {
      this.nombreUsuario = nombre;
    });
  }

  buscarProducto() {
    const texto = this.textoBusqueda.trim();

    // Si el texto está vacío, limpiamos la lista y no llamamos al servidor
    if (!texto) {
      this.resultadosBusqueda = [];
      return;
    }

    // Llamada al backend usando tu nuevo método
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

  irLogin() {
    if (this.nombreUsuario === 'CLIENTE') {
      this.router.navigate(['/login']);
    } else {
      this.router.navigate(['/cliente']);
    }
  }
}
