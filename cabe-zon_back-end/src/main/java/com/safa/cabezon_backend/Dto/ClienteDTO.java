package com.safa.cabezon_backend.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class ClienteDTO {
    @NotNull
    private String nombre;
    @NotNull
    private String apellidos;
    private String foto;
    @NotNull
    private Integer cabecoins;
    @NotNull
    @Valid
    private NivelDTO nivel;
    @Valid
    private Set<CrearProductoDTO> ListaDeseosSet;
    @Valid
    private Set<ColeccionDTO> interesesSet;

}
