package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImagenRepository extends JpaRepository<Imagen, Integer> {
}
