import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Catalogo} from './BUNDLES/CatalogoBundle/pages/catalogo/catalogo';
import {Header} from './SHARED/header/header';
import {EdicionLimitada} from './BUNDLES/EdicionLimitadaBundle/pages/edicion-limitada/edicion-limitada';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Catalogo, Header, EdicionLimitada],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
