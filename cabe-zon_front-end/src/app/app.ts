import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Header} from './SHARED/header/header';
import {StartedPage} from './SHARED/started-page/started-page';
import {Catalogo} from './SHARED/catalogo/catalogo';
import {EdicionLimitada} from './SHARED/edicion-limitada/edicion-limitada';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Header, StartedPage, Catalogo, EdicionLimitada],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
