package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Estado;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PedidoSimpleDTO {
    private Integer id;
    private Date fecha;
    private Double precioTotal;
    private Estado estado;
    private Date fechaEstimada;
    private Date fechaEntrega;
    private List<ProductoPedidoDTO> productosPedidos;
}
