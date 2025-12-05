import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
// import {Usuario} from './usuario-service';

export interface Nivel{
  id?:number;
  nivel?:string;
  descuento?:number;
}

export interface Cliente {
  id?: number;
  nombre?: string;
  apellidos?: string;
  foto?: string;
  cabecoins?: number;
  nivel?: Nivel;
  pedidos?: any[];
  // usuario?: Usuario
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
export class ClienteService {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}
  // OBTENER TODOS LOS PRODUCTOS
  obtenerClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/cliente/all`);
  }
  // ONTENER CLIENTES PAGINADOS DE 5 EN 5
  obtenerClientesAdmin(page: number, size: number): Observable<PageResponse<Cliente>>{
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http.get<any>(`${this.apiUrl}/cliente/admin?page=${page}&size=${size}`);
  }

  // OBTENER PRODUCTO POR ID
  obtenerClientesPorID(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/cliente/${id}`);
  }
  // CREAR PRODUCTO
  crearCliente(cliente: Cliente): Observable<Cliente>{
    return this.http.post<Cliente>(`${this.apiUrl}/cliente/post`, cliente);
  }
  // ACTUALIZAR PRODUCTO
  actualizarCliente(id: number, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.apiUrl}/cliente/put/${id}`, cliente);
  }
  // ELIMINAR PRODUCTO
  eliminarCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/cliente/delete/${id}`);
  }
}
