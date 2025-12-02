package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.NivelDTO;
import com.safa.cabezon_backend.Modelos.Nivel;
import com.safa.cabezon_backend.Servicios.NivelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nivel")
@AllArgsConstructor
//Controlador de Nivel
public class NivelController {

    private NivelService nivelService;

    //Solicitar todos los niveles
    @GetMapping("/all")
    public List<Nivel> getNiveles(){return nivelService.BuscarNiveles();}

    //Solicitar nivel segun el id que se pase por url
    @GetMapping("/{id}")
    public Nivel getNivel(@PathVariable Integer id){return nivelService.BuscarNivelPorId(id);}

    //Editar los niveles segun id que se pase por url
    @PutMapping("/put/{id}")
    public void putNivel(@PathVariable Integer id, @RequestBody NivelDTO dto) {
        nivelService.EditarDescuentoNivel(id, dto);
    }

    //No creamos ni borrar ni crear porque los niveles seran siempre los mismos
}
