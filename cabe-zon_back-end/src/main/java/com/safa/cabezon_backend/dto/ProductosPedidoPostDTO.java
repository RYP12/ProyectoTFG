package com.safa.cabezon_backend.dto;

import lombok.Data;

@Data
public class ProductosPedidoPostDTO {
    private Double precioTotal;
    private Integer cantidad;
    private Integer idProducto;
    private Integer idPedido;
}
