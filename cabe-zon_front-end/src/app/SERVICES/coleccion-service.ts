import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface Coleccion {
  id?: number;
  nombre?: string;
  numeroProductos?: number;
}

@Injectable({
  providedIn: 'root',
})
export class ColeccionService {
  private apiUrl: string = 'http://localhost:8080';
  constructor(private http: HttpClient) { }
   //Obtener todas las colecciones
  obtenerColecciones(): Observable<Coleccion[]>{
    return this.http.get<Coleccion[]>(`${this.apiUrl}/coleccion/all`);
  }
  //Obtene coleccion por id
  obtenerProductoPorID(id:number):Observable<Coleccion>{
    return this.http.get<Coleccion>(`${this.apiUrl}/producto/${id}`);
  }
}
