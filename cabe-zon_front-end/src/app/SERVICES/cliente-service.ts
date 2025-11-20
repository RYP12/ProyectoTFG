import { Injectable } from '@angular/core';

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

}
