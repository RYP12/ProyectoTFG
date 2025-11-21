package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.BuscarProductoDTO;
import com.safa.cabezon_backend.Dto.CrearProductoDTO;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Servicios.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public void postProducto(@RequestBody CrearProductoDTO dto) {
        productoService.CrearProducto(dto);
    }

    @PutMapping("/put/{id}")
    public void putProducto(@PathVariable Integer id, @RequestBody CrearProductoDTO dto) {
        productoService.EditarProducto(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProducto(@PathVariable Integer id){
        productoService.EliminarProducto(id);
    }

    @GetMapping("/top-ventas")
    public ResponseEntity<List<BuscarProductoDTO>> getTopVentas() {
        List<BuscarProductoDTO> topProductos = productoService.obtenerTop4MasVendidos();
        return ResponseEntity.ok(topProductos);
    }

    @GetMapping("/noexclusivo")
    public List<BuscarProductoDTO> getNoExclusivo() {
        return productoService.BuscarPorductosNormales();
    }

    @GetMapping("/exclusivo")
    public List<BuscarProductoDTO> getExclusivo() {
        return productoService.BuscarPorductosExclusivos();
    }

    @GetMapping("/coleccion/{id}")
    public List<BuscarProductoDTO> getProductoPorColeccion(@PathVariable Integer id) {
        return productoService.BuscarPorductosPorColeccion(id);
    }

    @GetMapping("/franjaPrecio")
    public List<BuscarProductoDTO> getFranjaPrecio(@RequestParam("min") Double precioMin, @RequestParam("max") Double precioMax) {
        return productoService.BuscarPorductosPorFranjaPrecio(precioMin,precioMax);
    }

    @GetMapping("/gustos/{id}")
    public List<BuscarProductoDTO> getGustos(@PathVariable Integer id) {
        return productoService.BuscarPorductosPorGustosCliente(id);
    }
}
