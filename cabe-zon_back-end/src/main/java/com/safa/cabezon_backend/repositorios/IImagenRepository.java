package com.safa.cabezon_backend.repositorios;

import com.safa.cabezon_backend.modelos.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImagenRepository extends JpaRepository<Imagen, Integer> {
}
