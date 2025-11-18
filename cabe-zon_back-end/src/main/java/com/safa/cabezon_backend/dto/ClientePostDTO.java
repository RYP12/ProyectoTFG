package com.safa.cabezon_backend.dto;

import com.safa.cabezon_backend.modelos.Nivel;
import com.safa.cabezon_backend.modelos.Rol;
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
