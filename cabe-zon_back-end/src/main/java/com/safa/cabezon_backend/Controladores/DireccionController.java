package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.DireccionDTO;
import com.safa.cabezon_backend.Modelos.Direccion;
import com.safa.cabezon_backend.Servicios.DireccionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direccion")
@AllArgsConstructor
public class DireccionController {
    private DireccionService direccionService;

    @GetMapping("/all")
    public List<Direccion> getDireccion(){return direccionService.BuscarDirecciones();}

    @GetMapping("/{id}")
    public Direccion getDireccionById(@PathVariable Integer id){return direccionService.BuscarDireccionPorId(id);}

    @PostMapping("/post")
    public void postDireccion(@RequestBody DireccionDTO dto){
        direccionService.CrearDireccion(dto);
    }

    @PutMapping("/put/{id}")
    public void putDireccion(@PathVariable Integer id, @RequestBody DireccionDTO dto){
        direccionService.EditarDireccionPorId(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDireccion(@PathVariable Integer id){
        direccionService.EliminarDireccionPorId(id);
    }
}
