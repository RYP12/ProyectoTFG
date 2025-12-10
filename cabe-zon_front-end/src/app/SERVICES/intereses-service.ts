import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Coleccion} from './coleccion-service';

@Injectable({
  providedIn: 'root',
})
export class InteresesService {
  private apiUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) {}

  obtenerIntereses(idCliente: number): Observable<Coleccion[]> {
    return this.http.get<Coleccion[]>(`${this.apiUrl}/intereses/cliente/${idCliente}`)
  }

  agregarInteres(idCliente: number, idColeccion: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/intereses/add`, { idCliente, idColeccion })
  }

  eliminarInteres(idCliente: number, idColeccion:number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/intereses/${idCliente}/${idColeccion}`)
  }
}
