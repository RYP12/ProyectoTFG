package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {


    @Query(value = "SELECT * FROM cabezon.producto p WHERE p.exclusivo = false", nativeQuery = true)
    List<Producto> findProductosNoExclusivos();

    @Query("SELECT p FROM Producto p WHERE p.exclusivo = true")
    Page<Producto> findProductosExclusivos(Pageable pageable);

    @Query(value = """
        SELECT p.* FROM cabezon.coleccion c 
        JOIN cabezon.coleccion_producto cp ON c.id = cp.id_coleccion 
        JOIN cabezon.producto p ON p.id = cp.id_producto 
        WHERE c.id = :idColeccion
        """, nativeQuery = true)
    List<Producto> buscarProductosPorColeccionId(@Param("idColeccion") Integer id);

    // BUSCAR PRODUCTOS POR ID PAGINADOS
    @Query(value = """
        SELECT p.* FROM cabezon.coleccion c 
        JOIN cabezon.coleccion_producto cp ON c.id = cp.id_coleccion 
        JOIN cabezon.producto p ON p.id = cp.id_producto 
        WHERE c.id = :idColeccion
        """, nativeQuery = true)
    Page<Producto> findByColecciones_Id(Integer idColeccion, Pageable pageable);
    //------------

    @Query(value = "SELECT * FROM cabezon.producto WHERE producto.precio BETWEEN :precioMin AND :precioMax", nativeQuery = true)
    List<Producto> findProductosByPrecio(@Param("precioMin") Double precioMin, @Param("precioMax") Double precioMax);

    @Query(value = "SELECT p.* FROM cabezon.cliente c " +
            "JOIN cabezon.gustos g ON c.id = g.id_cliente " +
            "JOIN cabezon.producto p ON p.id = g.id_producto " +
            "WHERE c.id = :clienteId", nativeQuery = true)
    List<Producto> findGustosCliente(@Param("clienteId") Integer clienteId);
}