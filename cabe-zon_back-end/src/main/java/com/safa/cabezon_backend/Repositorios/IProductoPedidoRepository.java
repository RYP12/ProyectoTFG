package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Dto.BuscarProductoDTO;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Modelos.ProductoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;

@Repository
public interface IProductoPedidoRepository extends JpaRepository<ProductoPedido, Integer> {

    @Query(value = "SELECT id_producto " +
            "FROM cabezon.productos_pedido " +
            "GROUP BY id_producto " +
            "ORDER BY SUM(cantidad) DESC",
            nativeQuery = true)
    List<Integer> BuscarTopVentas();
}
