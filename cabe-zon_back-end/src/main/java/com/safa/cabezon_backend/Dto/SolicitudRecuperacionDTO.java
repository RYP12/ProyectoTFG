package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolicitudRecuperacionDTO {
    @NotNull
    @Email
    private String email;
}
