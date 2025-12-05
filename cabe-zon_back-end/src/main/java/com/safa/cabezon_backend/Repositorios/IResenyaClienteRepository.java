package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.ResenyaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IResenyaClienteRepository extends JpaRepository<ResenyaCliente, Integer> {
    List<ResenyaCliente> findByProductoId(Integer idProducto);
}
