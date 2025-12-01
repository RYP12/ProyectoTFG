package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.BuscarDireccionDTO;
import com.safa.cabezon_backend.Dto.DireccionDTO;
import com.safa.cabezon_backend.Mapper.ImagenMapper;
import com.safa.cabezon_backend.Modelos.Direccion;
import com.safa.cabezon_backend.Servicios.DireccionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direccion")
@AllArgsConstructor
//Controlador Direccion
public class DireccionController {
    private DireccionService direccionService;

    //solicitar todas las direcciones(BuscarDireccionDTO)
    @GetMapping("/all")
    public List<BuscarDireccionDTO> getDireccion(){return direccionService.BuscarDirecciones();}

    //Solicitar direccion segun id que se pase por url(BuscarDireccionDTO)
    @GetMapping("/{id}")
    public BuscarDireccionDTO getDireccionById(@PathVariable Integer id){return direccionService.BuscarDireccionPorId(id);}

    //Crear direccion(DireccionDTO)
    @PostMapping("/post")
    public void postDireccion(@RequestBody DireccionDTO dto){
        direccionService.CrearDireccion(dto);
    }

    //Editar direccion segun id que se pase por url(DireccionDTO)
    @PutMapping("/put/{id}")
    public void putDireccion(@PathVariable Integer id, @RequestBody DireccionDTO dto){
        direccionService.EditarDireccionPorId(id, dto);
    }

    //Borrar direccion segun id
    @DeleteMapping("/delete/{id}")
    public void deleteDireccion(@PathVariable Integer id){
        direccionService.EliminarDireccionPorId(id);
    }
}
