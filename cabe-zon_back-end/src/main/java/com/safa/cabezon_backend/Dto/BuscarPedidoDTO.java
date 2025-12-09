package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Estado;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BuscarPedidoDTO {
    private Integer id;
    private Date fechaEstimada;
    private Date fechaEntrega;
    @Valid
    private Estado estado;
    private Date fecha;
    @PositiveOrZero
    private Double precioTotal;
    @Valid
    private BuscarClienteDTO Cliente;
    @Valid
    private List<ProductoPedidoDTO> productosPedidos;
}
