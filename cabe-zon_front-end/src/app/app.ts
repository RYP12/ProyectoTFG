import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Footer} from './SHARED/footer/footer';
import { OwnerControl } from "./BUNDLES/OwnerBundle/pages/owner-control/owner-control";
import {Catalogo} from './BUNDLES/CatalogoBundle/pages/catalogo/catalogo';
import {Header} from './SHARED/header/header';
import {Funko} from './BUNDLES/FunkoBundle/pages/funko/funko';
import {EdicionLimitada} from './BUNDLES/EdicionLimitadaBundle/pages/edicion-limitada/edicion-limitada';
import {AboutUs} from './BUNDLES/SobreNosotrosBundle/pages/about-us/about-us';
import {StartedPage} from './BUNDLES/StarterBundle/pages/started/started-page';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, OwnerControl, Footer, Catalogo, Header, Funko, EdicionLimitada, AboutUs, StartedPage],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
