package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Coleccion;
import com.safa.cabezon_backend.Modelos.Imagen;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProductoDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer codigoProducto;
    private Integer stock;
    private Boolean exclusivo;
    private Double valoracion;
}
