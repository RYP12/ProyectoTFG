package com.safa.cabezon_backend.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResenyaClienteDTO {
    private Integer id;
    private String texto;
    private Integer valoracion;
    private Date fecha;
    private BuscarProductoDTO producto;
    private BuscarClienteDTO cliente;
}
