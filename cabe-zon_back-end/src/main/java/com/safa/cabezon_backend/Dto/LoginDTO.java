package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;


}
