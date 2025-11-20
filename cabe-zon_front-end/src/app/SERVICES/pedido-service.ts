import { Injectable } from '@angular/core';
import {Cliente} from './cliente-service';
import {Estado} from './estado-service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface Pedido {
  id?: number;
  fechaEstimada?: Date;
  fechaEntrega?: Date;
  estado?: Estado;
  fecha?: Date;
  precioTotal?: number;
  cliente?: Cliente;
}

@Injectable({
  providedIn: 'root',
})
export class PedidoService {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // OBTENER TODOS LOS PRODUCTOS
  obtenerProductos(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.apiUrl}/producto/all`);
  }
  // OBTENER PRODUCTO POR ID
  obtenerProductoPorID(id: number): Observable<Pedido> {
    return this.http.get<Pedido>(`${this.apiUrl}/producto/${id}`);
  }
  // CREAR PRODUCTO
  crearProducto(Pedido: Pedido): Observable<Pedido>{
    return this.http.post<Pedido>(`${this.apiUrl}/producto`, Pedido);
  }
  // ACTUALIZAR PRODUCTO
  actualizarProducto(id: number, Pedido: Pedido): Observable<Pedido> {
    return this.http.put<Pedido>(`${this.apiUrl}/${id}`, Pedido);
  }
  // ELIMINAR PRODUCTO
  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
