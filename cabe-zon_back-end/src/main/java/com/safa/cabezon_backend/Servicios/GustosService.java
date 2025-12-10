package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Mapper.ProductoMapper;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import com.safa.cabezon_backend.Repositorios.IProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GustosService {

    private IClienteRepository clienteRepository;
    private IProductoRepository productoRepository;
    private ProductoMapper productoMapper;

    @Transactional
    public void agregarGusto(Integer idCliente, Integer idProducto) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        cliente.getListaDeseosSet().add(producto);
        clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminarGusto(Integer idCliente, Integer idProducto) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.getListaDeseosSet().removeIf(p -> p.getId().equals(idProducto));
        clienteRepository.save(cliente);
    }
}
