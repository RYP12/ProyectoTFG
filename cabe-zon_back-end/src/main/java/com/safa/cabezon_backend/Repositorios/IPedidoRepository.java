package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {
}
