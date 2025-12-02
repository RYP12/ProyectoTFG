import { Component } from '@angular/core';
import {Header} from '../../../../../SHARED/header/header';
import {CommonModule, NgClass} from '@angular/common';
import {Footer} from '../../../../../SHARED/footer/footer';

interface Producto {
  id: number;
  nombre: string;
  cantidad: number;
  precio: string;
  imagen: string;
}

interface Nivel {
  points: number;
  class: string;
  label: string;
}

@Component({
  selector: 'app-customer-control',
  standalone: true,
  imports: [
    Header,
    Footer,
  ],
  templateUrl: './customer-control.html',
  styleUrl: './customer-control.css',
})
export class CustomerControl {
  // Lógica del carrusel
  currentIndex: number = 0;
  itemsPerView: number = 4;

  productos: Producto[] = [
    { id: 1, nombre: 'Deadpool', cantidad: 1, precio: '10€', imagen: '/ASSETS/IMAGES/DeadpoolRey.png' },
    { id: 1, nombre: 'Deadpool', cantidad: 1, precio: '10€', imagen: '/ASSETS/IMAGES/DeadpoolRey.png' },
    { id: 1, nombre: 'Deadpool', cantidad: 1, precio: '10€', imagen: '/ASSETS/IMAGES/DeadpoolRey.png' },
    { id: 1, nombre: 'Deadpool', cantidad: 1, precio: '10€', imagen: '/ASSETS/IMAGES/DeadpoolRey.png' },
    { id: 1, nombre: 'Deadpool', cantidad: 1, precio: '10€', imagen: '/ASSETS/IMAGES/DeadpoolRey.png' },
    { id: 1, nombre: 'Deadpool', cantidad: 1, precio: '10€', imagen: '/ASSETS/IMAGES/DeadpoolRey.png' },
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

  // Nivel
  puntosUsuario: number = 1200;

  // Calcula el nombre del nivel automáticamente según los puntos
  get nivelActual(): string {
    if (this.puntosUsuario >= 1200) return 'diamante';
    if (this.puntosUsuario >= 600) return 'esmeralda';
    if (this.puntosUsuario >= 300) return 'oro';
    if (this.puntosUsuario >= 150) return 'plata';
    return 'bronce';
  }

  // Calcula el porcentaje de llenado de cada tramo de línea individualmente
  getPorcentajeTramo(inicio: number, fin: number): string {
    if (this.puntosUsuario >= fin) {
      return '100%'; // Tramo completo
    } else if (this.puntosUsuario > inicio) {
      // Estamos en medio de este tramo
      const puntosEnTramo = this.puntosUsuario - inicio;
      const rangoTramo = fin - inicio;
      const porcentaje = (puntosEnTramo / rangoTramo) * 100;
      return `${porcentaje}%`;
    } else {
      return '0%'; // Aún no llegamos
    }
  }
}
