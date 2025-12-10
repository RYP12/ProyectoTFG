import {Component, OnInit} from '@angular/core';
import {Header} from '../../../../../SHARED/header/header';
import {CommonModule, NgClass} from '@angular/common';
import {Footer} from '../../../../../SHARED/footer/footer';
import {Cliente, ClienteService, Pedido, ProductoPedido} from '../../../../../SERVICES/cliente-service';
import {Router} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {Direccion, DireccionService} from '../../../../../SERVICES/direccion-service';
import {GustosService} from '../../../../../SERVICES/gustos-service';
import {InteresesService} from '../../../../../SERVICES/intereses-service';
import {ColeccionService, Coleccion} from '../../../../../SERVICES/coleccion-service';
import {Producto} from '../../../../../SERVICES/productoService';
import {CarritoService} from '../../../../../SERVICES/carrito-service';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {FormComentario} from '../../../../ResenyaBundle/pages/form-comentario/form-comentario';

@Component({
  selector: 'app-customer-control',
  standalone: true,
  imports: [
    CommonModule,
    Header,
    Footer,
    FormsModule,
    MatDialogModule,
  ],
  templateUrl: './customer-control.html',
  styleUrl: './customer-control.css',
})
export class CustomerControl implements OnInit {

  // Datos principales
  cliente: Cliente | null = null;
  direcciones: Direccion[] = [];
  listaDeseos: Producto[] = [];
  coleccionesSeguidas: Coleccion[] = [];
  coleccionesDisponibles: Coleccion[] = [];

  // Estado de carga
  cargando: boolean = false;
  errorMsg: string = '';

  // Modales
  mostrarModalEditar: boolean = false;
  mostrarModalDireccion: boolean = false;
  mostrarModalPassword: boolean = false;

  // Formularios
  formEditar = {
    nombre: '',
    apellidos: '',
  };

  formPassword = {
    actual: '',
    nueva: '',
    confirmar: ''
  };

  direccionForm: Direccion = {};
  direccionEditando: Direccion | null = null;

  // Carrusel de productos en pedidos
  currentIndex: number = 0;
  itemsPerView: number = 4;
  pedidoActualIndex: number = 0


  constructor(
    private clienteService: ClienteService,
    private direccionService: DireccionService,
    private gustosService: GustosService,
    private interesesService: InteresesService,
    private coleccionService: ColeccionService,
    private carritoService: CarritoService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.cargarDatos();
  }

  // Carga de datos
  cargarDatos() {
    this.cargando = true;
    this.clienteService.obtenerMiPerfil().subscribe({
      next: data => {
        this.cliente = data;
        if (this.cliente.id) {
          this.cargarDirecciones();
          this.cargarListaDeseos();
          this.cargarIntereses();
        }
        this.cargando = false;
        this.cargando = false;
      },
      error: error => {
        console.error('Error cargando perfil:', error);
        this.errorMsg = 'No se pudo cargar tu perfil. ¿Has iniciado sesión?'
        this.cargando = false;
        this.router.navigate(['/login']);
      }
    });
  }

  cargarDirecciones() {
    if (!this.cliente?.id) return;
    this.direccionService.obtenerDireccionesCliente(this.cliente.id).subscribe({
      next: (data) => this.direcciones = data,
      error: (err) => console.error('Error cargando direcciones:', err)
    });
  }

  cargarListaDeseos() {
    if (!this.cliente?.id) return;
    this.gustosService.obtenerGustos(this.cliente.id).subscribe({
      next: (data) => this.listaDeseos = data,
      error: (err) => console.error('Error cargando lista de deseos:', err)
    });
  }

  cargarIntereses() {
    if (!this.cliente?.id) return;

    // Cargar colecciones seguidas
    this.interesesService.obtenerIntereses(this.cliente.id).subscribe({
      next: (data) => {
        this.coleccionesSeguidas = data;
        this.cargarColeccionesDisponibles();
      },
      error: (err) => console.error('Error cargando intereses:', err)
    });
  }

  // Cargar resto de colecciones
  cargarColeccionesDisponibles() {
    this.coleccionService.obtenerColecciones().subscribe({
      next: (data) => {
        // Filtrar las que no sigue
        const idsSegidas = this.coleccionesSeguidas.map(c => c.id);
        this.coleccionesDisponibles = data.filter(c => !idsSegidas.includes(c.id));
      },
      error: (err) => console.error('Error cargando colecciones:', err)
    });
  }

  // Editar datos cliente
  abrirModalEditar() {
    this.formEditar.nombre = this.cliente?.nombre || '';
    this.formEditar.apellidos = this.cliente?.apellidos || '';
    this.mostrarModalEditar = true;
  }

