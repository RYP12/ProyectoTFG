import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Pedido} from './pedido-service';

export enum Rol {
  CLIENTE = 'CLIENTE',
  ADMINISTRADOR = 'ADMINISTRADOR',
}

export interface Usuario {
  correo?: string;
  rol ?: Rol
}

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}
  // OBTENER TODOS LOS PRODUCTOS
  obtenerPedidos(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.apiUrl}/pedido/all`);
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
