package com.safa.cabezon_backend.Dto;

import lombok.Data;

@Data
public class ProductosPedidoPostDTO {
    private Double precioTotal;
    private Integer cantidad;
    private Integer idProducto;
    private Integer idPedido;
}
