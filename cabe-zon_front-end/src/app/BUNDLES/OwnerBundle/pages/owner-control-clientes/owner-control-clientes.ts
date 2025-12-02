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

  constructor(private clienteService: ClienteService,) {}

  ngOnInit() {
    this.clienteService.obtenerClientes().subscribe({
      next: (datos) => {
        this.listaClientes = datos;
        console.log(this.listaClientes);
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

}
