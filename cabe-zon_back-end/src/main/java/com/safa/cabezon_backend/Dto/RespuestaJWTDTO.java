package com.safa.cabezon_backend.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaJWTDTO {
    @NotBlank
    private String token;
}
