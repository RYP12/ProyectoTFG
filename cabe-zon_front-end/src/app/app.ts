import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Footer} from './SHARED/footer/footer';
import { OwnerControl } from "./BUNDLES/OwnerBundle/pages/owner-control/owner-control";
import {Login} from './BUNDLES/LoginBundle/pages/login/login';
import {CustomerControl} from './BUNDLES/CustomerBundle/pages/customer-control/customer-control/customer-control';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, OwnerControl, Footer, Login, CustomerControl],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('cabe-zon_front-end');
}
