package com.safa.cabezon_backend.Mapper;

import com.safa.cabezon_backend.Dto.BuscarImagenDTO;
import com.safa.cabezon_backend.Dto.ImagenDTO;
import com.safa.cabezon_backend.Modelos.Imagen;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Repositorios.IProductoRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ImagenMapper {

    @Autowired
    IProductoRepository productoRepository;

    public abstract List<BuscarImagenDTO> listToDTO(List<Imagen> imagenes);
    public abstract BuscarImagenDTO toDTO(Imagen imagen);

    @Mapping(source = "idProducto",target="producto")
    public abstract Imagen toEntity(ImagenDTO imagenDTO);

    Producto transformarProducto(Integer idProducto) {
        return productoRepository.findById(idProducto).orElse(null);
    }

    @Mapping(source = "idProducto",target="producto")
    public abstract Imagen actualizarImagen(ImagenDTO imagenDTO,@MappingTarget Imagen imagen);


}
