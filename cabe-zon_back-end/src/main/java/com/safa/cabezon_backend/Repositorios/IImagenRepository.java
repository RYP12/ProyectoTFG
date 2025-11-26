package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImagenRepository extends JpaRepository<Imagen, Integer> {

    @Query(value = """
        SELECT i.url 
        FROM cabezon.imagen i 
        JOIN (
            SELECT * FROM cabezon.producto ORDER BY RANDOM() LIMIT 10
        ) p ON p.id = i.id_producto 
        ORDER BY RANDOM()
        """, nativeQuery = true)
    List<String> findUrlsDeProductosAleatorios();
}
