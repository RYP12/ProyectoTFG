package com.safa.cabezon_backend.Dto;

import lombok.Data;
import org.springframework.security.access.method.P;

@Data
public class ProductoPedidoDTO {
    private Integer cantidad;
    private BuscarProductoDTO producto;
    private BuscarPedidoDTO pedido;
}
