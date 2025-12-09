import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
// Asegúrate de que esta interfaz incluya 'cantidad?: number;'
import { Producto } from './productoService';

@Injectable({
  providedIn: 'root' // Singleton: Existe una única instancia para toda la app
})
export class CarritoService {

  // Fuente de verdad privada. Iniciamos con un array vacío.
  // El array guardará objetos Producto, donde cada uno representa una línea de item único en el carrito.
  private _productosEnCarrito = new BehaviorSubject<Producto[]>([]);

  // Observable público. Los componentes se suscribirán a esto.
  productosCarrito$ = this._productosEnCarrito.asObservable();

  constructor() { }

  agregarProducto(producto: Producto) {
    const listaActual = this._productosEnCarrito.value;
    // Buscamos si el producto ya está en el carrito por su ID
    const productoExistente = listaActual.find(p => p.id === producto.id);

    let nuevaLista: Producto[];

    if (productoExistente) {
      // Si existe: Aumentamos la cantidad del producto existente
      nuevaLista = listaActual.map(p => {
        if (p.id === producto.id) {
          // Usamos 'p.cantidad || 0' para manejar el caso inicial donde es undefined
          return { ...p, cantidad: (p.cantidad || 0) + 1 };
        }
        return p;
      });
    } else {
      // Si no existe: Añadimos el producto con cantidad 1
      const nuevoProductoEnCarrito: Producto = {
        ...producto,
        cantidad: 1
      };
      // Usamos el operador spread para crear un nuevo array (inmutabilidad)
      nuevaLista = [...listaActual, nuevoProductoEnCarrito];
    }

    // Emitimos el nuevo estado del carrito
    this._productosEnCarrito.next(nuevaLista);
    console.log(`Producto añadido o cantidad aumentada. Total de items diferentes: ${nuevaLista.length}`);
  }

  aumentarCantidadProducto(producto: Producto) {
    const listaActual = this._productosEnCarrito.value;

    const nuevaLista = listaActual.map(p => {
      if (p.id === producto.id) {
        return { ...p, cantidad: (p.cantidad || 0) + 1 };
      }
      return p;
    });

    this._productosEnCarrito.next(nuevaLista);
  }

  disminuirCantidadProducto(producto: Producto) {
    const listaActual = this._productosEnCarrito.value;

    const productoEncontrado = listaActual.find(p => p.id === producto.id);

    if (productoEncontrado && productoEncontrado.cantidad && productoEncontrado.cantidad > 1) {
      // Restar cantidad si es mayor que 1
      const nuevaLista = listaActual.map(p => {
        if (p.id === producto.id) {
          return { ...p, cantidad: p.cantidad! - 1 }; // ! afirma que cantidad no es null/undefined
        }
        return p;
      });
      this._productosEnCarrito.next(nuevaLista);
    } else if (productoEncontrado && productoEncontrado.cantidad === 1) {
      // Si la cantidad es 1, lo eliminamos completamente
      this.eliminarProducto(producto);
    }
    // Si la cantidad es 0 o menos, no hacemos nada.
  }

  eliminarProducto(producto: Producto) {
    const listaActual = this._productosEnCarrito.value;

    // Usamos filter para crear un nuevo array SIN el producto que queremos eliminar
    const nuevaLista = listaActual.filter(p => p.id !== producto.id);

    this._productosEnCarrito.next(nuevaLista);
    console.log(`Producto ${producto.nombre} eliminado. Items restantes: ${nuevaLista.length}`);
  }
}
