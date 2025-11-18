package com.safa.cabezon_backend.Security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
    private String username;
    private String rol;
    private Long fecha_creacion;
    private Long fecha_expiracion;
}
