package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Estado;
import lombok.Data;

@Data
public class BuscarPedidoAdminDTO {
    private Integer id;
    private Estado estado;
    private BuscarClienteDTO cliente;
    private Double precioTotal;
}
