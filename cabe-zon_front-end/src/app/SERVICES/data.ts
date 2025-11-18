import { Injectable } from '@angular/core';
import {Routes} from '@angular/router';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Data {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

}
