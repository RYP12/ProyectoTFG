package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.ResenyaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResenyaClienteRepository extends JpaRepository<ResenyaCliente, Integer> {
}
