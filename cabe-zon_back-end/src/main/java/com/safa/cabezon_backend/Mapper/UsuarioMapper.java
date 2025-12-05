package com.safa.cabezon_backend.Mapper;

import com.safa.cabezon_backend.Dto.RegistroDTO;
import com.safa.cabezon_backend.Modelos.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {

    @Mapping(source = "cliente.nombre", target = "nombre")
    @Mapping(source = "cliente.apellidos", target = "apellidos")
    public abstract RegistroDTO toDTO(Usuario usuario);

    public abstract List<RegistroDTO> listToDTO(List<Usuario> usuarios);
}