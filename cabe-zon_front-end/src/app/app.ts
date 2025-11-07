import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Header} from './SHARED/header/header';
import {StartedPage} from './SHARED/started-page/started-page';
import {Catalogo} from './SHARED/catalogo/catalogo';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Header, StartedPage, Catalogo],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
