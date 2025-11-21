package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistroDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
