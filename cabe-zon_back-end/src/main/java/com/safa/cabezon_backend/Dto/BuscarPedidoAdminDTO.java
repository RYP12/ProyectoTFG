package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Estado;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class BuscarPedidoAdminDTO {
    private Integer id;
    @Valid
    private Estado estado;
    @Valid
    private BuscarClienteDTO cliente;
    @PositiveOrZero
    private Double precioTotal;
}
