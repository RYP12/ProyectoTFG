import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Footer} from './SHARED/footer/footer';
import { OwnerControl } from "./BUNDLES/OwnerBundle/pages/owner-control/owner-control";
import {
  OwnerControlClienteForm
} from './BUNDLES/OwnerBundle/pages/owner-control-cliente-form/owner-control-cliente-form';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, OwnerControl, Footer, OwnerControlClienteForm],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
