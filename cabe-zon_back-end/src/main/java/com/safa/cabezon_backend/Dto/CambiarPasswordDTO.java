package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CambiarPasswordDTO {
    @NotBlank
    @Email
    private String correo;
    @NotBlank
    private String passwordActual;
    @NotBlank
    private String passwordNuevo;
}
