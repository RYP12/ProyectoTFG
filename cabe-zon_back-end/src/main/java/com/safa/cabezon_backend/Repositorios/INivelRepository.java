package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INivelRepository extends JpaRepository<Nivel, Integer> {
}
