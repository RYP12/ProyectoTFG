package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.BuscarClienteDTO;
import com.safa.cabezon_backend.Dto.BuscarPedidoDTO;
import com.safa.cabezon_backend.Dto.PedidoDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Pedido;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import com.safa.cabezon_backend.Repositorios.IPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PedidoService {

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    public ResponseEntity<List<BuscarPedidoDTO>> BuscarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<BuscarPedidoDTO> pedidosDTO = pedidos.stream().map(pedido -> {
            BuscarPedidoDTO dto = new BuscarPedidoDTO();
            dto.setFechaEstimada(pedido.getFechaEstimada());
            dto.setFechaEntrega(pedido.getFechaEntrega());
            dto.setEstado(pedido.getEstado());
            dto.setFecha(pedido.getFecha());
            dto.setPrecio_total(pedido.getPrecioTotal());

            if (pedido.getCliente() != null) {
                BuscarClienteDTO clienteDTO = new BuscarClienteDTO();
                clienteDTO.setNombre(pedido.getCliente().getNombre());
                clienteDTO.setApellidos(pedido.getCliente().getApellidos());
                clienteDTO.setFoto(pedido.getCliente().getFoto());
                clienteDTO.setCabecoins(pedido.getCliente().getCabecoins());
                clienteDTO.setNivel(pedido.getCliente().getNivel());
                dto.setCliente(clienteDTO);
            }

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(pedidosDTO);}

    public BuscarPedidoDTO BuscarPedidoPorId(Integer id) {
            Pedido pedido =pedidoRepository.findById(id).orElse(null);
            if (pedido != null) {
                BuscarPedidoDTO dto = new BuscarPedidoDTO();
                dto.setFechaEstimada(pedido.getFechaEstimada());
                dto.setFechaEntrega(pedido.getFechaEntrega());
                dto.setEstado(pedido.getEstado());
                dto.setFecha(pedido.getFecha());
                dto.setPrecio_total(pedido.getPrecioTotal());
                if (pedido.getCliente() != null) {
                    BuscarClienteDTO clienteDTO = new BuscarClienteDTO();
                    clienteDTO.setNombre(pedido.getCliente().getNombre());
                    clienteDTO.setApellidos(pedido.getCliente().getApellidos());
                    clienteDTO.setFoto(pedido.getCliente().getFoto());
                    clienteDTO.setCabecoins(pedido.getCliente().getCabecoins());
                    clienteDTO.setNivel(pedido.getCliente().getNivel());
                    dto.setCliente(clienteDTO);
                }
                return dto;
            }else  {
                return null;
            }

    }

    public void CrearPedido(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setFechaEstimada(dto.getFechaEstimada());
        pedido.setFechaEntrega(dto.getFechaEntrega());
        pedido.setEstado(dto.getEstado());
        pedido.setFecha(dto.getFecha());
        pedido.setPrecioTotal(dto.getPrecio_total());

        Cliente cliente = clienteRepository.findById(dto.getIdCliente()).orElse(null);
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
            pedido.setPrecioTotal(dto.getPrecio_total());

            Cliente cliente = clienteRepository.findById(dto.getIdCliente()).orElse(null);
            pedido.setCliente(cliente);

            pedidoRepository.save(pedido);
        }
    }

    public void EliminarPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
