import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface Cliente {
  id?: number;
  nombre?: string;
  apellidos?: string;
  foto?: string;
  cabecoins?: number;
}

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}
  // OBTENER TODOS LOS PRODUCTOS
  obtenerProductos(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/cliente/all`);
  }
  // OBTENER PRODUCTO POR ID
  obtenerProductoPorID(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/cliente/${id}`);
  }
  // CREAR PRODUCTO
  crearProducto(cliente: Cliente): Observable<Cliente>{
    return this.http.post<Cliente>(`${this.apiUrl}/cliente/post`, cliente);
  }
  // ACTUALIZAR PRODUCTO
  actualizarProducto(id: number, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.apiUrl}/cliente/put/${id}`, cliente);
  }
  // ELIMINAR PRODUCTO
  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/cliente/delete/${id}`);
  }
}
