import {Component, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import {Cliente, ClienteService} from '../../../../SERVICES/cliente-service';

@Component({
  selector: 'app-owner-control-clientes',
  imports: [
    RouterLink
  ],
  templateUrl: './owner-control-clientes.html',
  styleUrl: './owner-control-clientes.css',
})
export class OwnerControlClientes implements OnInit {
  listaClientes: Cliente[] = [];
  listaClientesFiltrada: Cliente[] = [];

  constructor(private clienteService: ClienteService,) {}

  ngOnInit() {
    this.clienteService.obtenerClientes().subscribe({
      next: (datos) => {
        this.listaClientes = datos;
        this.listaClientesFiltrada = datos;
        console.log(this.listaClientes);
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  filtrar(event: any) {
    const texto = event.target.value.toLowerCase().trim(); // quitamos espacios sobrantes

    // Si está vacío, reseteamos
    if (!texto) {
      this.listaClientesFiltrada = this.listaClientes;
      return;
    }

    // Filtramos
    this.listaClientesFiltrada = this.listaClientes.filter(cliente => {
      // Si nombre es null, usamos cadena vacía
      const nombre = cliente.nombre ? cliente.nombre.toLowerCase() : '';

      // Convertimos el ID (número) a String. Si no tiene ID, cadena vacía.
      const id = cliente.id !== undefined && cliente.id !== null ? String(cliente.id) : '';

      return nombre.includes(texto) || id.includes(texto);
    });
  }

}
