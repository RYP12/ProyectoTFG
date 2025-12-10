import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Producto} from './productoService';

@Injectable({
  providedIn: 'root',
})
export class GustosService {
  private apiUrl = "http://localhost:8080/";

  constructor(private http: HttpClient) {}

  obtenerGustos(idCliente: number): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/producto/gustos/${idCliente}`);
  }

  agregarGusto(idCliente: number, idProducto: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/gustos/add`, { idCliente, idProducto });
  }

  eliminarGusto(idCliente:number, idProducto: number):Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/gustos/${idCliente}/${idProducto}`);
  }
}
