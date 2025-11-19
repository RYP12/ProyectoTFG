import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Data, Producto} from '../../../../SERVICES/data';

@Component({
  selector: 'app-catalogo',
  imports: [],
  templateUrl: './catalogo.html',
  styleUrl: './catalogo.css',
})
export class Catalogo implements OnInit {
  listaProductos: Producto[] = [];

  constructor(private dataService: Data) { }

  ngOnInit() {
    this.dataService.obtenerProductos().subscribe({
      next: (datos) => {
        console.log(datos);
        this.listaProductos = datos;
      },
      error: (err) => {
        console.log(err);
      }
    })
  }
}
