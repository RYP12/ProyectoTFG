package com.safa.cabezon_backend.dto;

import lombok.Data;

@Data
public class DireccionesPostDTO {
    private String calle;
    private Integer numero;
    private String piso;
    private String letra;
    private String codigoPostal;

}
