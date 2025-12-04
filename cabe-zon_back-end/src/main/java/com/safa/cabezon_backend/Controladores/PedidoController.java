package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.BuscarPedidoDTO;
import com.safa.cabezon_backend.Dto.PedidoDTO;
import com.safa.cabezon_backend.Modelos.Pedido;
import com.safa.cabezon_backend.Servicios.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@AllArgsConstructor
//Controlador de Pedido
public class PedidoController {
    
    private PedidoService pedidoService;

    //Solicitar todos los pedidos(BuscarPedidoDTO)
    @GetMapping("/all")
    public List<BuscarPedidoDTO> getPedidos(){return pedidoService.BuscarPedidos();}

    //Solicitar pedido segun id que se pase por url(BuscarPedidoDTO)
    @GetMapping("/{id}")
    public BuscarPedidoDTO getPedido(@PathVariable Integer id){return pedidoService.BuscarPedidoPorId(id);}

    //Crear pedido(PedidoDTO)
    @PostMapping("/post")
    public void postPedido(@RequestBody PedidoDTO dto) {
        pedidoService.CrearPedido(dto);
    }

    //Editar pedido segun id que se pase por url(PedidoDTO)
    @PutMapping("/put/{id}")
    public void putPedido(@PathVariable Integer id, @RequestBody PedidoDTO dto) {
        pedidoService.EditarPedido(id, dto);
    }

    //Borrar pedido segun id que se pase por url
    @DeleteMapping("/delete/{id}")
    public void deletePedido(@PathVariable Integer id){
        pedidoService.EliminarPedido(id);
    }

    @GetMapping("/cliente/{idCliente}/count")
    public int contarPedidosCliente(@PathVariable Integer idCliente) {
        return pedidoService.ContarPedidosPorCliente(idCliente);
    }

}
