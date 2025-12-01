import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Producto} from './productoService';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class FiltrosService {

  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) { }



}
