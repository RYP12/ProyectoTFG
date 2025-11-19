package com.safa.cabezon_backend.Dto;

import lombok.Data;

import java.util.Set;

@Data
public class ClienteDTO {
    private String nombre;
    private String apellidos;
    private String foto;
    private Integer cabecoins;
    private NivelDTO nivel;
    private Set<CrearProductoDTO> ListaDeseosSet;
    private Set<ColeccionDTO> interesesSet;

}
