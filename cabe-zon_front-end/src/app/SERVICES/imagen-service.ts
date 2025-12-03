import { Injectable } from '@angular/core';
import {Producto} from './productoService';

export interface Imagenes{
  nombre?: string;
  url?: string;
}

@Injectable({
  providedIn: 'root',
})
export class ImagenService {

}
