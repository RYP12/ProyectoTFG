package com.safa.cabezon_backend.Dto;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.security.access.method.P;

@Data
public class ProductoPedidoDTO {
    private Integer cantidad;
    @Valid
    private BuscarProductoDTO producto;
}
