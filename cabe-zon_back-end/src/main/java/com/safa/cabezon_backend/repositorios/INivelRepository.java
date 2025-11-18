package com.safa.cabezon_backend.repositorios;

import com.safa.cabezon_backend.modelos.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INivelRepository extends JpaRepository<Nivel, Integer> {
}
