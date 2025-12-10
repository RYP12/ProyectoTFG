import {Component, OnInit, signal} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
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

  constructor(private clienteService: ClienteService, private router: Router) {}

  ngOnInit() {
    this.cargarClientes();
  }

  cargarClientes() {
    const pagina = this.paginaActual();

    this.clienteService.obtenerClientesAdmin(pagina - 1, this.itemsPorPagina).subscribe({
      next: (data: any) => {
        if (data.content) {
          this.clientes.set(data.content);
          this.totalPaginas.set(data.totalPages);
        }
        this.listaClientesFiltrada = this.clientes();
      },
      error: (error) => console.log('Error al cargar productos Admin:', error)
    });
  }

  filtrar(event: any) {
    const texto = event.target.value.toLowerCase().trim();

    if (!texto) {
      this.listaClientesFiltrada = this.clientes();
      return;
    }

    this.listaClientesFiltrada = this.clientes().filter(cliente => {
      const nombre = cliente.nombre ? cliente.nombre.toLowerCase() : '';
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

  irADetalle(id: number | undefined) {
    if (id) {
      // Navegamos a la ruta de detalle: /admin/clientes/ID
      this.router.navigate(['admin/clientes', id]);
    }
  }
}
