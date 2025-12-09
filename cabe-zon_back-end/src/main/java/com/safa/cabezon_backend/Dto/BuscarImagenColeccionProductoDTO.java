package com.safa.cabezon_backend.Dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuscarImagenColeccionProductoDTO {
    @Valid
    private ImagenDTO imagen;
    @Valid
    private ColeccionDTO coleccion;
    @Valid
    private BuscarProductoDTO producto;
}
