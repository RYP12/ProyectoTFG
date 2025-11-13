import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { OwnerControlPedidoForm } from "./BUNDLES/OwnerBundle/forms/owner-control-pedido-form/owner-control-pedido-form";
import { Footer } from './SHARED/footer/footer';
import {
  OwnerControlProductoForm
} from './BUNDLES/OwnerBundle/forms/owner-control-producto-form/owner-control-producto-form';
import {OwnerControlPedidos} from './BUNDLES/OwnerBundle/forms/owner-control-pedidos/owner-control-pedidos';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, OwnerControlPedidoForm, Footer, OwnerControlProductoForm, OwnerControlPedidos],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})

export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
