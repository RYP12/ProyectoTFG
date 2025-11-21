package com.safa.cabezon_backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrearProductoDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer codigoProducto;
    private Integer stock;
    private Boolean exclusivo;
}
