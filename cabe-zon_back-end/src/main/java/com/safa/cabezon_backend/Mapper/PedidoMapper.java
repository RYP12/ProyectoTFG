package com.safa.cabezon_backend.Mapper;

import com.safa.cabezon_backend.Dto.BuscarPedidoAdminDTO;
import com.safa.cabezon_backend.Dto.BuscarPedidoDTO;
import com.safa.cabezon_backend.Dto.PedidoDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Pedido;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PedidoMapper {

    @Autowired
    private IClienteRepository clienteRepository;


    public abstract List<BuscarPedidoDTO> listToPedidoDTO(List<Pedido> pedidos);
    public abstract BuscarPedidoDTO toDTO(Pedido pedido);
    public abstract BuscarPedidoAdminDTO toPedidoAdminDTO(Pedido pedido);

    @Mapping(source = "idCliente",target="cliente")
    public abstract Pedido toEntity(PedidoDTO pedido);

    Cliente transformarCliente(Integer idCliente){
        return clienteRepository.findById(idCliente).orElse(null);
    }

    @Mapping(source = "idCliente",target="cliente")
    public abstract Pedido actualizarPedido(PedidoDTO dto,@MappingTarget Pedido pedido);

}
