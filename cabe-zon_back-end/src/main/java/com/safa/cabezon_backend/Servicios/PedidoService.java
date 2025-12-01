package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.BuscarClienteDTO;
import com.safa.cabezon_backend.Dto.BuscarPedidoDTO;
import com.safa.cabezon_backend.Dto.PedidoDTO;
import com.safa.cabezon_backend.Mapper.PedidoMapper;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Pedido;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import com.safa.cabezon_backend.Repositorios.IPedidoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PedidoService {

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper mapper;


    @Transactional
    public List<BuscarPedidoDTO> BuscarPedidos() {
        List<BuscarPedidoDTO> list = mapper.listToPedidoDTO(pedidoRepository.findAll());
        return list;
    }

    @Transactional
    public BuscarPedidoDTO BuscarPedidoPorId(Integer id) {
        return mapper.toDTO(pedidoRepository.findById(id).orElse(null));
    }

    @Transactional
    public void CrearPedido(PedidoDTO dto) {
        pedidoRepository.save(mapper.toEntity(dto));
    }


    @Transactional
    public void EditarPedido(Integer id, PedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        mapper.actualizarPedido(dto, pedido);
        pedidoRepository.save(pedido);
    }

    public void EliminarPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
