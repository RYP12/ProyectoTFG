package com.safa.cabezon_backend.repositorios;

import com.safa.cabezon_backend.modelos.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IColeccionRepository extends JpaRepository<Coleccion, Integer> {
}
