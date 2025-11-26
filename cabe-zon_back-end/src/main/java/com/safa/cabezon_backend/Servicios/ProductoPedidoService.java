package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.BuscarProductoDTO;
import com.safa.cabezon_backend.Dto.CrearProductoPedidoDTO;
import com.safa.cabezon_backend.Dto.ProductoPedidoDTO;
import com.safa.cabezon_backend.Mapper.ProductoPedidoMapper;
import com.safa.cabezon_backend.Modelos.Pedido;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Modelos.ProductoPedido;
import com.safa.cabezon_backend.Repositorios.IPedidoRepository;
import com.safa.cabezon_backend.Repositorios.IProductoPedidoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductoPedidoService {

    @Autowired
    private IProductoPedidoRepository productoPedidoRepository;

    @Autowired
    private ProductoPedidoMapper mapper;


    @Transactional
    public List<ProductoPedidoDTO> BuscarProductoPedido() {
        return mapper.listToDto(productoPedidoRepository.findAll());
    }

    @Transactional
    public ProductoPedidoDTO BuscarProductoPedidoPorId(Integer id) {
        return mapper.toDto(productoPedidoRepository.findById(id).orElse(null));
    }


    public void CrearProductoPedido(CrearProductoPedidoDTO dto) {
       productoPedidoRepository.save(mapper.toEntity(dto));
    }

    public void EditarProductoPedido(Integer id, CrearProductoPedidoDTO dto) {
        ProductoPedido productoPedido = productoPedidoRepository.findById(id).orElse(null);
        mapper.actualizarEntityFromDto(dto,productoPedido);
        productoPedidoRepository.save(productoPedido);

    }

    public void EliminarProductoPedido(Integer id) {
        productoPedidoRepository.deleteById(id);
    }


}
