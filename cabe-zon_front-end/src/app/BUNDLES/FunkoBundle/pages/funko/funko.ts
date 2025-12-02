import {Component, inject, OnInit} from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {ActivatedRoute} from '@angular/router';
import {Producto, ProductoService} from '../../../../SERVICES/productoService';

@Component({
  selector: 'app-funko',
  standalone: true,
  imports: [
    Header,
    Footer
  ],
  templateUrl: './funko.html',
  styleUrl: './funko.css',
})
export class Funko implements OnInit {
  private route = inject(ActivatedRoute);
  private productoService = inject(ProductoService);

  producto: Producto | undefined;

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
  }
}
