import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface Producto {
  id?: number;
  nombre?: string;
  precio?: number;
  descripcion?: string;
  stock?: number;
  exclusivo?: boolean;
  codigoProducto?: number;
  valoracion?: number;
}

@Injectable({
  providedIn: 'root',
})

export class ProductoService {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}
  // OBTENER TODOS LOS PRODUCTOS
  obtenerProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/producto/all`);
  }
  // OBTENER PRODUCTO POR ID
  obtenerProductoPorID(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/producto/${id}`);
  }
  // CREAR PRODUCTO
  crearProducto(producto: Producto): Observable<Producto>{
    return this.http.post<Producto>(`${this.apiUrl}/producto/post`, producto);
  }
  // ACTUALIZAR PRODUCTO
  actualizarProducto(id: number, producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/producto/put/${id}`, producto);
  }
  // ELIMINAR PRODUCTO
  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/producto/delete/${id}`);
  }
  // OBTENER TOP VENTAS (4)
  obtenerTopVentas(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/producto/top-ventas`);
  }

  // OBTENER POR FRANJA DE PRECIO
  obtenerFranjaPrecio(min: number,  max: number): Observable<Producto[]> {

    const params = new HttpParams()
      .set('min', min.toString())
      .set('max', max.toString());

    return this.http.get<Producto[]>(`${this.apiUrl}/producto/franjaPrecio`,{params});
  }

  // FILTRAR POR COLECCION
  filtrarPorColeccion(idColeccion: number): Observable<Producto[]> {

    return this.http.get<Producto[]>(`${this.apiUrl}/producto/coleccion/${idColeccion}`);
  }


}
