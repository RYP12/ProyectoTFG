package com.safa.cabezon_backend.Mapper;

import com.safa.cabezon_backend.Dto.BuscarDireccionDTO;
import com.safa.cabezon_backend.Dto.DireccionDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Direccion;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DireccionMapper {

    @Autowired
    private IClienteRepository clienteRepository;


    public abstract List<BuscarDireccionDTO> listToDTO(List<Direccion> direccion);
    public abstract BuscarDireccionDTO toDTO(Direccion direccion);

    @Mapping(source = "idCliente", target = "cliente")
    public abstract Direccion toEntity(DireccionDTO direccionDTO);


    Cliente transformarCliente(Integer idCliente) {
        return clienteRepository.findById(idCliente).orElse(null);
    }

    @Mapping(source = "idCliente", target = "cliente")
    public abstract Direccion actualizarDireccion(DireccionDTO direccionDTO, @MappingTarget Direccion direccion);

}
