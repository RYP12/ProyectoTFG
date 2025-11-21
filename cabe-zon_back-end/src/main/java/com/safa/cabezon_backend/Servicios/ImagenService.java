package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.ImagenDTO;
import com.safa.cabezon_backend.Modelos.Imagen;
import com.safa.cabezon_backend.Repositorios.IImagenRepository;
import com.safa.cabezon_backend.Repositorios.IProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImagenService {

    @Autowired
    private IImagenRepository imagenRepository;

    @Autowired
    private IProductoRepository productoRepository;

    public List<Imagen> BuscarImagenes() {return imagenRepository.findAll();}

    public Imagen BuscarImagenPorId(Integer id) {return imagenRepository.findById(id).orElse(null);}

    public void EliminarImagenPorId(Integer id) {imagenRepository.deleteById(id);}

    public void  CrearImagen(ImagenDTO imagenDTO){
        Imagen nuevaImagen = new Imagen();
        nuevaImagen.setNombre(imagenDTO.getNombre());
        nuevaImagen.setUrl(imagenDTO.getUrl());
        nuevaImagen.setProducto(productoRepository.findById(imagenDTO.getIdProducto()).orElse(null));
        imagenRepository.save(nuevaImagen);
    }

    public void EditarImagenPorId(Integer id, ImagenDTO imagenDTO){
        Imagen nuevaImagen = imagenRepository.findById(id).orElse(null);
        nuevaImagen.setNombre(imagenDTO.getNombre());
        nuevaImagen.setUrl(imagenDTO.getUrl());
        nuevaImagen.setProducto(productoRepository.findById(imagenDTO.getIdProducto()).orElse(null));
        imagenRepository.save(nuevaImagen);
    }

    public List<String> Buscar10ImagenesAleatorias(){
        return imagenRepository.findUrlsDeProductosAleatorios();
    }
}
