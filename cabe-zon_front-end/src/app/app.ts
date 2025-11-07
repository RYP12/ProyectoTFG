import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Propietario } from "./BUNDLES/OwnerBundle/pages/owner/propietario";
import { OwnerControl } from "./BUNDLES/OwnerBundle/pages/owner-control/owner-control";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, OwnerControl],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
