package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findAllByInteresesSet_Id(Integer coleccionId);
}
