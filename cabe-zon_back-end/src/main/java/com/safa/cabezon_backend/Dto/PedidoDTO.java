package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Estado;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Date;

@Data
public class PedidoDTO {
    private Date fechaEstimada;
    private Date fechaEntrega;
    private Estado estado;
    @NotNull

    private Date fecha;
    @NotNull
    @Positive
    private Double precio_total;
    @NotNull
    private Integer idCliente;
}
