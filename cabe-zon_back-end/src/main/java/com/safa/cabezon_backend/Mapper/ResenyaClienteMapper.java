package com.safa.cabezon_backend.Mapper;


import com.safa.cabezon_backend.Dto.CrearResenyaClienteDTO;
import com.safa.cabezon_backend.Dto.ResenyaClienteDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Modelos.ResenyaCliente;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import com.safa.cabezon_backend.Repositorios.IProductoRepository;
import com.safa.cabezon_backend.Repositorios.IUsuarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ResenyaClienteMapper {

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IProductoRepository productoRepository;

    public abstract List<ResenyaClienteDTO> toListResenyaClienteDTO(List<ResenyaCliente> resenya);

    public abstract ResenyaClienteDTO toResenyaClienteDTO(ResenyaCliente resenya);

    @Mapping(source = "idProducto", target = "producto")
    @Mapping(source = "idCliente", target = "cliente")
    public abstract ResenyaCliente toEntity(CrearResenyaClienteDTO dto);

    Cliente transformarCliente(Integer idCliente) {
        return clienteRepository.findById(idCliente).orElse(null);
    }

    Producto transformarProducto(Integer idProducto) {
        return productoRepository.findById(idProducto).orElse(null);
    }
    @Mapping(source = "idProducto", target = "producto")
    @Mapping(source = "idCliente", target = "cliente")
    public abstract void actualizarEntityFromDto(CrearResenyaClienteDTO dto,@MappingTarget ResenyaCliente resenyaCliente);


}
