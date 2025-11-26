package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.*;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteService {
    @Autowired
    private IClienteRepository clienteRepository;

    //@Transactional
//    public List<ClienteDTO> BuscarClientes() {
//        return clienteRepository.findAll().stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }

//    @Transactional
//    public ClienteDTO BuscarClientePorId(Integer id){return mapToDTO(clienteRepository.findById(id).orElse(null));}
//

    public void CrearCliente(CrearClienteDTO clienteDto){
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(clienteDto.getNombre());
        nuevoCliente.setApellidos(clienteDto.getApellidos());
        clienteRepository.save(nuevoCliente);
    }

    public void EliminarClientePorId(Integer id){ clienteRepository.deleteById(id);}

    public void EditarClientePorId(Integer id, ClienteDTO clienteDto){

        Cliente cliente = clienteRepository.findById(id).orElse(null);
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setFoto(clienteDto.getFoto());
        clienteRepository.save(cliente);
    }


}
