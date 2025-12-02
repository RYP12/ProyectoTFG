package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.BuscarImagenDTO;
import com.safa.cabezon_backend.Dto.ImagenDTO;
import com.safa.cabezon_backend.Modelos.Imagen;
import com.safa.cabezon_backend.Servicios.ImagenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imagen")
@AllArgsConstructor
//Controlador Imagen
public class ImagenController {

    private ImagenService imagenService;

    //Buscar todas las imagenes(BuscarImagenDTO)
    @GetMapping("/all")
    public List<BuscarImagenDTO> getImagenes(){return imagenService.BuscarImagenes();}

    //Buscar imagen segun id que se pasa por la url(BuscarImagenDTO)
    @GetMapping("/{id}")
    public BuscarImagenDTO getImagenById(@PathVariable Integer id){return imagenService.BuscarImagenPorId(id);}

    //Crear imagen(ImagenDTO)
    @PostMapping("/post")
    public void postImagen(@RequestBody ImagenDTO dto) {
        imagenService.CrearImagen(dto);
    }

    //Editar imagen segun id que se pase por la url(ImagenDTO)
    @PutMapping("/put/{id}")
    public void putImagen(@PathVariable Integer id, @RequestBody ImagenDTO dto) {
        imagenService.EditarImagenPorId(id, dto);
    }

    //Borrar imagen segun id que se pase por url
    @DeleteMapping("/delete/{id}")
    public void deleteImagen(@PathVariable Integer id){
        imagenService.EliminarImagenPorId(id);
    }

    //Solicita 10 urls de imagenes aleatorias
    @GetMapping("/10aleatorias")
    public List<String> findUrlsDeProductosAleatorias(){return imagenService.Buscar10ImagenesAleatorias();}
}
