import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Footer} from './SHARED/footer/footer';
import { OwnerControl } from "./BUNDLES/OwnerBundle/pages/owner-control/owner-control";



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, OwnerControl, Footer],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
