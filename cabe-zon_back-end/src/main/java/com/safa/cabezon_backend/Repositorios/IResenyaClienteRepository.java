package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.ResenyaCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IResenyaClienteRepository extends JpaRepository<ResenyaCliente, Integer> {
}
