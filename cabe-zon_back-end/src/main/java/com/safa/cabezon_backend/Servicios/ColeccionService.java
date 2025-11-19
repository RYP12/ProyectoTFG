package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.BuscarColeccionDTO;
import com.safa.cabezon_backend.Dto.ColeccionDTO;
import com.safa.cabezon_backend.Dto.ProductoDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Coleccion;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import com.safa.cabezon_backend.Repositorios.IColeccionRepository;
import com.safa.cabezon_backend.Repositorios.IProductoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColeccionService {

    @Autowired
    private IColeccionRepository coleccionRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Transactional
    public List<BuscarColeccionDTO> BuscarColecciones() {
        return coleccionRepository.findAll().stream()
                .map(c -> new BuscarColeccionDTO(
                        c.getNombre(),
                        c.getNumeroDeProductos(),
                        c.getProductosColeccionSet().stream().map(
                                p -> new ProductoDTO(
                                        p.getNombre(),
                                        p.getDescripcion(),
                                        p.getPrecio(),
                                        p.getCodigoProducto(),
                                        p.getStock(),
                                        p.getExclusivo(),
                                        p.getValoracion()
                                )
                        ).collect(Collectors.toSet())
                ))
                .toList();
    }

    @Transactional
    public BuscarColeccionDTO BuscarColeccionPorId(Integer id) {
       Coleccion coleccion= coleccionRepository.findById(id).orElse(null);
       BuscarColeccionDTO dto = new BuscarColeccionDTO(
               coleccion.getNombre(),
               coleccion.getNumeroDeProductos(),
               coleccion.getProductosColeccionSet().stream().map(
                       p -> new ProductoDTO(
                               p.getNombre(),
                               p.getDescripcion(),
                               p.getPrecio(),
                               p.getCodigoProducto(),
                               p.getStock(),
                               p.getExclusivo(),
                               p.getValoracion()
                       )
               ).collect(Collectors.toSet())
       );
       return dto;
    }

    @Transactional
    public void borrarColeccionPorId(Integer id) {
        Coleccion coleccion = coleccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coleccion no encontrada"));

        // Limpiar relaciones ManyToMany en clientes
        clienteRepository.findAll().forEach(cliente -> {
            cliente.getInteresesSet().remove(coleccion);
        });

        // Borrar la colección
        coleccionRepository.delete(coleccion);
    }

    @Transactional
    public void CrearColeccion(ColeccionDTO coleccion) {
        Coleccion coleccionNuevo = new Coleccion();
        coleccionNuevo.setNombre(coleccion.getNombre());
        coleccionNuevo.setNumeroDeProductos(coleccion.getNumeroDeProductos());
        Set<Producto> productosNuevo = new HashSet<>();
        for(Integer id:coleccion.getProductosSet()){
            productoRepository.findById(id).ifPresent(productosNuevo::add);
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
