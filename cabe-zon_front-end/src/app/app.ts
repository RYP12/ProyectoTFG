import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { OwnerControlPedidoForm } from "./BUNDLES/OwnerBundle/forms/owner-control-pedido-form/owner-control-pedido-form";
import { Footer } from './SHARED/footer/footer';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, OwnerControlPedidoForm, Footer],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})

export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
