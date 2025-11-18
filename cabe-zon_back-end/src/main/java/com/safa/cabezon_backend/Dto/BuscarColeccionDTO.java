package com.safa.cabezon_backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class BuscarColeccionDTO {
    private String nombre;
    private Integer numeroDeProductos;
    private Set<ProductoDTO> productosSet;
}
