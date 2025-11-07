package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.ColeccionDTO;
import com.safa.cabezon_backend.Modelos.Coleccion;
import com.safa.cabezon_backend.Repositorios.IColeccionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ColeccionServie {

    @Autowired
    private IColeccionRepository coleccionRepository;

    public List<Coleccion> BuscarColecciones() {return coleccionRepository.findAll();}

    public Coleccion BuscarColeccionPorId(Integer id) {return coleccionRepository.findById(id).orElse(null);}

    public void BorrarColeccionPorId(Integer id) {coleccionRepository.deleteById(id);}

    public void CrearColeccion(ColeccionDTO coleccion) {
        Coleccion coleccionNuevo = new Coleccion();
        coleccionNuevo.setNombre(coleccion.getNombre());
        coleccionNuevo.setNumeroDeProductos(coleccion.getNumeroDeProductos());
        coleccionRepository.save(coleccionNuevo);
    }

    public void EditarColeccionPorId(Integer id, ColeccionDTO coleccion) {
        Coleccion coleccionActual = coleccionRepository.findById(id).orElse(null);
        coleccionActual.setNombre(coleccion.getNombre());
        coleccionActual.setNumeroDeProductos(coleccion.getNumeroDeProductos());
        coleccionRepository.save(coleccionActual);
    }
}
