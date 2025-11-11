package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.PedidoDTO;
import com.safa.cabezon_backend.Modelos.Pedido;
import com.safa.cabezon_backend.Servicios.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@AllArgsConstructor
public class PedidoController {
    
    private PedidoService pedidoService;

    @GetMapping("/all")
    public List<Pedido> getPedidos(){return pedidoService.BuscarPedidos();}

    @GetMapping("/{id}")
    public Pedido getPedido(@PathVariable Integer id){return pedidoService.BuscarPedidoPorId(id);}

    @PostMapping("/post")
    public void postPedido(@RequestBody PedidoDTO dto) {
        pedidoService.CrearPedido(dto);
    }

    @PutMapping("/put/{id}")
    public void putPedido(@PathVariable Integer id, @RequestBody PedidoDTO dto) {
        pedidoService.EditarPedido(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePedido(@PathVariable Integer id){
        pedidoService.EliminarPedido(id);
    }

}
