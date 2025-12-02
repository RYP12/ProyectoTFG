package com.safa.cabezon_backend.Mapper;

import com.safa.cabezon_backend.Dto.BuscarColeccionDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Coleccion;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public abstract class ColeccionMapper {

    public abstract List<BuscarColeccionDTO> listTODTO(List<Coleccion> coleccions);
    public abstract BuscarColeccionDTO toDTO(Coleccion coleccion);
}
