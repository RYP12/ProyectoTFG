package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.ProductoDTO;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Servicios.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@AllArgsConstructor
public class ProductoController {

    private ProductoService productoService;

    @GetMapping("/all")
    public List<Producto> getProductos(){return productoService.BuscarProductos();}

    @GetMapping("/{id}")
    public Producto getProducto(@PathVariable Integer id){return productoService.BuscarProductoPorId(id);}

    @PostMapping("/post")
    public void postProducto(@RequestBody ProductoDTO dto) {
        productoService.CrearProducto(dto);
    }

    @PutMapping("/put/{id}")
    public void putProducto(@PathVariable Integer id, @RequestBody ProductoDTO dto) {
        productoService.EditarProducto(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProducto(@PathVariable Integer id){
        productoService.EliminarProducto(id);
    }
}
