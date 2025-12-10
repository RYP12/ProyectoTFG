package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.NivelDTO;
import com.safa.cabezon_backend.Exception.RecursoNoEncontradoException;
import com.safa.cabezon_backend.Modelos.Nivel;
import com.safa.cabezon_backend.Repositorios.INivelRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NivelService {
    @Autowired
    private INivelRepository nivelRepository;

    public List<Nivel> BuscarNiveles(){return nivelRepository.findAll();}

    public Nivel BuscarNivelPorId(Integer id){return nivelRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el nivel con ID: " + id));}

    public void EditarDescuentoNivel(Integer id, NivelDTO nivelDTO){
        Nivel nivel = nivelRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el nivel con ID: " + id));
        nivel.setDescuento(nivelDTO.getDescuento());
        nivelRepository.save(nivel);
    }


}
