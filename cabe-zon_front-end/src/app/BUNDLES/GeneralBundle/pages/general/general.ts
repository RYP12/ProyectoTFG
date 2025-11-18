import { Component } from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';

@Component({
  selector: 'app-general',
  imports: [
    Header,
    Footer
  ],
  templateUrl: './general.html',
  styleUrl: './general.css',
})
export class General {

}
