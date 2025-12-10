import {Component, Input, inject, Output, EventEmitter} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {ProductoService} from '../../../../SERVICES/productoService';
import { NgFor } from '@angular/common';

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

  form = this.fb.nonNullable.group({
    texto: ['', [Validators.required, Validators.minLength(5)]],
    valoracion: 5,
    idProducto: 0,
    idCliente: 0
  });

  ngOnInit() {
    this.form.patchValue({
      idProducto: this.idProducto,
      idCliente: this.idCliente
    });
  }


  enviar() {
    if (this.form.invalid) return;

    this.productoService.crearResenya(this.form.value).subscribe({
      next: (res) => {
        this.comentarioEnviado.emit(res);
        this.form.reset({ valoracion: 5, texto: '', idProducto: this.idProducto, idCliente: this.idCliente });
      },
      error: (err) => console.error(err)
    });
  }
}
