package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.ProductoDTO;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Repositorios.IPedidoRepository;
import com.safa.cabezon_backend.Repositorios.IProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoService {

    @Autowired
    private IProductoRepository productoRepository;

    public List<Producto> BuscarProductos() {return productoRepository.findAll();}

    public Producto BuscarProductoPorId(Integer id) {return productoRepository.findById(id).orElse(null);}

    public void CrearProducto(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setCodigoProducto(dto.getCodigoProducto());
        producto.setStock(dto.getStock());
        producto.setExclusivo(dto.getExclusivo());
        producto.setValoracion(dto.getValoracion());
        producto.setImagenes(dto.getImagenes());
        producto.setColeccionesSet(dto.getColeccionesSet());

        productoRepository.save(producto);
    }

    public void EditarProducto(Integer id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            producto.setNombre(dto.getNombre());
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setCodigoProducto(dto.getCodigoProducto());
            producto.setStock(dto.getStock());
            producto.setExclusivo(dto.getExclusivo());
            producto.setValoracion(dto.getValoracion());
            producto.setImagenes(dto.getImagenes());
            producto.setColeccionesSet(dto.getColeccionesSet());

            productoRepository.save(producto);
        }
    }

    public void EliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

}
