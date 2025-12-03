package com.safa.cabezon_backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuscarImagenColeccionProductoDTO {
    private ImagenDTO imagen;
    private ColeccionDTO coleccion;
    private BuscarProductoDTO producto;
}
