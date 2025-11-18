package com.safa.cabezon_backend.dto;

import com.safa.cabezon_backend.modelos.Coleccion;
import com.safa.cabezon_backend.modelos.Imagen;
import lombok.Data;

import java.util.Set;

@Data
public class ProductoPostDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer codigoProducto;
    private Integer stock;
    private Boolean exclusivo;
    private Double valoracion;
    private Set<Imagen> imagenes;
    private Set<Coleccion> coleccionesSet;
}
