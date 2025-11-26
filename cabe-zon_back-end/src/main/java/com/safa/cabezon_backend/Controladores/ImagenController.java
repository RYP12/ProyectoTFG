package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.ImagenDTO;
import com.safa.cabezon_backend.Modelos.Imagen;
import com.safa.cabezon_backend.Servicios.ImagenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imagen")
@AllArgsConstructor
public class ImagenController {

    private ImagenService imagenService;

    @GetMapping("/all")
    public List<Imagen> getImagenes(){return imagenService.BuscarImagenes();}

    @GetMapping("/{id}")
    public Imagen getImagenById(@PathVariable Integer id){return imagenService.BuscarImagenPorId(id);}

    @PostMapping("/post")
    public void postImagen(@RequestBody ImagenDTO dto) {
        imagenService.CrearImagen(dto);
    }

    @PutMapping("/put/{id}")
    public void putImagen(@PathVariable Integer id, @RequestBody ImagenDTO dto) {
        imagenService.EditarImagenPorId(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImagen(@PathVariable Integer id){
        imagenService.EliminarImagenPorId(id);
    }

    @GetMapping("/10aleatorias")
    public List<String> findUrlsDeProductosAleatorias(){return imagenService.Buscar10ImagenesAleatorias();}
}
