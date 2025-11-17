import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Footer} from './SHARED/footer/footer';
import {Header} from './SHARED/header/header';
import {Carrito} from './BUNDLES/CarritoBundle/pages/carrito/carrito';
import {Catalogo} from './BUNDLES/CatalogoBundle/pages/catalogo/catalogo';
import {Confirmar} from './BUNDLES/ConfirmacionPedidoBundle/pages/confirmar/confirmar';
import {EdicionLimitada} from './BUNDLES/EdicionLimitadaBundle/pages/edicion-limitada/edicion-limitada';
import {Finalizar} from './BUNDLES/FinalizarPedidoBundle/pages/finalizar/finalizar';
import {Funko} from './BUNDLES/FunkoBundle/pages/funko/funko';
import {
  OwnerControlClienteForm
} from './BUNDLES/OwnerBundle/pages/owner-control-cliente-form/owner-control-cliente-form';
import {OwnerControlPedidos} from './BUNDLES/OwnerBundle/forms/owner-control-pedidos/owner-control-pedidos';
import {OwnerControlProductos} from './BUNDLES/OwnerBundle/forms/owner-control-productos/owner-control-productos';
import {Error} from './SHARED/ErrorBundle/pages/error/error';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Footer, Header, Carrito, Catalogo, Confirmar, EdicionLimitada, Finalizar, Funko, OwnerControlClienteForm, OwnerControlPedidos, OwnerControlProductos, Error],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
