import {Component, Input, inject, Output, EventEmitter, Optional, Inject} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {ProductoService} from '../../../../SERVICES/productoService';
import { NgFor } from '@angular/common';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-form-comentario',
  imports: [
    ReactiveFormsModule,
    NgFor
  ],
  templateUrl: './form-comentario.html',
  styleUrl: './form-comentario.css',
})
export class FormComentario   {
  @Input() idProducto!: number;
  @Input() idCliente!: number;
  @Output() comentarioEnviado = new EventEmitter<any>();

  private fb = inject(FormBuilder);
  private productoService = inject(ProductoService);

  nombreProducto: string = '';

  form = this.fb.nonNullable.group({
    texto: ['', [Validators.required, Validators.minLength(5)]],
    valoracion: 5,
    idProducto: 0,
    idCliente: 0
  });

  constructor(
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any,
    @Optional() private dialogRef: MatDialogRef<FormComentario>
  ) {
    // Si viene desde el modal (customer-control)
    if (data) {
      this.idProducto = data.idProducto;
      this.idCliente = data.idCliente;
      this.nombreProducto = data.nombreProducto || '';
    }
  }

  ngOnInit() {
    this.form.patchValue({
      idProducto: this.idProducto,
      idCliente: this.idCliente
    });
  }

  enviar() {
    if (this.form.invalid) {
      alert('Por favor completa todos los campos correctamente');
      return;
    }

    this.productoService.crearResenya(this.form.value).subscribe({
      next: (res) => {
        this.comentarioEnviado.emit(res);
        this.form.reset({ valoracion: 5, texto: '', idProducto: this.idProducto, idCliente: this.idCliente });

        // Si está en modal, cerrarlo
        if (this.dialogRef) {
          this.dialogRef.close(res);
        }
      },
      error: (err) => {
        console.error(err);
        alert('Error al enviar la reseña');
      }
    });
  }

  cerrar() {
    if (this.dialogRef) {
      this.dialogRef.close();
    }
  }
}
