import { Injectable } from '@angular/core';
import {Routes} from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class Data {
  private apiUrl: string = 'http://localhost:8080/';
}
