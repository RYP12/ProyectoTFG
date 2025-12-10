import {Component, inject, OnInit} from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {ActivatedRoute} from '@angular/router';
import {Producto, ProductoService, Resenya} from '../../../../SERVICES/productoService';
import {NgFor, NgIf, NgOptimizedImage} from '@angular/common';
import {CarritoService} from '../../../../SERVICES/carrito-service';
import {FormComentario} from '../../../ResenyaBundle/pages/form-comentario/form-comentario';
import {GustosService} from '../../../../SERVICES/gustos-service';
import {UsuarioService} from '../../../../SERVICES/usuario-service';

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
  private gustosService = inject(GustosService);
  private usuarioService = inject(UsuarioService);

  producto: Producto | undefined;
  resenyas: any[] = [];
  mostrarFormulario = false;

  esFavorito: boolean = false;
  idCliente: number | null = null;


  ngOnInit() {

    this.idCliente = this.usuarioService.obtenerIdCliente();

    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));

      if (id) {
        this.cargarProducto(id);
      }
    });
  }

  cargarProducto(id: number) {
    this.productoService.obtenerProductoPorID(id).subscribe({
      next: (data) => {
        this.producto = data;
        console.log('Producto cargado:', this.producto);

        this.verificarSiEsFavorito();
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

  verificarSiEsFavorito() {
    if (!this.idCliente) return;

    this.gustosService.obtenerGustos(this.idCliente).subscribe({
      next: (gustos) => {
        this.esFavorito = gustos.some(g => g.id === this.producto?.id);
      },
      error: (err) => console.error('Error verificando favoritos:', err)
    });
  }

  toggleFavorito() {
    if (!this.idCliente || !this.producto?.id) {
      alert('Debes iniciar sesión para guardar favoritos');
      return;
    }

    if (this.esFavorito) {
      // Quitar de favoritos
      this.gustosService.eliminarGusto(this.idCliente, this.producto.id).subscribe({
        next: () => {
          this.esFavorito = false;
          alert('Eliminado de favoritos');
        },
        error: (err) => {
          console.error('Error al eliminar favorito:', err);
          alert('Error al eliminar de favoritos');
        }
      });
    } else {
      // Añadir a favoritos
      this.gustosService.agregarGusto(this.idCliente, this.producto.id).subscribe({
        next: () => {
          this.esFavorito = true;
          alert('Añadido a favoritos');
        },
        error: (err) => {
          console.error('Error al agregar favorito:', err);
          alert('Error al agregar a favoritos');
        }
      });
    }
  }

  obtenerImagenUrl(funko: Producto | undefined): string | null {
    if (!funko?.imagenes || funko.imagenes.length === 0) {
      return null;
    }

    // Buscar imagen cuyo nombre empiece por "Foto Funko"
    const imagenFunko = funko.imagenes.find(
      img => img.nombre?.startsWith('Foto Funko')
    );

    // Si la encuentra, devolver su URL
    return imagenFunko?.url || null;
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
