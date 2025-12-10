package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class DireccionDTO {
    @NotNull
    private String calle;
    private Integer numero;
    @PositiveOrZero
    private String piso;
    private String letra;
    @NotNull
    @Positive
    private String codigoPostal;
    private String adicional;
    @NotNull
    private String pais;
    @NotNull
    private String provincia;
    @NotNull
    private String municipio;
    @NotNull
    private Integer idCliente;
}
