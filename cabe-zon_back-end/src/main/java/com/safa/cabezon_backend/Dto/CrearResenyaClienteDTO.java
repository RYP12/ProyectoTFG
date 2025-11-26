package com.safa.cabezon_backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrearResenyaClienteDTO {
    private String texto;
    private Integer valoracion;
    private Integer idProducto;
    private Integer idCliente;
}
