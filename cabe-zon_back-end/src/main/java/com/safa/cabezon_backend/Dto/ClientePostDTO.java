package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Rol;
import lombok.Data;

@Data
public class ClientePostDTO {
    private String nombre;
    private String apellidos;
    private String correo;
    private String passwordHash;
    private String foto;
    private Rol rol;
    private Integer cabecoins;
    private Integer idNivel;
}
