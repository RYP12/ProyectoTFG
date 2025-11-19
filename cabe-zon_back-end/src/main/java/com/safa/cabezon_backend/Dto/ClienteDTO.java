package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Coleccion;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Modelos.Rol;
import lombok.Data;

import java.util.Set;

@Data
public class ClienteDTO {
    private String nombre;
    private String apellidos;
    private String foto;
    private Integer cabecoins;
    private NivelDTO nivel;
    private Set<ProductoDTO> ListaDeseosSet;
    private Set<ColeccionDTO> interesesSet;

}
