package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Nivel;
import lombok.Data;

import java.util.List;

@Data
public class BuscarClienteDTO {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String foto;
    private Integer cabecoins;
    private Nivel nivel;
    private List<PedidoSimpleDTO> pedidos;
}
