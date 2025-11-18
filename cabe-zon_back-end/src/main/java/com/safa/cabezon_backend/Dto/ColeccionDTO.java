package com.safa.cabezon_backend.Dto;

import lombok.Data;

import java.util.Set;

@Data
public class ColeccionDTO {
    private String nombre;
    private Integer numeroDeProductos;
    private Set<Integer> productosSet;
}
