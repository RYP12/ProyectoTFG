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



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Footer, Header, Carrito, Catalogo, Confirmar, EdicionLimitada, Finalizar, Funko],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