  guardarCambiosPerfil() {
    if (!this.cliente?.id) return;

    const clienteDTO = {
      nombre: this.formEditar.nombre,
      apellidos: this.formEditar.apellidos
    };

    this.clienteService.actualizarCliente(this.cliente.id, clienteDTO).subscribe({
      next: () => {
        if (this.cliente) {
          this.cliente.nombre = this.formEditar.nombre;
          this.cliente.apellidos = this.formEditar.apellidos;
        }
        this.mostrarModalEditar = false;
        alert('Perfil actualizado correctamente');
      },
      error: (err) => {
        console.error('Error actualizando perfil:', err);
        alert('Error al actualizar el perfil');
      }
    });
  }

  // Cambiar contraseña
  abrirModalPassword() {
    this.formPassword = { actual: '', nueva: '', confirmar: '' };
    this.mostrarModalPassword = true;
  }

  guardarNuevaPassword() {
    if (this.formPassword.nueva !== this.formPassword.confirmar) {
      alert('Las contraseñas no coinciden');
      return;
    }

    if (!this.cliente?.email) {
      alert('No se pudo obtener el email del usuario');
      return;
    }

    const cambioPasswordDTO = {
      correo: this.cliente.email,
      passwordActual: this.formPassword.actual,
      passwordNuevo: this.formPassword.nueva
    };

    this.clienteService.cambiarPassword(cambioPasswordDTO).subscribe({
      next: () => {
        alert('Contraseña actualizada correctamente');
        this.mostrarModalPassword = false;
        this.formPassword = { actual: '', nueva: '', confirmar: '' };
      },
      error: (err) => {
        console.error('Error al cambiar contraseña:', err);
        alert('Error al cambiar la contraseña. Verifica que la contraseña actual sea correcta.');
      }
    });
  }

