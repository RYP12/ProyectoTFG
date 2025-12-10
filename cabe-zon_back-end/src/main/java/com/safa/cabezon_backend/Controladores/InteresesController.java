package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.BuscarColeccionDTO;
import com.safa.cabezon_backend.Servicios.InteresesService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/intereses")
@AllArgsConstructor
public class InteresesController {

    private InteresesService interesesService;

    @GetMapping("/cliente/{idCliente}")
    public List<BuscarColeccionDTO> getInteresesPorCliente(@PathVariable Integer idCliente) {
        return interesesService.obtenerInteresesPorCliente(idCliente);
    }

    @PostMapping("/add")
    public void agregarInteres(@RequestBody InteresRequest request) {
        interesesService.agregarInteres(request.getIdCliente(), request.getIdColeccion());
    }

    @DeleteMapping("/{idCliente}/{idColeccion}")
    public void eliminarInteres(@PathVariable Integer idCliente, @PathVariable Integer idColeccion) {
        interesesService.eliminarInteres(idCliente, idColeccion);
    }
}

@Setter
@Getter
class InteresRequest {
    private Integer idCliente;
    private Integer idColeccion;

}
