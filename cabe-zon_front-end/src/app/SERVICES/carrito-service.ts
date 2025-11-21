import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Producto } from './productoService';

@Injectable({
  providedIn: 'root' // Singleton: Existe una única instancia para toda la app
})
export class CarritoService {

  // 1. Fuente de verdad privada. Iniciamos con un array vacío.
  private _productosEnCarrito = new BehaviorSubject<Producto[]>([]);

  // 2. Observable público. Los componentes se suscribirán a esto.
  // El signo de dólar ($) es una convención para indicar que es un Observable.
  productosCarrito$ = this._productosEnCarrito.asObservable();

  constructor() { }

  // Método para añadir items (Lógica de negocio)
  agregarProducto(producto: Producto) {
    // Obtenemos el valor actual sin suscribirnos
    const listaActual = this._productosEnCarrito.value;

    // Best Practice: Inmutabilidad. Creamos un NUEVO array con lo anterior + el nuevo.
    // Esto ayuda a la detección de cambios de Angular.
    this._productosEnCarrito.next([...listaActual, producto]);

    console.log('Producto añadido. Total:', this._productosEnCarrito.value.length);
  }

  // Método opcional para obtener el total (puedes usarlo luego)
  limpiarCarrito() {
    this._productosEnCarrito.next([]);
  }
}
