package com.safa.cabezon_backend.Mapper;

import com.safa.cabezon_backend.Dto.BuscarClienteDTO;
import com.safa.cabezon_backend.Dto.ClienteDTO;
import com.safa.cabezon_backend.Dto.CrearClienteDTO;
import com.safa.cabezon_backend.Dto.PedidoSimpleDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ClienteMapper {
    public abstract BuscarClienteDTO toDTO(Cliente cliente);
    public abstract List<BuscarClienteDTO> listToDTO(List<Cliente> dto);
    public abstract PedidoSimpleDTO toPedidoDTO(Pedido pedido);

    public abstract Cliente toEntity(CrearClienteDTO dto);

    public abstract Cliente actualizar(ClienteDTO dto, @MappingTarget Cliente cliente);
}
