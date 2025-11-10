package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistroDTO {

    @NotBlank
    private String nombre;
    @NotBlank
    private String apellidos;

    @Email
    @NotBlank
    private String correo;
    @NotBlank
    private String password;
    @NotBlank
    private Rol rol;
}
