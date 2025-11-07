package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.DireccionDTO;
import com.safa.cabezon_backend.Modelos.Direccion;
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
    private ClienteServicie clienteServicie;

    public List<Direccion> BuscarDirecciones() { return direccionRepository.findAll();}

    public Direccion BuscarDireccionPorId(Integer id){ return direccionRepository.findById(id).orElse(null);}

    public void EliminarDireccionPorId(Integer id){ direccionRepository.deleteById(id);}

    public void CrearDireccion(DireccionDTO direccionDto){
        Direccion nuevaDireccion = new Direccion();
        nuevaDireccion.setCalle(direccionDto.getCalle());
        nuevaDireccion.setNumero(direccionDto.getNumero());
        nuevaDireccion.setPiso(direccionDto.getPiso());
        nuevaDireccion.setLetra(direccionDto.getLetra());
        nuevaDireccion.setCodigoPostal(direccionDto.getCodigoPostal());
        nuevaDireccion.setAdicional(direccionDto.getAdicional());
        nuevaDireccion.setPais(direccionDto.getPais());
        nuevaDireccion.setProvincia(direccionDto.getProvincia());
        nuevaDireccion.setMunicipio(direccionDto.getMunicipio());
        nuevaDireccion.setCliente(clienteServicie.BuscarClientePorId(direccionDto.getIdCliente()));
        direccionRepository.save(nuevaDireccion);

    }

    public void EditarDireccionPorId(Integer id, DireccionDTO direccionDto){
        Direccion nuevaDireccion = direccionRepository.findById(id).orElse(null);
        nuevaDireccion.setCalle(direccionDto.getCalle());
        nuevaDireccion.setNumero(direccionDto.getNumero());
        nuevaDireccion.setPiso(direccionDto.getPiso());
        nuevaDireccion.setLetra(direccionDto.getLetra());
        nuevaDireccion.setCodigoPostal(direccionDto.getCodigoPostal());
        nuevaDireccion.setAdicional(direccionDto.getAdicional());
        nuevaDireccion.setPais(direccionDto.getPais());
        nuevaDireccion.setProvincia(direccionDto.getProvincia());
        nuevaDireccion.setMunicipio(direccionDto.getMunicipio());
        nuevaDireccion.setCliente(clienteServicie.BuscarClientePorId(direccionDto.getIdCliente()));
        direccionRepository.save(nuevaDireccion);

    }
}
