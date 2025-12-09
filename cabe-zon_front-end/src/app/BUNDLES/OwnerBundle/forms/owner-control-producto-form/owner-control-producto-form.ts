import {Component, inject, OnInit, signal} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {Producto, ProductoService} from '../../../../SERVICES/productoService';
import {Coleccion, ColeccionService} from '../../../../SERVICES/coleccion-service';


@Component({
  selector: 'app-owner-control-producto-form',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './owner-control-producto-form.html',
  styleUrl: './owner-control-producto-form.css',
})
export class OwnerControlProductoForm implements OnInit {
  private fb = inject(FormBuilder);
  private productoService = inject(ProductoService);
  private coleccionService = inject(ColeccionService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  modoEdicion = signal<boolean>(false);
  productoId = signal<number | null>(null);

  productoForm!: FormGroup;

  colecciones = signal<Coleccion[]>([]);

  selectedFiles: File[] = [];

  ngOnInit() {
    this.iniciarFormulario();
    this.cargarColecciones();

    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        const id = parseInt(idParam, 10);
        this.productoId.set(id);
        this.modoEdicion.set(true);
        this.cargarDatosProducto(id);
      }
    });
  }

  iniciarFormulario() {
    this.productoForm = this.fb.group({
      nombre: ['', Validators.required],
      precio: [0, [Validators.required, Validators.min(0.01)]],
      descripcion: ['', Validators.required],
      stock: [1, [Validators.required, Validators.min(0)]],
      exclusivo: [false], // Checkbox
      codigoProducto: ['', Validators.required],

      coleccionId: [null, Validators.required],
    });
  }

  cargarColecciones() {
    this.coleccionService.obtenerColecciones().subscribe({
      next: (data) => this.colecciones.set(data),
      error: (err) => console.error('Error al cargar colecciones', err),
    });
  }

  cargarDatosProducto(id: number) {
    this.productoService.obtenerProductoPorID(id).subscribe({
      next: (producto) => {
        this.productoForm.patchValue({
          nombre: producto.nombre,
          precio: producto.precio,
          descripcion: producto.descripcion,
          stock: producto.stock,
          exclusivo: producto.exclusivo,
          codigoProducto: producto.codigoProducto,

          coleccionId: producto.colecciones?.[0]?.id || null,
        });
      },
      error: (err) => {
        console.error('Error al cargar el producto:', err);
        this.router.navigate(['/admin/productos']);
      }
    });
  }

  onFileSelected(event: any, index: number) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.selectedFiles[index] = file;
    }
  }

  procesarProducto() {
    if (this.productoForm.invalid) {
      this.productoForm.markAllAsTouched();
      console.error('Formulario inválido');
      return;
    }

    const formValue = this.productoForm.value;

    const productoData: Producto = {
      id: this.productoId() || undefined,
      nombre: formValue.nombre,
      precio: formValue.precio,
      descripcion: formValue.descripcion,
      stock: formValue.stock,
      exclusivo: formValue.exclusivo,
      codigoProducto: formValue.codigoProducto,

      colecciones: [{ id: formValue.coleccionId } as Coleccion],
    };

    if (this.modoEdicion() && this.productoId()) {
      this.productoService.actualizarProducto(this.productoId()!, productoData).subscribe({
        next: () => {
          console.log(`Producto ${this.productoId()} actualizado!`);
          this.router.navigate(['/admin/productos']);
        },
        error: (err) => console.error('Error al actualizar producto:', err),
      });
    } else {
      this.productoService.crearProducto(productoData).subscribe({
        next: (productoCreado) => {
          console.log('Producto creado exitosamente:', productoCreado);
          this.router.navigate(['/admin/productos']);
        },
        error: (err) => console.error('Error al crear producto:', err),
      });
    }
  }

  eliminarProducto() {
    if (this.productoId() && confirm('¿Estás seguro de que quieres eliminar este producto? Esta acción no se puede deshacer.')) {
      this.productoService.eliminarProducto(this.productoId()!).subscribe({
        next: () => {
          console.log(`Producto ${this.productoId()} eliminado!`);
          this.router.navigate(['/admin/productos']);
        },
        error: (err) => console.error('Error al eliminar producto:', err),
      });
    }
  }

  cancelar() {
    this.router.navigate(['/admin/productos']);
  }
}
