package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDireccionesRepository extends JpaRepository<Direccion, Integer> {
}
