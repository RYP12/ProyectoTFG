package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Estado;
import lombok.Data;

import java.util.Date;

@Data
public class PedidoDTO {
    private Date fechaEstimada;
    private Date fechaEntrega;
    private Estado estado;
    private Date fecha;
    private Double precio_total;
    private Integer IdCliente;
}
