package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.ProductoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoPedidorepository extends JpaRepository<ProductoPedido,Integer> {
}
