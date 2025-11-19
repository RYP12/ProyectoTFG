package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.BuscarProductoDTO;
import com.safa.cabezon_backend.Dto.ProductoPedidoDTO;
import com.safa.cabezon_backend.Modelos.Pedido;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Modelos.ProductoPedido;
import com.safa.cabezon_backend.Repositorios.IPedidoRepository;
import com.safa.cabezon_backend.Repositorios.IProductoPedidoRepository;
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
    private ProductoService productoService;

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private IPedidoRepository pedidoRepository;

    public List<ProductoPedido> BuscarProductoPedido() {
        return productoPedidoRepository.findAll();
    }

    public ProductoPedido BuscarProductoPedidoPorId(Integer id) {
        return productoPedidoRepository.findById(id).orElse(null);
    }

    public void CrearProductoPedido(ProductoPedidoDTO dto) {
        ProductoPedido productoPedido = new ProductoPedido();
        productoPedido.setSubtotal(0.0);
        productoPedido.setCantidad(dto.getCantidad());

        Producto producto = productoService.BuscarProductoPorId(dto.getIdProducto());
        productoPedido.setProducto(producto);

        Pedido pedido =pedidoRepository.findById(dto.getIdPedido()).orElse(null);
        productoPedido.setPedido(pedido);

        productoPedidoRepository.save(productoPedido);
    }

    public void EditarProductoPedido(Integer id, ProductoPedidoDTO dto) {
        ProductoPedido productoPedido = productoPedidoRepository.findById(id).orElse(null);
        if (productoPedido != null) {
            productoPedido.setSubtotal(0.0);
            productoPedido.setCantidad(dto.getCantidad());

            Producto producto = productoService.BuscarProductoPorId(dto.getIdProducto());
            productoPedido.setProducto(producto);

            Pedido pedido = pedidoRepository.findById(dto.getIdPedido()).orElse(null);
            productoPedido.setPedido(pedido);

            productoPedidoRepository.save(productoPedido);
        }
    }

    public void EliminarProductoPedido(Integer id) {
        productoPedidoRepository.deleteById(id);
    }


}
