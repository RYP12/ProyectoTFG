package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrearResenyaClienteDTO {
    @NotNull
    private String texto;
    @NotNull
    @PositiveOrZero
    private Integer valoracion;
    @NotNull
    private Integer idProducto;
    @NotNull
    private Integer idCliente;
}
