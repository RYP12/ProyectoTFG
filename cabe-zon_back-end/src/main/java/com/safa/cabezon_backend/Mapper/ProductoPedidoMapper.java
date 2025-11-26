package com.safa.cabezon_backend.Mapper;

import com.safa.cabezon_backend.Dto.CrearProductoPedidoDTO;
import com.safa.cabezon_backend.Dto.CrearResenyaClienteDTO;
import com.safa.cabezon_backend.Dto.ProductoPedidoDTO;
import com.safa.cabezon_backend.Modelos.Pedido;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Modelos.ProductoPedido;
import com.safa.cabezon_backend.Modelos.ResenyaCliente;
import com.safa.cabezon_backend.Repositorios.IPedidoRepository;
import com.safa.cabezon_backend.Repositorios.IProductoRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductoPedidoMapper {

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IPedidoRepository pedidoRepository;


    public abstract List<ProductoPedidoDTO> listToDto(List<ProductoPedido> productoPedido);
    public abstract ProductoPedidoDTO toDto(ProductoPedido productoPedido);

    @Mapping(source = "idProducto",target="producto")
    @Mapping(source = "idPedido",target="pedido")
    public abstract ProductoPedido toEntity(CrearProductoPedidoDTO productoPedidoDTO);

    Producto transformarProducto(Integer idProducto) {
        return productoRepository.findById(idProducto).orElse(null);
    }

    Pedido transformarPedido(Integer idPedido) {
        return pedidoRepository.findById(idPedido).orElse(null);
    }
    @Mapping(source = "idProducto",target="producto")
    @Mapping(source = "idPedido",target="pedido")
    public abstract void actualizarEntityFromDto(CrearProductoPedidoDTO dto, @MappingTarget ProductoPedido productoPedido);
}
