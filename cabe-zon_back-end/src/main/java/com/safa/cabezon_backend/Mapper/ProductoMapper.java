package com.safa.cabezon_backend.Mapper;

import com.safa.cabezon_backend.Dto.BuscarProductoDTO;
import com.safa.cabezon_backend.Dto.CrearProductoDTO;
import com.safa.cabezon_backend.Dto.ProductoDTO;
import com.safa.cabezon_backend.Modelos.Coleccion;
import com.safa.cabezon_backend.Modelos.Imagen;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Repositorios.IColeccionRepository;
import com.safa.cabezon_backend.Repositorios.IImagenRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ProductoMapper {

    @Autowired
    private IColeccionRepository coleccionRepository;

    public abstract List<ProductoDTO> listToDTO(List<Producto> productos);
    public abstract List<BuscarProductoDTO> listToBuscarDTO(List<Producto> productos);
    public abstract ProductoDTO toDTO(Producto producto);

    public abstract BuscarProductoDTO toBuscarProductoDTO(Producto producto);
    @Mapping(source = "idColecciones",target="colecciones")
    public abstract Producto toProducto(CrearProductoDTO productoDTO);

    Coleccion transformarColeccion(Integer idColeccion) {
        return coleccionRepository.findById(idColeccion).orElse(null);
    }


    @Mapping(source = "idColecciones",target="colecciones")
    public abstract Producto actualizarEntityFromDTO(CrearProductoDTO productodto,@MappingTarget Producto producto);
}
