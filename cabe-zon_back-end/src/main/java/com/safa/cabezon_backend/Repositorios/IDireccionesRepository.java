package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.Direcciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDireccionesRepository extends JpaRepository<Direcciones, Integer> {
}
