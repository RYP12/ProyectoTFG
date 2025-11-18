import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Footer} from './SHARED/footer/footer';
import {
  OwnerControlClienteForm
} from './BUNDLES/OwnerBundle/forms/owner-control-cliente-form/owner-control-cliente-form';
import {General} from './BUNDLES/GeneralBundle/pages/general/general';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Footer, OwnerControlClienteForm, General],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
