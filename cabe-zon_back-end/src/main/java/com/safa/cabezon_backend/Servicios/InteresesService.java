package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.BuscarColeccionDTO;
import com.safa.cabezon_backend.Mapper.ColeccionMapper;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Coleccion;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import com.safa.cabezon_backend.Repositorios.IColeccionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InteresesService {

    private IClienteRepository clienteRepository;
    private IColeccionRepository coleccionRepository;
    private ColeccionMapper coleccionMapper;

    @Transactional
    public List<BuscarColeccionDTO> obtenerInteresesPorCliente(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Set<Coleccion> intereses = cliente.getInteresesSet();
        return coleccionMapper.listTODTO(intereses.stream().collect(Collectors.toList()));
    }

    @Transactional
    public void agregarInteres(Integer idCliente, Integer idColeccion) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Coleccion coleccion = coleccionRepository.findById(idColeccion)
                .orElseThrow(() -> new RuntimeException("Colección no encontrada"));

        cliente.getInteresesSet().add(coleccion);
        clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminarInteres(Integer idCliente, Integer idColeccion) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.getInteresesSet().removeIf(c -> c.getId().equals(idColeccion));
        clienteRepository.save(cliente);
    }

}
