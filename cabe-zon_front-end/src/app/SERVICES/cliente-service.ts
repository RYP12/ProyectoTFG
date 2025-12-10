import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
// import {Usuario} from './usuario-service';

export interface Nivel{
  id?:number;
  nivel?:string;
  descuento?:number;
}

export interface Direccion{
  id?:number;
  calle?: string;
  numero?: number;
  piso?: string;
  letra?: string;
  codigoPostal?: string;
  adicional?: string;
  pais?: string;
  provincia?: string;
  municipio?: string;
}

export interface ProductoPedido {
  id?: number;
  cantidad?: number;
  subtotal?: number;
  producto?: {
    id?: number;
    nombre?: string;
    precio?: number;
    imagenes?: Array<{ url?: string }>;
  };
}

export interface Pedido {
  id?: number;
  fecha?: Date;
  fechaEstimada?: Date;
  fechaEntrega?: Date;
  precioTotal?: number;
  estado?: string;
  productosPedidos?: ProductoPedido[];
}

export interface Cliente {
  id?: number;
  nombre?: string;
  apellidos?: string;
  foto?: string;
  cabecoins?: number;
  nivel?: Nivel;
  pedidos?: Pedido[];
  email?: string;
  fotoURL?: string;
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
  // OBTENER TODOS LOS CLIENTES
  obtenerClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/cliente/all`);
  }
  // ONTENER CLIENTES PAGINADOS DE 5 EN 5
  obtenerClientesAdmin(page: number, size: number): Observable<PageResponse<Cliente>>{
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http.get<any>(`${this.apiUrl}/cliente/admin?page=${page}&size=${size}`);
  }

  // OBTENER CLIENTE POR ID
  obtenerClientesPorID(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/cliente/${id}`);
  }
  // CREAR CLIENTE
  crearCliente(cliente: Cliente): Observable<Cliente>{
    return this.http.post<Cliente>(`${this.apiUrl}/cliente/post`, cliente);
  }
  // ACTUALIZAR CLIENTE
  actualizarCliente(id: number, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.apiUrl}/cliente/put/${id}`, cliente);
  }
  // ELIMINAR CLIENTE
  eliminarCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/cliente/delete/${id}`);
  }

  obtenerMiPerfil(): Observable<Cliente> {
    const token = localStorage.getItem('token');
    const headers = { Authorization: `Bearer ${token}` };

    return this.http.get<Cliente>(`${this.apiUrl}/cliente/me`, { headers });
  }

  subirFoto(id: number, formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/cliente/${id}/foto`, formData);
  }

  obtenerIdClienteLogueado(): number | null {
    const json = localStorage.getItem('token');
    if (!json) return null;

    try {
      const datos = JSON.parse(json);
      // Intentamos buscar el ID en varios lugares comunes según cómo responda tu backend
      return datos.id || (datos.cliente && datos.cliente.id) || (datos.usuario && datos.usuario.id) || null;
    } catch (e) {
      console.error('Error al leer sesión:', e);
      return null;
    }
  }
}
