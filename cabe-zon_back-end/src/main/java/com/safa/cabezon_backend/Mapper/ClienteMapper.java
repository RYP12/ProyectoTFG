package com.safa.cabezon_backend.Mapper;

import com.safa.cabezon_backend.Dto.*;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Pedido;
import com.safa.cabezon_backend.Servicios.NivelService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PedidoMapper.class, NivelService.class})
public abstract class ClienteMapper {

    @Mapping(source = "usuario.username", target = "email")
    public abstract BuscarClienteDTO toDTO(Cliente cliente);

    public abstract List<BuscarClienteDTO> listToDTO(List<Cliente> dto);
    public abstract PedidoSimpleDTO toPedidoDTO(Pedido pedido);
    public abstract BuscarClienteAdminDTO toClienteAdminDTO(Cliente cliente);

    public abstract Cliente toEntity(CrearClienteDTO dto);

    public abstract Cliente actualizar(ClienteDTO dto, @MappingTarget Cliente cliente);
}
