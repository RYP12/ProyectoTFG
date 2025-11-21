package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Nivel;
import lombok.Data;

@Data
public class BuscarClienteDTO {
    private String nombre;
    private String apellidos;
    private String foto;
    private Integer cabecoins;
    private Nivel nivel;
}
