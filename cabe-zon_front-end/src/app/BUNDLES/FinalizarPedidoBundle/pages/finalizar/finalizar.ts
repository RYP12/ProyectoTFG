import { Component, inject, OnInit, signal } from '@angular/core';
import { Header } from '../../../../SHARED/header/header';
import { Footer } from '../../../../SHARED/footer/footer';
import { Producto } from '../../../../SERVICES/productoService';
import { CarritoService } from '../../../../SERVICES/carrito-service';
import { Router } from '@angular/router';
import { AsyncPipe, CurrencyPipe } from '@angular/common';
import { DireccionService, Direccion } from '../../../../SERVICES/direccion-service';
import {ClienteService} from '../../../../SERVICES/cliente-service';

@Component({
  selector: 'app-finalizar',
  standalone: true,
  imports: [
    Header,
    Footer,
    AsyncPipe,
    CurrencyPipe
  ],
  templateUrl: './finalizar.html',
  styleUrl: './finalizar.css',
})
export class Finalizar implements OnInit {

  private carritoService = inject(CarritoService);
  private router = inject(Router);
  private direccionService = inject(DireccionService);
  private clienteService= inject(ClienteService);

  public direcciones = signal<Direccion[]>([]);
  public direccionSeleccionada = signal<Direccion | null>(null);

  // Observable del carrito
  productosPedido$ = this.carritoService.productosCarrito$;

  subtotal: number = 0;
  envio: number = 3;
  descuento: number = 0;
  total: number = 0;

  private readonly PLACEHOLDER_IMG_URL: string = '/ASSETS/IMAGES/placeholder.png';


  constructor() {
    this.productosPedido$.subscribe(productos => {
      this.actualizarResumen(productos);
    });
  }

  ngOnInit(): void {
    this.cargarDirecciones();
  }

  cargarDirecciones() {
    // TODO: Recuerda que este ID hardcodeado es temporal.
    // Deberá venir de tu AuthService (token) en el futuro.
    const idCliente = this.clienteService.obtenerIdClienteLogueado();

    if (idCliente) {
      // Si tenemos ID, cargamos sus direcciones
      this.direccionService.obtenerDireccionesCliente(idCliente).subscribe({
        next: (data) => {
          this.direcciones.set(data);
          if (data.length > 0) {
            this.direccionSeleccionada.set(data[0]);
          }
        },
        error: (err) => {
          console.error('Error al cargar direcciones:', err);
        }
      });
    } else {
      // 4. Manejo de caso "No Logueado"
      console.warn('Usuario no identificado. Redirigiendo al login...');
      // Opcional: Redirigir al login si es obligatorio estar logueado para ver esto
      // this.router.navigate(['/login']);
    }
  }

  seleccionarDireccion(direccion: Direccion) {
    this.direccionSeleccionada.set(direccion);
    console.log('Dirección elegida:', direccion);
  }

  // --- Lógica del Carrito ---

  protected decrementarCantidad(funko: Producto) {
    this.carritoService.disminuirCantidadProducto(funko);
  }

  protected incrementarCantidad(funko: Producto) {
    this.carritoService.aumentarCantidadProducto(funko);
  }

  protected eliminarDelPedido(funko: Producto) {
    this.carritoService.eliminarProducto(funko);
  }

  protected obtenerImagenUrl(funko: Producto): string {
    if (funko.imagenes && funko.imagenes.length > 0 && funko.imagenes[0].url) {
      return funko.imagenes[0].url;
    }
    return this.PLACEHOLDER_IMG_URL;
  }

  // --- Lógica del Resumen y Pago ---

  private actualizarResumen(productos: Producto[]) {
    this.subtotal = productos.reduce((acc, p) => acc + (p.precio! * (p.cantidad || 1)), 0);
    this.descuento = 0; // Lógica de cupones futura
    this.total = this.subtotal + this.envio - this.descuento;
  }

  confirmarPedido() {
    if (this.total > 0) {
      console.log('Procesando pedido. Total:', this.total);

      // Aquí validaremos si hay una dirección seleccionada antes de pagar
      if (!this.direccionSeleccionada()) {
        alert('Por favor, selecciona una dirección de envío.');
        return;
      }

      alert('Funcionalidad de pago en construcción');
    } else {
      alert('No hay productos en el pedido.');
    }
  }
}
