package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.BuscarImagenDTO;
import com.safa.cabezon_backend.Dto.ImagenDTO;
import com.safa.cabezon_backend.Mapper.ImagenMapper;
import com.safa.cabezon_backend.Mapper.PedidoMapper;
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

    @Autowired
    private ImagenMapper imagenMapper;
    @Autowired
    private PedidoMapper pedidoMapper;

    public List<BuscarImagenDTO> BuscarImagenes() {return imagenMapper.listToDTO(imagenRepository.findAll());}

    public BuscarImagenDTO BuscarImagenPorId(Integer id) {return imagenMapper.toDTO(imagenRepository.findById(id).orElse(null));}

    public void EliminarImagenPorId(Integer id) {imagenRepository.deleteById(id);}

    public void  CrearImagen(ImagenDTO imagenDTO){
        imagenRepository.save(imagenMapper.toEntity(imagenDTO));
    }

    public void EditarImagenPorId(Integer id, ImagenDTO imagenDTO){
        Imagen nuevaImagen = imagenRepository.findById(id).orElse(null);
        imagenMapper.actualizarImagen(imagenDTO, nuevaImagen);
        imagenRepository.save(nuevaImagen);
    }

    public List<String> Buscar10ImagenesAleatorias(){
        return imagenRepository.findUrlsDeProductosAleatorios();
    }
}
