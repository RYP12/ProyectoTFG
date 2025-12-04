package com.safa.cabezon_backend.Repositorios;

import com.safa.cabezon_backend.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findTopByUsername(String username);
}
