package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.BuscarClienteDTO;
import com.safa.cabezon_backend.Dto.ClienteDTO;
import com.safa.cabezon_backend.Dto.CrearClienteDTO;
import com.safa.cabezon_backend.Mapper.ClienteMapper;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import com.safa.cabezon_backend.Servicios.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private IClienteRepository clienteRepository;

    private ClienteMapper clienteMapper;

    @GetMapping("/all")
    public List<BuscarClienteDTO> getClientes(){return clienteMapper.listToDTO(clienteRepository.findAll());}

    @GetMapping("/{id}")
    public BuscarClienteDTO getClienteById(@PathVariable Integer id){return clienteMapper.toDTO(clienteRepository.findById(id).orElse(null));}

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
