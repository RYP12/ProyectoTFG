import { Injectable } from '@angular/core';
import {Routes} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';



export interface Producto {
  id: number;
  name: string;
  price: number;
}

@Injectable({
  providedIn: 'root',
})

export class Data {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  obtenerProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/producto/all`);
  }
}
