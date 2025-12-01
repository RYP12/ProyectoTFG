package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.BuscarColeccionDTO;
import com.safa.cabezon_backend.Dto.BuscarDireccionDTO;
import com.safa.cabezon_backend.Dto.ColeccionDTO;
import com.safa.cabezon_backend.Modelos.Coleccion;
import com.safa.cabezon_backend.Servicios.ColeccionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coleccion")
@AllArgsConstructor
//Controlador colecciones
public class ColeccionController {
    private ColeccionService coleccionService;

    //Solicitar todas las colecciones(BuscarColeccionDTO)
    @GetMapping("/all")
    public List<BuscarColeccionDTO> getColecciones(){ return coleccionService.crearColeccion();}

    //Solicitar coleccion según id que se pase por url(BuscarColeccionDTO)
    @GetMapping("/{id}")
    public BuscarColeccionDTO getColeccionById(@PathVariable Integer id){return coleccionService.BuscarColeccionPorId(id);}

    //Crear coleccion(ColeccionDTO)
    @PostMapping("/post")
    public void postColeccion(@RequestBody ColeccionDTO dto){
        coleccionService.CrearColeccion(dto);
    }

    //Editar coleccion segun id que se pase por url(ColeccionDTO)
    @PutMapping("/put/{id}")
    public void putColeccion(@PathVariable Integer id, @RequestBody ColeccionDTO dto){
        coleccionService.EditarColeccionPorId(id, dto);
    }

    //Borrar coleccion segun id que se pase por url
    @DeleteMapping("/delete/{id}")
    public void deleteColeccion(@PathVariable Integer id){
        coleccionService.borrarColeccionPorId(id);
    }
}
