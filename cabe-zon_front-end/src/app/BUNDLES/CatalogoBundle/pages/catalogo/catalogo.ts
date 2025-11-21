import {Component, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Producto, ProductoService} from '../../../../SERVICES/productoService';

@Component({
  selector: 'app-catalogo',
  standalone: true, // Asegúrate de que sea standalone
  imports: [FormsModule], // <--- 2. AÑADIR AQUÍ
  templateUrl: './catalogo.html',
  styleUrl: './catalogo.css',
})
export class Catalogo implements OnInit {

  listaProductos: Producto [] = [];

  // RECIBE LOS FILTROS DEL HTML
  filtros = {

    orden: '',
    rangoPrecio: '',
    colaboracion: ''

  };

  constructor(private productoService: ProductoService) {}

  ngOnInit() {
    this.cargarProductos();
  }

  cargarProductos (){
    this.productoService.obtenerProductos().subscribe({
      next:(datos) => this.listaProductos = datos,
      error:(error) => console.log(error)
    });
  }

  aplicarFiltros (){

  }
}
