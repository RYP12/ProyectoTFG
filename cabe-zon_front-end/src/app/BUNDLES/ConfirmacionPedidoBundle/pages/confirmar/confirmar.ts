import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';

@Component({
  selector: 'app-confirmar',
  imports: [
    RouterLink
  ],
  templateUrl: './confirmar.html',
  styleUrl: './confirmar.css',
})
export class Confirmar implements OnInit {
  private route = inject(ActivatedRoute);

  pedidoId: string | null = null;
  pedidoTotal: number = 0;

  ngOnInit(): void {
    // Leer los parámetros que enviamos desde finalizar.ts
    this.route.queryParams.subscribe(params => {
      this.pedidoId = params['id'] || 'N/A';
      this.pedidoTotal = parseFloat(params['total']) || 0;
    });
  }
}
