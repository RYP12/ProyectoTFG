package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IColeccionRepository extends JpaRepository<Coleccion, Integer> {

    @Query(value = "SELECT c.* FROM cabezon.producto p " +
            "JOIN cabezon.coleccion_producto cp ON p.id = cp.id_producto " +
            "JOIN cabezon.coleccion c ON c.id = cp.id_coleccion " +
            "WHERE p.exclusivo = true " +
            "GROUP BY c.id",
            nativeQuery = true)
    List<Coleccion> findColeccionesConProductosExclusivos();
}
