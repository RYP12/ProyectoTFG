package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.*;
import com.safa.cabezon_backend.Mapper.ClienteMapper;
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

    @Autowired
    private ClienteMapper clienteMapper;


    public void CrearCliente(CrearClienteDTO clienteDto){
        clienteRepository.save(clienteMapper.toEntity(clienteDto));
    }

    public void EliminarClientePorId(Integer id){ clienteRepository.deleteById(id);}

    public void EditarClientePorId(Integer id, ClienteDTO clienteDto){
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        clienteMapper.actualizar(clienteDto, cliente);
        clienteRepository.save(cliente);
    }


}
