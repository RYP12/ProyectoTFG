import { Component } from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';

@Component({
  selector: 'app-about-us',
  imports: [
    Header,
    Footer
  ],
  templateUrl: './about-us.html',
  styleUrl: './about-us.css',
})
export class AboutUs {

}