  //Foto de perfil
  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file && this.cliente?.id) {
      const formData = new FormData();
      formData.append('foto', file); // ✅ La clave debe coincidir con el backend

      this.clienteService.subirFoto(this.cliente.id, formData).subscribe({
        next: (response) => {
          if (this.cliente) {
            this.cliente.foto = response.url || URL.createObjectURL(file);
          }
          alert('Foto actualizada correctamente');
        },
        error: (err) => {
          console.error('Error subiendo foto:', err);
          alert('Error al subir la foto');
        }
      });
    }
  }

  //Direcciones
  abrirModalAgregarDireccion() {
    this.direccionForm = { idCliente: this.cliente?.id };
    this.direccionEditando = null;
    this.mostrarModalDireccion = true;
  }

  abrirModalEditarDireccion(direccion: Direccion) {
    this.direccionForm = { ...direccion };
    this.direccionEditando = direccion;
    this.mostrarModalDireccion = true;
  }

  guardarDireccion() {
    if (this.direccionEditando?.id) {
      // Editar
      this.direccionService.actualizarDireccion(this.direccionEditando.id, this.direccionForm).subscribe({
        next: () => {
          this.cargarDirecciones();
          this.mostrarModalDireccion = false;
          alert('Dirección actualizada');
        },
        error: (err) => alert('Error al actualizar dirección')
      });
    } else {
      // Crear
      this.direccionForm.idCliente = this.cliente?.id;
      this.direccionService.crearDireccion(this.direccionForm).subscribe({
        next: () => {
          this.cargarDirecciones();
          this.mostrarModalDireccion = false;
          alert('Dirección agregada');
        },
        error: (err) => alert('Error al agregar dirección')
      });
    }
  }

  eliminarDireccion(id: number | undefined) {
    if (!id || !confirm('¿Eliminar esta dirección?')) return;

    this.direccionService.eliminarDireccion(id).subscribe({
      next: () => {
        this.cargarDirecciones();
        alert('Dirección eliminada');
      },
      error: (err) => alert('Error al eliminar dirección')
    });
  }

  //Lista de deseos
  quitarDeListaDeseos(producto: Producto) {
    if (!this.cliente?.id || !producto.id) return;

    this.gustosService.eliminarGusto(this.cliente.id, producto.id).subscribe({
      next: () => {
        this.cargarListaDeseos();
        alert('Producto eliminado de la lista de deseos');
      },
      error: (err) => alert('Error al eliminar producto')
    });
  }

  agregarAlCarrito(producto: Producto) {
    this.carritoService.agregarProducto(producto);
    alert('Producto añadido al carrito');
  }

  //Intereses
  seguirColeccion(coleccion: Coleccion) {
    if (!this.cliente?.id || !coleccion.id) return;

    this.interesesService.agregarInteres(this.cliente.id, coleccion.id).subscribe({
      next: () => {
        this.cargarIntereses();
      },
      error: (err) => alert('Error al seguir colección')
    });
  }

  dejarDeSeguirColeccion(coleccion: Coleccion) {
    if (!this.cliente?.id || !coleccion.id) return;

    this.interesesService.eliminarInteres(this.cliente.id, coleccion.id).subscribe({
      next: () => {
        this.cargarIntereses();
      },
      error: (err) => alert('Error al dejar de seguir colección')
    });
  }

  //Carrusel de productos en pedidos
  obtenerProductosVisibles(pedido: Pedido): ProductoPedido[] {
    if (!pedido.productosPedidos) return [];
    const start = this.currentIndex;
    return pedido.productosPedidos.slice(start, start + this.itemsPerView);
  }

  // Muestra/oculta flecha izquierda
  mostrarFlechaIzquierda(): boolean {
    return this.currentIndex > 0;
  }

  // Muestra/oculta flecha derecha
  mostrarFlechaDerecha(pedido: Pedido): boolean {
    const total = pedido.productosPedidos?.length || 0;
    return this.currentIndex < total - this.itemsPerView;
  }

  // Navegar a la izquierda
  anterior() {
    if (this.currentIndex > 0) this.currentIndex--;
  }

  // Navegar a la derecha
  siguiente(pedido: Pedido) {
    const maxIndex = (pedido.productosPedidos?.length || 0) - this.itemsPerView;
    if (this.currentIndex < maxIndex) this.currentIndex++;
  }

  // Ir a una página específica (para los indicadores)
  irAPagina(index: number): void {
    this.currentIndex = index;
  }

  // Obtener array de indicadores
  paginasIndicadores(pedido: Pedido): number[] {
    const total = pedido.productosPedidos?.length || 0;
    if (total === 0) return [];

    const numPaginas = Math.ceil(total / this.itemsPerView);
    return Array.from({ length: numPaginas }, (_, i) => i);
  }

  //Valorar producto
  onValorar(productoPedido: ProductoPedido) {
    if (!productoPedido.producto?.id || !this.cliente?.id) {
      alert('No se puede valorar este producto');
      return;
    }

    const dialogRef = this.dialog.open(FormComentario, {
      width: '500px',
      data: {
        idProducto: productoPedido.producto.id,
        idCliente: this.cliente.id,
        nombreProducto: productoPedido.producto.nombre
      }
    });

    dialogRef.componentInstance.comentarioEnviado.subscribe((resenya: any) => {
      console.log('Reseña enviada:', resenya);
      alert('¡Gracias por tu valoración!');
      dialogRef.close();
    });
  }

  // Nivel
  get puntosUsuario(): number {
    return this.cliente?.cabecoins || 0;
  }
  // Calcula el nombre del nivel automáticamente según los puntos
  get nivelActual(): string {
    if (this.puntosUsuario >= 1200) return 'diamante';
    if (this.puntosUsuario >= 600) return 'esmeralda';
    if (this.puntosUsuario >= 300) return 'oro';
    if (this.puntosUsuario >= 150) return 'plata';
    return 'bronce';
  }

  // Calcula el porcentaje de llenado de cada tramo de línea individualmente
  getPorcentajeTramo(inicio: number, fin: number): string {
    if (this.puntosUsuario >= fin) {
      return '100%'; // Tramo completo
    } else if (this.puntosUsuario > inicio) {
      // Estamos en medio de este tramo
      const puntosEnTramo = this.puntosUsuario - inicio;
      const rangoTramo = fin - inicio;
      const porcentaje = (puntosEnTramo / rangoTramo) * 100;
      return `${porcentaje}%`;
    } else {
      return '0%'; // Aún no llegamos
    }
  }

  //Utilidades
  obtenerImagenUrl(producto: Producto | undefined): string | null {
    if (!producto?.imagenes || producto.imagenes.length === 0) {
      return null;
    }

    // Buscar imagen cuyo nombre empiece por "Foto Funko"
    const imagenFunko = producto.imagenes.find(
      img => img.nombre?.startsWith('Foto Funko')
    );

    // Si la encuentra, devolver su URL
    return imagenFunko?.url || null;
  }

  logout() {
    localStorage.removeItem('token');
    window.location.href = '/';
  }

  cerrarModal() {
    this.mostrarModalEditar = false;
    this.mostrarModalDireccion = false;
    this.mostrarModalPassword = false;
  }
}
