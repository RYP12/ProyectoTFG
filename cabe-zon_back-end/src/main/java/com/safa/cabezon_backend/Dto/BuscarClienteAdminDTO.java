package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.Nivel;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class BuscarClienteAdminDTO {
    private Integer id;
    private String nombre;
    @Valid
    private Nivel nivel;
    @Valid
    private List<PedidoSimpleDTO> pedidos;
}
