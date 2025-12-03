package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.*;
import com.safa.cabezon_backend.Mapper.ProductoMapper;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Repositorios.IProductoPedidoRepository;
import com.safa.cabezon_backend.Repositorios.IProductoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductoService {

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IProductoPedidoRepository productoPedidoRepository;

    @Autowired
    private ProductoMapper mapper;
    @Autowired
    private ProductoMapper productoMapper;


    @Transactional
    public List<ProductoDTO> BuscarProductos() {
        return mapper.listToDTO(productoRepository.findAll());}

    @Transactional
    public Page<BuscarProductoDTO> buscarPorPagina(Pageable pageable) {
        Page<Producto> productos = productoRepository.findAll(pageable);
        return productos.map(productoMapper::toBuscarProductoDTO);
    }

    @Transactional
    public ProductoDTO BuscarProductoPorId(Integer id) {return mapper.toDTO(productoRepository.findById(id).orElse(null));}

    @Transactional
    public void CrearProducto(CrearProductoDTO dto) {
        productoRepository.save(mapper.toProducto(dto));
    }

    @Transactional
    public void EditarProducto(Integer id, CrearProductoDTO dto) {
        Producto producto = productoRepository.findById(id).orElse(null);
        mapper.actualizarEntityFromDTO(dto, producto);
        productoRepository.save(producto);
    }

    @Transactional
    public void EliminarProducto(Integer id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        producto.getColecciones().clear();
        productoRepository.deleteById(id);
    }

    @Transactional
    public List<BuscarProductoDTO> BuscarPorductosNormales(){
        return mapper.listToBuscarDTO(productoRepository.findProductosNoExclusivos());
    }

    @Transactional
    public List<BuscarProductoDTO> BuscarPorductosExclusivos(){
        return mapper.listToBuscarDTO(productoRepository.findProductosExclusivos());
    }

    @Transactional
    public List<BuscarProductoDTO> obtenerTop4MasVendidos() {
        List<Integer> idProductos = productoPedidoRepository.BuscarTopVentas();
        List<Producto> productos = productoRepository.findAllById(idProductos);
        return mapper.listToBuscarDTO(productos);
    }
    @Transactional
    public List<BuscarProductoDTO> BuscarPorductosPorColeccion(Integer idColeccion) {
        return mapper.listToBuscarDTO(productoRepository.buscarProductosPorColeccionId(idColeccion));
    }

    @Transactional
    public List<BuscarProductoDTO> BuscarPorductosPorFranjaPrecio(double franjaPreciomin, double franjaPreciomax) {
        return mapper.listToBuscarDTO(productoRepository.findProductosByPrecio(franjaPreciomin,franjaPreciomax));
    }

    @Transactional
    public List<BuscarProductoDTO> BuscarPorductosPorGustosCliente(Integer idCliente) {
        return mapper.listToBuscarDTO(productoRepository.findGustosCliente(idCliente));
    }
}
