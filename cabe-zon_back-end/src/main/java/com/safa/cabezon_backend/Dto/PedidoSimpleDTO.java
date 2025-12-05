package com.safa.cabezon_backend.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class PedidoSimpleDTO {
    private Integer id;
    private Date fecha;
    private Double precioTotal;
    private Integer estado;
}
