import {Component, inject, OnInit} from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {Producto, ProductoService} from '../../../../SERVICES/productoService';
import {CurrencyPipe} from '@angular/common';


@Component({
  selector: 'app-starter-page',
  imports: [
    Header,
    Footer,
    CurrencyPipe
  ],
  templateUrl: './starter-page.html',
  styleUrl: './starter-page.css',
})
export class StarterPage implements OnInit {


  private productoService = inject(ProductoService);


  topProductos: Producto[] = [];


  ngOnInit(): void {
    // Llamada simple al backend
    this.productoService.obtenerTopVentas().subscribe({
      next: (data) => {
        this.topProductos = data;
      },
      error: (err) => console.error(err)
    });
  }
}
