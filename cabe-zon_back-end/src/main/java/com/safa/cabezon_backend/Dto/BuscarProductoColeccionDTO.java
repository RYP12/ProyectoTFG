package com.safa.cabezon_backend.Dto;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.Set;

@Data
public class BuscarProductoColeccionDTO {
    private String nombre;
    private Double precio;
    @Valid
    private Set<BuscarImagenDTO> imagenes;
}
