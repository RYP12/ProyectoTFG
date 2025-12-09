package com.safa.cabezon_backend.Exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorResponse {
    private String mensaje;
    private Integer codigoEstado;
    private LocalDateTime fechaEntrega;


    public void errorResponse(String mensaje, Integer codigoEstado) {
        this.mensaje = mensaje;
        this.codigoEstado = codigoEstado;
        this.fechaEntrega = LocalDateTime.now();
    }
}
