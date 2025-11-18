package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.BuscarClienteDTO;
import com.safa.cabezon_backend.Dto.BuscarDireccionDTO;
import com.safa.cabezon_backend.Dto.DireccionDTO;
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

    public List<BuscarDireccionDTO> BuscarDirecciones() {
        List<Direccion> direcciones= direccionRepository.findAll();
        return direcciones.stream().map( direccion -> {
            BuscarDireccionDTO direccionDTO = new BuscarDireccionDTO();
            direccionDTO.setAdicional(direccion.getAdicional());
            direccionDTO.setPais(direccion.getPais());
            direccionDTO.setProvincia(direccion.getProvincia());
            direccionDTO.setMunicipio(direccion.getMunicipio());
            direccionDTO.setCodigoPostal(direccion.getCodigoPostal());
            direccionDTO.setLetra(direccion.getLetra());
            direccionDTO.setNumero(direccion.getNumero());
            direccionDTO.setPiso(direccion.getPiso());
            direccionDTO.setCalle(direccion.getCalle());

            if (direccion.getCliente() != null) {
                BuscarClienteDTO clienteDTO = new BuscarClienteDTO();
                clienteDTO.setNombre(direccion.getCliente().getNombre());
                clienteDTO.setApellidos(direccion.getCliente().getApellidos());
                clienteDTO.setFoto(direccion.getCliente().getFoto());
                clienteDTO.setCabecoins(direccion.getCliente().getCabecoins());
                clienteDTO.setNivel(direccion.getCliente().getNivel());
                direccionDTO.setCliente(clienteDTO);
            }
            return direccionDTO;
        }).toList();

    }

    public BuscarDireccionDTO BuscarDireccionPorId(Integer id){
        Direccion direccion = direccionRepository.findById(id).orElse(null);
        BuscarDireccionDTO direccionDTO = new BuscarDireccionDTO();
        direccionDTO.setAdicional(direccion.getAdicional());
        direccionDTO.setPais(direccion.getPais());
        direccionDTO.setProvincia(direccion.getProvincia());
        direccionDTO.setMunicipio(direccion.getMunicipio());
        direccionDTO.setCodigoPostal(direccion.getCodigoPostal());
        direccionDTO.setLetra(direccion.getLetra());
        direccionDTO.setNumero(direccion.getNumero());
        direccionDTO.setPiso(direccion.getPiso());
        direccionDTO.setCalle(direccion.getCalle());

        if (direccion.getCliente() != null) {
            BuscarClienteDTO clienteDTO = new BuscarClienteDTO();
            clienteDTO.setNombre(direccion.getCliente().getNombre());
            clienteDTO.setApellidos(direccion.getCliente().getApellidos());
            clienteDTO.setFoto(direccion.getCliente().getFoto());
            clienteDTO.setCabecoins(direccion.getCliente().getCabecoins());
            clienteDTO.setNivel(direccion.getCliente().getNivel());
            direccionDTO.setCliente(clienteDTO);
        }
        return direccionDTO;
    }

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
        nuevaDireccion.setCliente(clienteRepository.findById(direccionDto.getIdCliente()).orElse(null));
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
        nuevaDireccion.setCliente(clienteRepository.findById(direccionDto.getIdCliente()).orElse(null));
        direccionRepository.save(nuevaDireccion);

    }
}
