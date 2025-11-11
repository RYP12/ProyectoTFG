import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Footer} from './SHARED/footer/footer';
import { OwnerControl } from "./BUNDLES/OwnerBundle/pages/owner-control/owner-control";
import {Catalogo} from './BUNDLES/CatalogoBundle/pages/catalogo/catalogo';
import {Header} from './SHARED/header/header';
import {Funko} from './BUNDLES/FunkoBundle/pages/funko/funko';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, OwnerControl, Footer, Catalogo, Header, Funko],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
