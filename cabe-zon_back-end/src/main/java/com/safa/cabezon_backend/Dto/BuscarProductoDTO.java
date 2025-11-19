package com.safa.cabezon_backend.Dto;

import jakarta.persistence.SecondaryTable;
import lombok.Data;

import java.util.Set;

@Data
public class BuscarProductoDTO {
    private String nombre;
    private Double precio;
    private Set<String> colecciones;
    private Set<BuscarImagenDTO> imagenes;
}
