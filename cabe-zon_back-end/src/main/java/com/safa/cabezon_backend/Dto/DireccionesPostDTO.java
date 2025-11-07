package com.safa.cabezon_backend.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class DireccionesPostDTO {
    private String calle;
    private Integer numero;
    private String piso;
    private String letra;
    private String codigoPostal;
    private String adicional;
    private String pais;
    private String provincia;
    private String municipio;
    private Integer idCliente;

    public static class ResenyaClientePostDTO {
        private String texto;
        private Integer valoracion;
        private Date fecha;
        private Integer idProducto;
        private Integer idCliente;
    }
}
