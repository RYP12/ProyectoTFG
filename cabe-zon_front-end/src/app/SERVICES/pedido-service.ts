import { Injectable } from '@angular/core';
import {Cliente} from './cliente-service';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

export enum Estado {
  EN_PREPARACION = 'EN_PREPARACION',
  ENVIADO = 'ENVIADO',
  ENTREGADO = 'ENTREGADO',
  CANCELADO = 'CANCELADO',
}

export interface Pedido {
  id?: number;
  fechaEstimada?: Date;
  fechaEntrega?: Date;
  estado?: Estado;
  fecha?: Date;
  precioTotal?: number;
  cliente?: Cliente;
}

export interface PageResponse<T> {
  content: T[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class PedidoService {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}
  // OBTENER TODOS LOS PRODUCTOS
  obtenerPedidos(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.apiUrl}/pedido/all`);
  }
  // OBTENER PEDIDOS ADMIN PAGINADOS DE 5 EN 5
  obtenerPedidosAdmin(page: number, size: number): Observable<PageResponse<Pedido>>{
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http.get<any>(`${this.apiUrl}/pedido/admin?page=${page}&size=${size}`);
  }
  // OBTENER PRODUCTO POR ID
  obtenerProductoPorID(id: number): Observable<Pedido> {
    return this.http.get<Pedido>(`${this.apiUrl}/pedido/${id}`);
  }
  // CREAR PRODUCTO
  crearProducto(pedido: Pedido): Observable<Pedido>{
    return this.http.post<Pedido>(`${this.apiUrl}/pedido/post`, pedido);
  }
  // ACTUALIZAR PRODUCTO
  actualizarProducto(id: number, pedido: Pedido): Observable<Pedido> {
    return this.http.put<Pedido>(`${this.apiUrl}/pedido/put/${id}`, pedido);
  }
  // ELIMINAR PRODUCTO
  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/pedido/delete/${id}`);
  }
}
