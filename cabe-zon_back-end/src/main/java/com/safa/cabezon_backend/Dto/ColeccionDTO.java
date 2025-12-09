package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ColeccionDTO {
    @NotNull
    private String nombre;
    @NotNull
    private Integer numeroDeProductos;
    @NotNull
    private Set<Integer> productosSet;

}
