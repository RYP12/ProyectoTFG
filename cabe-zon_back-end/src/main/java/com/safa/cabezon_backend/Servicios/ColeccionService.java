package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.ColeccionDTO;
import com.safa.cabezon_backend.Modelos.Coleccion;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Repositorios.IColeccionRepository;
import com.safa.cabezon_backend.Repositorios.IProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ColeccionService {

    @Autowired
    private IColeccionRepository coleccionRepository;

    @Autowired
    private IProductoRepository productoRepository;

    public List<Coleccion> BuscarColecciones() {return coleccionRepository.findAll();}

    public Coleccion BuscarColeccionPorId(Integer id) {return coleccionRepository.findById(id).orElse(null);}

    public void BorrarColeccionPorId(Integer id) {coleccionRepository.deleteById(id);}

    public void CrearColeccion(ColeccionDTO coleccion) {
        Coleccion coleccionNuevo = new Coleccion();
        coleccionNuevo.setNombre(coleccion.getNombre());
        coleccionNuevo.setNumeroDeProductos(coleccion.getNumeroDeProductos());
        Set<Producto> productosNuevo = new HashSet<>();
        for(Integer id:coleccion.getProductosSet()){
            productosNuevo.add(productoRepository.findById(id).orElse(null));
        }
        coleccionNuevo.setProductosColeccionSet(productosNuevo);

        coleccionRepository.save(coleccionNuevo);
    }

    public void EditarColeccionPorId(Integer id, ColeccionDTO coleccion) {
        Coleccion coleccionActual = coleccionRepository.findById(id).orElse(null);
        coleccionActual.setNombre(coleccion.getNombre());
        coleccionActual.setNumeroDeProductos(coleccion.getNumeroDeProductos());
        Set<Producto> productosNuevo = new HashSet<>();
        for(Integer idProducto:coleccion.getProductosSet()){
            productosNuevo.add(productoRepository.findById(idProducto).orElse(null));
        }
        coleccionActual.setProductosColeccionSet(productosNuevo);
        coleccionRepository.save(coleccionActual);
    }
}
