package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CrearClienteDTO {
    @NotNull
    private String nombre;
    @NotNull
    private String apellidos;
}
