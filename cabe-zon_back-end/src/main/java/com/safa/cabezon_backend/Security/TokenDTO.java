package com.safa.cabezon_backend.Security;

import com.safa.cabezon_backend.Modelos.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
    private String username;
    private Rol rol;
    private String fecha_creacion;
    private String fecha_expiracion;
}