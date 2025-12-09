package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank
    @NotNull
    @Email
    private String username;
    @NotBlank
    @NotNull
    private String password;


}
