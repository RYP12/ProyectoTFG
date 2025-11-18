package com.safa.cabezon_backend.Dto;

import lombok.Data;

@Data
public class ProductoPedidoDTO {
    private Integer cantidad;
    private Integer idProducto;
    private Integer idPedido;
}
