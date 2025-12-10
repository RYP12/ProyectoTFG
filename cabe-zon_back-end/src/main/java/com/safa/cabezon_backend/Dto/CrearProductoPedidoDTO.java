package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrearProductoPedidoDTO {
    @NotNull
    @PositiveOrZero
    private Integer cantidad;
    @NotNull
    private Integer idProducto;
    @NotNull
    private Integer idPedido;
}
