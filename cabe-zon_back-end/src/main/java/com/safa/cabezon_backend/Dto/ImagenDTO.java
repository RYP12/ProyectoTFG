package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ImagenDTO {
    @NotNull
    private String nombre;
    @NotNull
    private String url;
    @NotNull
    private Integer idProducto;
}
