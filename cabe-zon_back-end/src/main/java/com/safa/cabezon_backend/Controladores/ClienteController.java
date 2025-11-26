package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.ClienteDTO;
import com.safa.cabezon_backend.Dto.CrearClienteDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Servicios.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteController {

    private ClienteService clienteService;

//    @GetMapping("/all")
//    public List<ClienteDTO> getClientes(){return clienteService.BuscarClientes();}
//
//    @GetMapping("/{id}")
//    public ClienteDTO getClienteById(@PathVariable Integer id){return clienteService.BuscarClientePorId(id);}

    @PostMapping("/post")
    public void postCliente(@RequestBody CrearClienteDTO dto){
        clienteService.CrearCliente(dto);
    }

    @PutMapping("/put/{id}")
    public void putCliente(@PathVariable Integer id, @RequestBody ClienteDTO dto){
        clienteService.EditarClientePorId(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCliente(@PathVariable Integer id){
        clienteService.EliminarClientePorId(id);
    }

}
