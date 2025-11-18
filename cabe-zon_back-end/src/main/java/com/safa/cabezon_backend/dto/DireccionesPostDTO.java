package com.safa.cabezon_backend.dto;

import lombok.Data;

@Data
public class DireccionesPostDTO {
    private String calle;
    private Integer numero;
    private String piso;
    private String letra;
    private String codigoPostal;
    private String adicional;
    private String pais;
    private String provincia;
    private String municipio;
    private Integer idCliente;
}
