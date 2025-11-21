package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.BuscarImagenDTO;
import com.safa.cabezon_backend.Dto.BuscarProductoColeccionDTO;
import com.safa.cabezon_backend.Dto.BuscarProductoDTO;
import com.safa.cabezon_backend.Dto.CrearProductoDTO;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Repositorios.IProductoPedidoRepository;
import com.safa.cabezon_backend.Repositorios.IProductoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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


    public List<Producto> BuscarProductos() {return productoRepository.findAll();}

    public Producto BuscarProductoPorId(Integer id) {return productoRepository.findById(id).orElse(null);}

    public void CrearProducto(CrearProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setCodigoProducto(dto.getCodigoProducto());
        producto.setStock(dto.getStock());
        producto.setExclusivo(dto.getExclusivo());
        productoRepository.save(producto);
    }

    public void EditarProducto(Integer id, CrearProductoDTO dto) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            producto.setNombre(dto.getNombre());
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setCodigoProducto(dto.getCodigoProducto());
            producto.setStock(dto.getStock());
            producto.setExclusivo(dto.getExclusivo());
            productoRepository.save(producto);
        }
    }

    public void EliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    @Transactional
    public List<BuscarProductoDTO> BuscarPorductosNormales(){
        return productoRepository.findProductosNoExclusivos().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Transactional
    public List<BuscarProductoDTO> BuscarPorductosExclusivos(){
        return productoRepository.findProductosExclusivos().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Transactional
    public List<BuscarProductoDTO> obtenerTop4MasVendidos() {
        Pageable top4 = PageRequest.of(0, 4);
        List<Integer> idProductos = productoPedidoRepository.BuscarTopVentas(top4);

        List<Producto> productosDesordenados = productoRepository.findAllById(idProductos);
        Map<Integer, Producto> mapaProductos = productosDesordenados.stream()
                .collect(Collectors.toMap(Producto::getId, p -> p));

        List<Producto> productosOrdenados = idProductos.stream()
                .map(mapaProductos::get)
                .filter(Objects::nonNull)
                .toList();
        return productosOrdenados.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public List<BuscarProductoDTO> BuscarPorductosPorColeccion(Integer idColeccion) {
        return productoRepository.buscarProductosPorColeccionId(idColeccion).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Transactional
    public List<BuscarProductoDTO> BuscarPorductosPorFranjaPrecio(double franjaPreciomin, double franjaPreciomax) {
        return productoRepository.findProductosByPrecio(franjaPreciomin,franjaPreciomax).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Transactional
    public List<BuscarProductoDTO> BuscarPorductosPorGustosCliente(Integer idCliente) {
        return productoRepository.findGustosCliente(idCliente).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    private BuscarProductoDTO convertirADTO(Producto p) {
        BuscarProductoDTO dto = new BuscarProductoDTO();
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());

        if (p.getColeccionesSet() != null) {
            dto.setColecciones(p.getColeccionesSet().stream()
                    .map(col -> col.getNombre())
                    .collect(Collectors.toSet()));
        }
        if (p.getImagenes() != null) {
            dto.setImagenes(p.getImagenes().stream()
                    .map(img -> {
                        BuscarImagenDTO imgDto = new BuscarImagenDTO();
                        imgDto.setNombre(img.getNombre());
                        imgDto.setUrl(img.getUrl());
                        return imgDto;
                    })
                    .collect(Collectors.toSet()));
        }

        return dto;
    }
}
