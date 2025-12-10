package com.safa.cabezon_backend.Dto;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class BuscarDireccionDTO {
    private String calle;
    private Integer numero;
    private String piso;
    private String letra;
    private String codigoPostal;
    private String adicional;
    private String pais;
    private String provincia;
    private String municipio;
    @Valid
    private BuscarClienteDTO cliente;
}
