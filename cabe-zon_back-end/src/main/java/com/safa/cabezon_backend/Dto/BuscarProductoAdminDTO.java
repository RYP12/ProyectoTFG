package com.safa.cabezon_backend.Dto;

import lombok.Data;

@Data
public class BuscarProductoAdminDTO {
    private Integer id;
    private String nombre;
    private Double valoracion;
    private Integer stock;
    private Double precio;
}
