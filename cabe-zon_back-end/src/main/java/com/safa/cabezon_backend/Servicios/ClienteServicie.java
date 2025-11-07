package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.ClienteDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServicie {
    @Autowired
    private IClienteRepository clienteRepository;

    public List<Cliente> BuscarClientes() {return clienteRepository.findAll();}

    public Cliente BuscarClientePorId(Integer id){return clienteRepository.findById(id).orElse(null);}

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void CrearCliente(ClienteDTO clienteDto){
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(clienteDto.getNombre());
        nuevoCliente.setApellidos(clienteDto.getApellidos());
        nuevoCliente.setCorreo(clienteDto.getCorreo());
        nuevoCliente.setPasswordHash(passwordEncoder.encode(clienteDto.getPasswordHash()));
        clienteRepository.save(nuevoCliente);
    }

    public void EliminarClientePorId(Integer id){ clienteRepository.deleteById(id);}

    public void EditarClientePorId(Integer id, ClienteDTO clienteDto){

        Cliente cliente = clienteRepository.findById(id).orElse(null);
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setCorreo(clienteDto.getCorreo());
        cliente.setPasswordHash(passwordEncoder.encode(clienteDto.getPasswordHash()));
        cliente.setFoto(clienteDto.getFoto());
        cliente.setRol(clienteDto.getRol());
        clienteRepository.save(cliente);
    }
}
