package com.safa.cabezon_backend.repositorios;

import com.safa.cabezon_backend.modelos.Direcciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDireccionesRepository extends JpaRepository<Direcciones, Integer> {
}
