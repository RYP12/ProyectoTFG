import { Component } from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';

@Component({
  selector: 'app-starter-page',
  imports: [
    Header,
    Footer
  ],
  templateUrl: './starter-page.html',
  styleUrl: './starter-page.css',
})
export class StarterPage {

}
