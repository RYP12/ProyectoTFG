package com.safa.cabezon_backend.Dto;

import jakarta.persistence.SecondaryTable;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.Set;

@Data
public class BuscarProductoDTO {
    private Integer id;
    private String nombre;
    private boolean exclusivo;
    private Double precio;
    @Valid
    private Set<BuscarColeccionDTO> colecciones;
    @Valid
    private Set<BuscarImagenDTO> imagenes;


}
