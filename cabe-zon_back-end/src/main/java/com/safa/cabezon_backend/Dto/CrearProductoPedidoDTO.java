package com.safa.cabezon_backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrearProductoPedidoDTO {
    private Integer cantidad;
    private Integer idProducto;
    private Integer idPedido;
}
