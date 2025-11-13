package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.ResenyaClienteDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Modelos.ResenyaCliente;
import com.safa.cabezon_backend.Repositorios.IResenyaClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ResenyaClienteService {

    @Autowired
    private IResenyaClienteRepository resenyaClienteRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ClienteService clienteService;

    public List<ResenyaCliente> BuscarResenyaCliente() {
        return resenyaClienteRepository.findAll();
    }

    public ResenyaCliente BuscarResenyaClientePorId(Integer id) {
        return resenyaClienteRepository.findById(id).orElse(null);
    }

    public void CrearResenyaCliente(ResenyaClienteDTO dto) {
        ResenyaCliente resenyaCliente = new ResenyaCliente();
        resenyaCliente.setTexto(dto.getTexto());
        resenyaCliente.setValoracion(dto.getValoracion());
        resenyaCliente.setFecha(Date.valueOf(LocalDate.now()));

        Producto producto = productoService.BuscarProductoPorId(dto.getIdProducto());
        resenyaCliente.setProducto(producto);

        Cliente cliente = clienteService.BuscarClientePorId(dto.getIdCliente());
        resenyaCliente.setCliente(cliente);

        resenyaClienteRepository.save(resenyaCliente);
    }

    public void EditarResenyaCliente(Integer id, ResenyaClienteDTO dto) {
        ResenyaCliente resenyaCliente = resenyaClienteRepository.findById(id).orElse(null);
        if (resenyaCliente != null) {
            resenyaCliente.setTexto(dto.getTexto());
            resenyaCliente.setValoracion(dto.getValoracion());

            Producto producto = productoService.BuscarProductoPorId(dto.getIdProducto());
            resenyaCliente.setProducto(producto);

            Cliente cliente = clienteService.BuscarClientePorId(dto.getIdCliente());
            resenyaCliente.setCliente(cliente);

            resenyaClienteRepository.save(resenyaCliente);
        }
    }

    public void EliminarResenyaCliente(Integer id) {
        resenyaClienteRepository.deleteById(id);
    }
}
