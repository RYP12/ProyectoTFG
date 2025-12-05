import {Component, OnInit, signal} from '@angular/core';
import {RouterLink} from '@angular/router';
import {Cliente, ClienteService} from '../../../../SERVICES/cliente-service';
import {Producto} from '../../../../SERVICES/productoService';

@Component({
  selector: 'app-owner-control-clientes',
  imports: [
    RouterLink
  ],
  templateUrl: './owner-control-clientes.html',
  styleUrl: './owner-control-clientes.css',
})
export class OwnerControlClientes implements OnInit {
  clientes = signal<Cliente[]>([]);
  paginaActual = signal<number>(1);
  totalPaginas = signal<number>(1);

  itemsPorPagina = 5;
  listaClientesFiltrada: Cliente[] = [];

  constructor(private clienteService: ClienteService,) {}

  ngOnInit() {
    this.cargarClientes();
  }

  cargarClientes() {
    const pagina = this.paginaActual();

    this.clienteService.obtenerClientesAdmin(pagina - 1, this.itemsPorPagina).subscribe({
      next: (data: any) => {
        if (Array.isArray(data)) {
          const totalItems = data.length;
          const paginasCalculadas = Math.ceil(totalItems / this.itemsPorPagina);
          this.totalPaginas.set(paginasCalculadas || 1);

          const inicio = (pagina - 1) * this.itemsPorPagina;
          const fin = inicio + this.itemsPorPagina;
          const clientesRecortados = data.slice(inicio, fin);

          this.clientes.set(clientesRecortados);
        }
        else if (data.content) {
          this.clientes.set(data.content);
          this.totalPaginas.set(data.totalPages);
        }
      },
      error: (error) => console.log('Error al cargar productos Admin:', error)
    });
  }

  filtrar(event: any) {
    const texto = event.target.value.toLowerCase().trim(); // quitamos espacios sobrantes

    // Si está vacío, reseteamos
    if (!texto) {
      this.listaClientesFiltrada = this.clientes();
      return;
    }

    // Filtramos
    this.listaClientesFiltrada = this.clientes().filter(cliente => {
      // Si nombre es null, usamos cadena vacía
      const nombre = cliente.nombre ? cliente.nombre.toLowerCase() : '';

      // Convertimos el ID (número) a String. Si no tiene ID, cadena vacía.
      const id = cliente.id !== undefined && cliente.id !== null ? String(cliente.id) : '';

      return nombre.includes(texto) || id.includes(texto);
    });
  }

  paginaAnterior(){
    if (this.paginaActual() > 1) {
      this.paginaActual.set(this.paginaActual() - 1);
      this.cargarClientes();
    }
  }

  paginaPosterior() {
    if (this.paginaActual() < this.totalPaginas()) {
      this.paginaActual.set(this.paginaActual() + 1);
      this.cargarClientes();
    }
  }
}
