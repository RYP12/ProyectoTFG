package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.ResenyaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IResenyaClienteRepository extends JpaRepository<ResenyaCliente, Integer> {
    List<ResenyaCliente> findByProductoId(Integer idProducto);

    // JPQL: Seleccionamos el PROMEDIO (AVG) de las valoraciones filtrando por el ID del producto.
    // Usamos 'coalesce(AVG(r.valoracion), 0.0)' para que si no hay reseñas, devuelva 0.0 en lugar de NULL.
    @Query("SELECT COALESCE(AVG(r.valoracion), 0.0) FROM ResenyaCliente r WHERE r.producto.id = :idProducto")
    Double obtenerPromedioValoracionPorProducto(@Param("idProducto") Integer idProducto);
}
