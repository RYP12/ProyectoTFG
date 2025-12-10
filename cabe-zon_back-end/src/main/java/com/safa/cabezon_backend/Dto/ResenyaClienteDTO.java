package com.safa.cabezon_backend.Dto;

import jakarta.validation.Valid;
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
    @Valid
    private BuscarProductoDTO producto;
    @Valid
    private BuscarClienteDTO cliente;
}
