import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { OwnerControl } from "./BUNDLES/OwnerBundle/pages/owner-control/owner-control";
import { Footer } from './SHARED/footer/footer';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, OwnerControl, Footer],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})

export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
