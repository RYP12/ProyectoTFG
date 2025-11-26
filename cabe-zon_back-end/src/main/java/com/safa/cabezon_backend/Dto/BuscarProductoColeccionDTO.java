package com.safa.cabezon_backend.Dto;

import lombok.Data;

import java.util.Set;

@Data
public class BuscarProductoColeccionDTO {
    private String nombre;
    private Double precio;
    private Set<BuscarImagenDTO> imagenes;
}
