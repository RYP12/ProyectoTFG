package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.BuscarClienteDTO;
import com.safa.cabezon_backend.Dto.BuscarDireccionDTO;
import com.safa.cabezon_backend.Dto.DireccionDTO;
import com.safa.cabezon_backend.Mapper.DireccionMapper;
import com.safa.cabezon_backend.Mapper.ImagenMapper;
import com.safa.cabezon_backend.Modelos.Direccion;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import com.safa.cabezon_backend.Repositorios.IDireccionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DireccionService {

    @Autowired
    private IDireccionRepository direccionRepository;

    @Autowired
    private IClienteRepository clienteRepository;


    private DireccionMapper direccionMapper;

    public List<BuscarDireccionDTO> BuscarDirecciones() {
        return direccionMapper.listToDTO(direccionRepository.findAll());
    }

    public BuscarDireccionDTO BuscarDireccionPorId(Integer id){
        return direccionMapper.toDTO(direccionRepository.findById(id).orElse(null));
    }

    public void EliminarDireccionPorId(Integer id){ direccionRepository.deleteById(id);}

    public void CrearDireccion(DireccionDTO direccionDto){
        direccionRepository.save(direccionMapper.toEntity(direccionDto));

    }

    public void EditarDireccionPorId(Integer id, DireccionDTO direccionDto){
        Direccion nuevaDireccion = direccionRepository.findById(id).orElse(null);
        direccionMapper.actualizarDireccion(direccionDto, nuevaDireccion);
        direccionRepository.save(nuevaDireccion);

    }
}
