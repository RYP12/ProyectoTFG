package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CrearProductoDTO {
    @NotNull
    private String nombre;
    @NotNull
    private String descripcion;
    @NotNull
    @Positive
    private Double precio;
    @NotNull
    private Integer codigoProducto;
    @NotNull
    @PositiveOrZero
    private Integer stock;
    @NotNull
    private Boolean exclusivo;
    @NotNull
    private Set<Integer> idColecciones;
}
