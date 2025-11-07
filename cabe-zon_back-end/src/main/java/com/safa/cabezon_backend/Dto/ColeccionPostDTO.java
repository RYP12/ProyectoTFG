package com.safa.cabezon_backend.Dto;

import lombok.Data;

import java.util.Set;

@Data
public class ColeccionPostDTO {
    private String nombre;
    private Integer numeroDeProductos;
    private Set<Integer> productosSet;
}
