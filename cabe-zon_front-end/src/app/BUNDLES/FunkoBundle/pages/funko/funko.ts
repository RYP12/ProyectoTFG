import {Component, inject, OnInit} from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {ActivatedRoute} from '@angular/router';
import {Producto, ProductoService, Resenya} from '../../../../SERVICES/productoService';
import {NgOptimizedImage} from '@angular/common';
import {CarritoService} from '../../../../SERVICES/carrito-service';

@Component({
  selector: 'app-funko',
  standalone: true,
  imports: [
    Header,
    Footer,
  ],
  templateUrl: './funko.html',
  styleUrl: './funko.css',
})
export class Funko implements OnInit {
  private route = inject(ActivatedRoute);
  private productoService = inject(ProductoService);

  // URL de reserva si el producto no tiene imagen (ajusta la ruta si es necesario)
  private readonly PLACEHOLDER_IMG_URL: string = 'assets/img/placeholder.png';


  producto: Producto | undefined;
  resenyas: Resenya[] = []

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));

      if (id) {
        this.cargarProducto(id);
      }
    });
  }

  private cargarProducto(id : number) {
    this.productoService.obtenerProductoPorID(id).subscribe({
      next: (data) => {
        this.producto = data;
        console.log('Producto cargado:', this.producto);
      },
      error: (error) => {
        console.log('Error cargando producto', error);
      }
    });

    this.productoService.obtenerResenyasPorProducto(id).subscribe({
      next: (data) => {
        this.resenyas = data;
        console.log('Reseñas cargadas:', this.resenyas);
      },
      error: (error) => {
        console.log('Error cargando reseñas', error)
      }
    })
  }

  obtenerImagenUrl(funko: Producto, index: number): string {
    // 1. Verifica si el array 'imagenes' existe
    // 2. Verifica si el array es lo suficientemente largo para el índice solicitado
    // 3. Verifica si el elemento en ese índice tiene una 'url'
    if (funko.imagenes && funko.imagenes.length > index && funko.imagenes[index].url) {
      return funko.imagenes[index].url;
    }
    // Si falla, devuelve la URL de reserva
    return this.PLACEHOLDER_IMG_URL;
  }
}
