import { Component } from '@angular/core';
import {Footer} from '../../../../SHARED/footer/footer';
import {Header} from '../../../../SHARED/header/header';

@Component({
  selector: 'app-edicion-limitada',
  imports: [
    Footer,
    Header
  ],
  templateUrl: './edicion-limitada.html',
  styleUrl: './edicion-limitada.css',
})
export class EdicionLimitada {

}
