package com.safa.cabezon_backend.Dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProductoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer codigoProducto;
    private Integer stock;
    private Boolean exclusivo;
    private Double valoracion;
    @Valid
    private Set<BuscarColeccionDTO> colecciones;
    @Valid
    private Set<BuscarImagenDTO> imagenes;
}
