import {Component, inject, OnInit} from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {Producto, ProductoService} from '../../../../SERVICES/productoService';
import {CurrencyPipe} from '@angular/common';
import {RouterLink} from '@angular/router';


@Component({
  selector: 'app-starter-page',
  imports: [
    Header,
    Footer,
    CurrencyPipe,
    RouterLink
  ],
  templateUrl: './starter-page.html',
  styleUrl: './starter-page.css',
})
export class StarterPage implements OnInit {

  private productoService = inject(ProductoService);

  topProductos: Producto[] = [];

  // URL del placeholder si no hay imagen
  private readonly PLACEHOLDER_IMG_URL: string = '/ASSETS/IMAGES/placeholder.png';


  ngOnInit(): void {
    // Llamamos a la peticion preparada en el productoService gracias a subscribe
    this.productoService.obtenerTopVentas().subscribe({
      // Si todo esta bien data coge los datos json de los productos
      next: (data) => {
        this.topProductos = data;
      },
      error: (err) => console.error(err)
    });
  }

  obtenerImagenUrl(funko: Producto, index: number): string {

    if (funko.imagenes && funko.imagenes.length > index && funko.imagenes[index].url) {
      // Retorna la URL del backend (que ya debería ser completa si viene de tu servicio)
      return funko.imagenes[index].url;
    }
    return this.PLACEHOLDER_IMG_URL;
  }
}
