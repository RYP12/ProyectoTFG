import {Component, OnInit, signal} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {PageResponse, Producto, ProductoService} from '../../../../SERVICES/productoService';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-owner-control-productos',
  imports: [
    RouterLink,
    FormsModule
  ],
  templateUrl: './owner-control-productos.html',
  styleUrl: './owner-control-productos.css',
})

export class OwnerControlProductos implements OnInit {
  // Contiene los productos de la página actual O los resultados de la búsqueda
  productos = signal<Producto[]>([]);

  paginaActual = signal<number>(1);
  totalPaginas = signal<number>(1);

  itemsPorPagina = 5;

  listaProductosFiltrada: Producto[] = [];

  // Propiedad para enlazar al campo de texto del buscador
  textoBusqueda: string = '';

  constructor(private productoService: ProductoService, private router: Router) {}

  ngOnInit() {
    this.cargarProductos();
  }

  cargarProductos() {
    // Si hay texto en la caja de búsqueda, no cargamos páginas, dejamos los resultados de la búsqueda
    if (this.textoBusqueda.trim()) {
      // Si la búsqueda está activa, simplemente no hagas nada (mantén los resultados actuales)
      // O llama a buscarProducto() si necesitas refrescar
      return;
    }

    const pagina = this.paginaActual();

    this.productoService.obtenerProductosAdmin(pagina - 1, this.itemsPorPagina).subscribe({
      next: (data: any) => {
        let productosCargados: Producto[] = [];

        if (Array.isArray(data)) {
          const totalItems = data.length;
          const paginasCalculadas = Math.ceil(totalItems / this.itemsPorPagina);
          this.totalPaginas.set(paginasCalculadas || 1);

          const inicio = (pagina - 1) * this.itemsPorPagina;
          const fin = inicio + this.itemsPorPagina;
          productosCargados = data.slice(inicio, fin);
        } else if (data.content) {
          productosCargados = data.content;
          this.totalPaginas.set(data.totalPages);
        }

        this.productos.set(productosCargados);
        this.listaProductosFiltrada = productosCargados;

      },
      error: (error) => console.log('Error al cargar productos Admin:', error)
    });
  }

  buscarProducto() {
    const texto = this.textoBusqueda.trim();

    // Si el texto está vacío, volvemos a la paginación normal
    if (!texto) {
      this.listaProductosFiltrada = []; // Limpia por si acaso
      this.paginaActual.set(1);
      this.cargarProductos(); // Vuelve a cargar la página 1 paginada
      return;
    }

    // Llamada al backend buscando en toda la base de datos
    this.productoService.buscarPorTermino(texto).subscribe({
      next: (data: Producto[]) => {
        // Desactivamos la paginación visualmente
        this.totalPaginas.set(1);
        this.paginaActual.set(1);

        // Asignamos los resultados al signal y a la lista visible
        this.productos.set(data);
        this.listaProductosFiltrada = data;
      },
      error: (err) => {
        console.error('Error buscando productos:', err);
        this.productos.set([]);
        this.listaProductosFiltrada = [];
      }
    });
  }


  // Mantenemos la lógica de paginación, pero solo debe ejecutarse si no estamos buscando
  paginaAnterior(){
    if (this.textoBusqueda.trim().length === 0 && this.paginaActual() > 1) {
      this.paginaActual.set(this.paginaActual() - 1);
      this.cargarProductos();
    }
  }

  paginaPosterior() {
    if (this.textoBusqueda.trim().length === 0 && this.paginaActual() < this.totalPaginas()) {
      this.paginaActual.set(this.paginaActual() + 1);
      this.cargarProductos();
    }
  }

  irAEditar(id: number | undefined) {
    if (id) {
      this.router.navigate(['admin/productos', id]);
    }
  }

}
