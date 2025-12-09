package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDireccionRepository extends JpaRepository<Direccion, Integer> {
    List<Direccion> findByClienteId(Integer idCliente);
}
