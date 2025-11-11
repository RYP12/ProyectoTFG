package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.ResenyaClienteDTO;
import com.safa.cabezon_backend.Modelos.ResenyaCliente;
import com.safa.cabezon_backend.Servicios.ResenyaClienteService;
import com.safa.cabezon_backend.Servicios.ResenyaClienteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resenya_cliente")
@AllArgsConstructor
public class ResenyaClienteController {
    private ResenyaClienteService resenyaClienteService;
    
    @GetMapping("/all")
    public List<ResenyaCliente> getResenyaClientes(){return resenyaClienteService.BuscarResenyaCliente();}

    @GetMapping("/{id}")
    public ResenyaCliente getResenyaClienteById(@PathVariable Integer id){return resenyaClienteService.BuscarResenyaClientePorId(id);}

    @PostMapping("/post")
    public void postResenyaCliente(@RequestBody ResenyaClienteDTO dto){
        resenyaClienteService.CrearResenyaCliente(dto);
    }

    @PutMapping("/put/{id}")
    public void putResenyaCliente(@PathVariable Integer id, @RequestBody ResenyaClienteDTO dto){
        resenyaClienteService.EditarResenyaCliente(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteResenyaCliente(@PathVariable Integer id){
        resenyaClienteService.EliminarResenyaCliente(id);
    }
}
