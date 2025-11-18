import { Component } from '@angular/core';
import { Header } from '../../../../SHARED/header/header';
import { FormsModule } from '@angular/forms';
import {NgClass} from '@angular/common';
import {Footer} from '../../../../SHARED/footer/footer';

@Component({
  selector: 'app-login',
  imports: [
    Header,
    FormsModule,
    NgClass,
    Footer
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  isRegistroVisible: boolean = false;

  mostrarRegistro() {
    this.isRegistroVisible = true;
  }

  mostrarLogin() {
    this.isRegistroVisible = false;
  }
}
