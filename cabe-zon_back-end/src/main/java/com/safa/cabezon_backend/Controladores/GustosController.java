package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Servicios.GustosService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gustos")
@AllArgsConstructor
public class GustosController {

    private GustosService gustosService;

    @PostMapping("/add")
    public void agregarGusto(@RequestBody GustoRequest request) {
        gustosService.agregarGusto(request.getIdCliente(), request.getIdProducto());
    }

    @DeleteMapping("/{idCliente}/{idProducto}")
    public void eliminarGusto(@PathVariable Integer idCliente, @PathVariable Integer idProducto) {
        gustosService.eliminarGusto(idCliente, idProducto);
    }
}

@Setter
@Getter
class GustoRequest {
    private Integer idCliente;
    private Integer idProducto;
}
