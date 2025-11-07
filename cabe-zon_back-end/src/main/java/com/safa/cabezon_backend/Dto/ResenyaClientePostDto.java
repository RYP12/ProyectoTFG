package com.safa.cabezon_backend.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResenyaClientePostDto {
    private String texto;
    private Integer valoracion;
    private Date fecha;
    private Integer idProducto;
    private Integer idCliente;
}
