import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface LoginDTO {
  username?: string,
  password?: string,
}

export interface RegistroDTO {
  username?: string,
  password?: string,
  nombre?: string,
  apellidos?: string,
}

export interface RecuperarPasswordDTO {
  token: string,
  nuevaPassword?: string,
}

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {

  private baseUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  // Login
  login(loginData: LoginDTO): Observable<string> {
    return this.http.post(this.baseUrl + 'login', loginData, { responseType: 'text' });
  }

  // Registro
  registrar(registroData: RegistroDTO): Observable<void> {
    return this.http.post<void>(this.baseUrl + '/registro', registroData);
  }

  // Confirmar cuenta
  confirmarCuenta(token: string): Observable<string> {
    return this.http.get(this.baseUrl + `/confirmar?token=${token}`, { responseType: 'text' });
  }

  // Solicitar recuperación (olvidar contraseña)
  solicitarRecuperacion(email: string): Observable<string> {
    return this.http.post(this.baseUrl + '/solicitar-recuperacion', { email: email}, { responseType: 'text' });
  }

  // Cambiar contraseña
  cambiarPassword(datos: RecuperarPasswordDTO): Observable<string> {
    return this.http.post(this.baseUrl + '/cambiar-password', datos, { responseType: 'text' });
  }
}
