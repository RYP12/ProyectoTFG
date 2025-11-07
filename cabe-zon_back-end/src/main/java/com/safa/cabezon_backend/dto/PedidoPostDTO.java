package com.safa.cabezon_backend.dto;

import com.safa.cabezon_backend.modelos.Estado;
import lombok.Data;

import java.util.Date;

@Data
public class PedidoPostDTO {
    private Date fechaEstimada;
    private Date fechaEntrega;
    private Estado estado;
    private Date fecha;
    private Integer IdCliente;
}
