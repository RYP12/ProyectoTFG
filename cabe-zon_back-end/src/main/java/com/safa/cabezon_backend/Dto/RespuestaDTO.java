package com.safa.cabezon_backend.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespuestaDTO {
    private Integer estado;
    private String token;
    private String mensaje;
    private Object cuerpo;

}
