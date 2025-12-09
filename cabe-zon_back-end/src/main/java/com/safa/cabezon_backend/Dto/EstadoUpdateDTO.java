package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadoUpdateDTO {
    @NotNull
    private String estado;
}
