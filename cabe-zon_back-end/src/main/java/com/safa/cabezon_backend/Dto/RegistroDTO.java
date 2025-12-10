package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDTO {
    private Integer id;

    @NotBlank
    @NotNull
    @Email
    private String username;

    @NotBlank
    @NotNull
    private String password;

    @NotBlank
    @NotNull
    private String nombre;

    @NotBlank
    @NotNull
    private String apellidos;
}
