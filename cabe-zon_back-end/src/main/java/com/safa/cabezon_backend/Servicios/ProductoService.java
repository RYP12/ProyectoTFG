package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.ProductoDTO;
import com.safa.cabezon_backend.Modelos.Coleccion;
import com.safa.cabezon_backend.Modelos.Imagen;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Repositorios.IImagenRepository;
import com.safa.cabezon_backend.Repositorios.IPedidoRepository;
import com.safa.cabezon_backend.Repositorios.IProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductoService {

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private ImagenService imagenService;

    @Autowired
    private ColeccionServie coleccionService;


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
        Set<Imagen> imagenes = new HashSet<>();
        for(Integer id: dto.getImagenes()){
            imagenes.add(imagenService.BuscarImagenPorId(id));
        }
        producto.setImagenes(imagenes);

        Set<Coleccion> colecciones = new HashSet<>();
        for(Integer id: dto.getColeccionesSet()){
            colecciones.add(coleccionService.BuscarColeccionPorId(id));
        }
        producto.setColeccionesSet(colecciones);

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
            Set<Imagen> imagenes = new HashSet<>();
            for(Integer idImagen: dto.getImagenes()){
                imagenes.add(imagenService.BuscarImagenPorId(idImagen));
            }
            producto.setImagenes(imagenes);

            Set<Coleccion> colecciones = new HashSet<>();
            for(Integer idcoleccion: dto.getColeccionesSet()){
                colecciones.add(coleccionService.BuscarColeccionPorId(idcoleccion));
            }
            producto.setColeccionesSet(colecciones);

            productoRepository.save(producto);
        }
    }

    public void EliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

}
