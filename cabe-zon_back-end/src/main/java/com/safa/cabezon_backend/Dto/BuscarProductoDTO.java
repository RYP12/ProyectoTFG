package com.safa.cabezon_backend.Dto;

import jakarta.persistence.SecondaryTable;
import lombok.Data;

import java.util.Set;

@Data
public class BuscarProductoDTO {
    private Integer id;
    private String nombre;
    private Double precio;
    private Set<BuscarColeccionDTO> colecciones;
    private Set<BuscarImagenDTO> imagenes;
}
