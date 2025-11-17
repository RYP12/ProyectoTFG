import { Component } from '@angular/core';
import {Header} from '../../../../../SHARED/header/header';

interface Producto {
  id: number;
  nombre: string;
  precio: string;
  imagen: string;
}

@Component({
  selector: 'app-customer-control',
  imports: [
    Header,
  ],
  templateUrl: './customer-control.html',
  styleUrl: './customer-control.css',
})
export class CustomerControl {
  // Lógica del carrusel
  currentIndex: number = 0;
  itemsPerView: number = 4;

  // Ejemplo de productos (reemplaza con tus datos reales)
  productos: Producto[] = [
    { id: 1, nombre: 'Producto 1', precio: '10€', imagen: '/ruta/imagen1.jpg' },
    { id: 2, nombre: 'Producto 2', precio: '20€', imagen: '/ruta/imagen2.jpg' },
    { id: 3, nombre: 'Producto 3', precio: '30€', imagen: '/ruta/imagen3.jpg' },
    { id: 4, nombre: 'Producto 4', precio: '40€', imagen: '/ruta/imagen4.jpg' },
    { id: 5, nombre: 'Producto 5', precio: '50€', imagen: '/ruta/imagen5.jpg' },
    { id: 6, nombre: 'Producto 6', precio: '60€', imagen: '/ruta/imagen6.jpg' }
  ];

  // Calcula el índice máximo
  get maxIndex(): number {
    return Math.max(0, this.productos.length - this.itemsPerView);
  }

  // Obtiene los productos visibles
  get productosVisibles() {
    return this.productos.slice(this.currentIndex, this.currentIndex + this.itemsPerView);
  }

  // Muestra/oculta flecha izquierda
  get mostrarFlechaIzquierda(): boolean {
    return this.currentIndex > 0;
  }

  // Muestra/oculta flecha derecha
  get mostrarFlechaDerecha(): boolean {
    return this.currentIndex < this.maxIndex;
  }

  // Navegar a la izquierda
  anterior(): void {
    if (this.currentIndex > 0) {
      this.currentIndex--;
    }
  }

  // Navegar a la derecha
  siguiente(): void {
    if (this.currentIndex < this.maxIndex) {
      this.currentIndex++;
    }
  }

  // Ir a una página específica (para los indicadores)
  irAPagina(index: number): void {
    this.currentIndex = index;
  }

  // Obtener array para los indicadores
  get paginasIndicadores(): number[] {
    return Array.from({ length: this.maxIndex + 1 }, (_, i) => i);
  }

  onValorar(producto: Producto): void {
    console.log('Valorar producto:', producto);
    // Aquí puedes implementar la lógica de valoración
  }
}
