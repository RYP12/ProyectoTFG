import {Component, inject, OnInit} from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {ActivatedRoute} from '@angular/router';
import {Producto, ProductoService, Resenya} from '../../../../SERVICES/productoService';
import {NgFor, NgIf, NgOptimizedImage} from '@angular/common';
import {CarritoService} from '../../../../SERVICES/carrito-service';
import {FormComentario} from '../../../ResenyaBundle/pages/form-comentario/form-comentario';

@Component({
  selector: 'app-funko',
  standalone: true,
  imports: [
    Header,
    Footer,
    FormComentario,
    NgIf,
    NgFor
  ],
  templateUrl: './funko.html',
  styleUrl: './funko.css',
})
export class Funko implements OnInit {
  private route = inject(ActivatedRoute);
  private productoService = inject(ProductoService);
  private carritoService = inject(CarritoService);

  producto: Producto | undefined;
  resenyas: any[] = [];

  mostrarFormulario = false;

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id) this.cargarProducto(id);
    });
  }

  cargarProducto(id: number) {
    this.productoService.obtenerProductoPorID(id).subscribe({
      next: (data) => this.producto = data,
      error: (err) => console.error(err)
    });

    this.productoService.obtenerResenyasPorProducto(id).subscribe({
      next: (data) => this.resenyas = data,
      error: (err) => console.error(err)
    });
  }

  obtenerImagenUrl(funko: Producto, index: number): string {
    if (funko.imagenes && funko.imagenes.length > index && funko.imagenes[index].url) {
      return funko.imagenes[index].url;
    }
    return 'assets/img/placeholder.png';
  }

  trackById(index: number, item: any) {
    return item.id;
  }

  handleComentarioEnviado(resenya: any) {

    this.resenyas.unshift(resenya);
    this.mostrarFormulario = false;

  }

  abrirFormulario() {
    this.mostrarFormulario = true;
  }

  cerrarFormulario() {
    this.mostrarFormulario = false;
  }

  protected agregarAlCarrito(funko: Producto) {
    this.carritoService.agregarProducto(funko);
    alert('¡Funko añadido al carrito!');
  }

}
