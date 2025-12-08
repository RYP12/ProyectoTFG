package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Estado;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BuscarPedidoDTO {
    private Integer id;
    private Date fechaEstimada;
    private Date fechaEntrega;
    private Estado estado;
    private Date fecha;
    private Double precioTotal;
    private BuscarClienteDTO Cliente;
    private List<ProductoPedidoDTO> productosPedidos;
}
