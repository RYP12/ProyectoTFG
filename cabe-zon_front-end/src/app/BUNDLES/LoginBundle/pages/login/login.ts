import { Component } from '@angular/core';
import { Header } from '../../../../SHARED/header/header';
import { FormsModule } from '@angular/forms';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [
    Header,
    FormsModule,
    NgClass
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
