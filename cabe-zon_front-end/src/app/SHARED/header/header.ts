import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {Carrito} from '../../BUNDLES/CarritoBundle/pages/carrito/carrito';
@Component({
  selector: 'app-header',
  imports: [
    RouterLink,
    MatDialogModule
  ],
  standalone: true,
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
    constructor(private dialog: MatDialog) {}

    openModal(obj: any = {}) {
      let resp: any;

      const dialogRef = this.dialog.open(Carrito, {
        data: obj,
        minWidth: 'auto'
      });

    }
}
