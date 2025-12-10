import {Inject, Injectable, PLATFORM_ID} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {isPlatformBrowser} from '@angular/common';

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

  private nombreUsuarioSubject = new BehaviorSubject<string>('CLIENTE');

  nombreUsuario$ = this.nombreUsuarioSubject.asObservable();

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.recuperarUsuarioDeToken();
  }

  private guardarToken(token: string) {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('token', token);
    }
  }

  private leerToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('token');
    }
    return null;
  }

  private eliminarToken() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('token');
    }
  }

  // Login
  login(loginData: LoginDTO): Observable<string> {
    return this.http.post(this.baseUrl + '/login', loginData, { responseType: 'text' })
    .pipe(
      tap((token) => {
        this.guardarToken(token);
        this.obtenerDatosUsuario();
        }
      )
    )
  }

  // Cerrar sesión
  logout() {
    this.eliminarToken();
    this.nombreUsuarioSubject.next('CLIENTE');
  }

  // Método para pedir los datos del backend usando el token
  obtenerDatosUsuario() {
    const token = this.leerToken();
    if (!token) {
      console.warn('No hay token para obtener datos');
      return;
    }

    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);

    this.http.get<any>(this.baseUrl + '/me', {headers}).subscribe({
      next: (usuario) => {
        this.nombreUsuarioSubject.next(usuario.nombre);
      },
      error: (error) => {
        console.error('Error al obtener perfil:', error);
        this.logout();
      }
    })
  }

  obtenerIdCliente(): number | null {
    const token = this.leerToken();
    if (!token) return null;

    try {
      const payload = this.decodificarToken(token);

      // Asumiendo que el token incluye el ID del cliente
      // Ajusta según la estructura real de tu token
      if (payload && payload.tokenDTO && payload.tokenDTO.idCliente) {
        return payload.tokenDTO.idCliente;
      }

      return null;
    } catch (error) {
      console.error('Error al leer el ID del cliente del token:', error);
      return null;
    }
  }

  obtenerRol(): string | null {
    const token = this.leerToken();
    if (!token) return null;

    try {
      const payload = this.decodificarToken(token);

      if (payload && payload.tokenDTO && payload.tokenDTO.rol) {
        return payload.tokenDTO.rol;
      }
      return null;
    } catch (error) {
      console.error('Error al leer el rol del token:', error);
      return null;
    }
  }

  private decodificarToken(token: string): any {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));

      return JSON.parse(jsonPayload);
    } catch (e) {
      return null;
    }
  }

  // Recuperar datos si se recarga la página
  private recuperarUsuarioDeToken() {
    const token = this.leerToken();
    if (token) {
      this.obtenerDatosUsuario();
    }
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
