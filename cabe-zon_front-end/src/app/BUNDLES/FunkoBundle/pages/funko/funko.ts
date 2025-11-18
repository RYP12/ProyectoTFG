import { Component } from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';

@Component({
  selector: 'app-funko',
  imports: [
    Header,
    Footer
  ],
  templateUrl: './funko.html',
  styleUrl: './funko.css',
})
export class Funko {

}
