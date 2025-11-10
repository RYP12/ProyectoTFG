package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.PedidoDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Pedido;
import com.safa.cabezon_backend.Repositorios.IPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    public List<Pedido> BuscarPedidos() {return pedidoRepository.findAll();}

    public Pedido BuscarPedidoPorId(Integer id) {return pedidoRepository.findById(id).orElse(null);}

    public void CrearPedido(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setFechaEstimada(dto.getFechaEstimada());
        pedido.setFechaEntrega(dto.getFechaEntrega());
        pedido.setEstado(dto.getEstado());
        pedido.setFecha(dto.getFecha());

        Cliente cliente = clienteService.BuscarClientePorId(dto.getIdCliente());
        pedido.setCliente(cliente);

        pedidoRepository.save(pedido);
    }

    public void EditarPedido(Integer id, PedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null) {
            pedido.setFechaEstimada(dto.getFechaEstimada());
            pedido.setFechaEntrega(dto.getFechaEntrega());
            pedido.setEstado(dto.getEstado());
            pedido.setFecha(dto.getFecha());

            Cliente cliente = clienteService.BuscarClientePorId(dto.getIdCliente());
            pedido.setCliente(cliente);

            pedidoRepository.save(pedido);
        }
    }

    public void EliminarPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
