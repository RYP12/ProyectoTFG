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
public class NivelController {

    private NivelService nivelService;

    @GetMapping("/all")
    public List<Nivel> getNiveles(){return nivelService.BuscarNiveles();}

    @GetMapping("/{id}")
    public Nivel getNivel(@PathVariable Integer id){return nivelService.BuscarNivelPorId(id);}

    @PutMapping("/put/{id}")
    public void putNivel(@PathVariable Integer id, @RequestBody NivelDTO dto) {
        nivelService.EditarDescuentoNivel(id, dto);
    }

}
