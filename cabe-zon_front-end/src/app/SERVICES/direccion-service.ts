import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface Direccion {
  id?: number;
  calle?: string;
  numero?: number;
  piso?: string;
  letra?: string;
  codigoPostal?: string;
  adicional?: string;
  pais?: string;
  provincia?: string;
  municipio?: string;
  idCliente?: number;
}

@Injectable({
  providedIn: 'root',
})
export class DireccionService {
  private apiUrl = 'http://localhost:8080/direccion';

  constructor(private http: HttpClient) {}

  obtenerDireccionesCliente(idCliente: number): Observable<Direccion[]> {
    // Necesitarás crear este endpoint en tu backend
    return this.http.get<Direccion[]>(`${this.apiUrl}/cliente/${idCliente}`);
  }

  crearDireccion(direccion: Direccion): Observable<Direccion> {
    return this.http.post<Direccion>(`${this.apiUrl}/post`, direccion);
  }

  actualizarDireccion(id: number, direccion: Direccion): Observable<Direccion> {
    return this.http.put<Direccion>(`${this.apiUrl}/put/${id}`, direccion);
  }

  eliminarDireccion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

}
