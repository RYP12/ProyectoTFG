package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Nivel;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class BuscarClienteAdminDTO {
    private Integer id;
    private String nombre;
    private Nivel nivel;
    private List<PedidoSimpleDTO> pedidos;
}
