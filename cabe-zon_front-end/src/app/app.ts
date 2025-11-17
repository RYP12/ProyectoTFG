import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Footer} from './SHARED/footer/footer';
import {
  OwnerControlClienteForm
} from './BUNDLES/OwnerBundle/pages/owner-control-cliente-form/owner-control-cliente-form';
import {OwnerControlProductos} from './BUNDLES/OwnerBundle/forms/owner-control-productos/owner-control-productos';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Footer, OwnerControlClienteForm, OwnerControlProductos],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
