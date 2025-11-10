package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.ProductoPedidoDTO;
import com.safa.cabezon_backend.Modelos.ProductoPedido;
import com.safa.cabezon_backend.Servicios.ProductoPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto_pedido")
@AllArgsConstructor
public class ProductoPedidoController {

    private ProductoPedidoService productoPedidoService;

    @GetMapping("/all")
    public List<ProductoPedido> getProductoPedidos(){return productoPedidoService.BuscarProductoPedido();}

    @GetMapping("/{id}")
    public ProductoPedido getProductoPedidoById(@PathVariable Integer id){return productoPedidoService.BuscarProductoPedidoPorId(id);}

    @PostMapping("/post")
    public void postProductoPedido(@RequestBody ProductoPedidoDTO dto){
        productoPedidoService.CrearProductoPedido(dto);
    }

    @PutMapping("/put/{id}")
    public void putProductoPedido(@PathVariable Integer id, @RequestBody ProductoPedidoDTO dto){
        productoPedidoService.EditarProductoPedido(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductoPedido(@PathVariable Integer id){
        productoPedidoService.EliminarProductoPedido(id);
    }

}
