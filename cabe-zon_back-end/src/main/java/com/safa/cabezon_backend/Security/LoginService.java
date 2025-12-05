package com.safa.cabezon_backend.Security;

import com.safa.cabezon_backend.Dto.LoginDTO;
import com.safa.cabezon_backend.Dto.RespuestaDTO;
import com.safa.cabezon_backend.Modelos.Usuario;
import com.safa.cabezon_backend.Repositorios.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private IUsuarioRepository usuarioRepository;
    private JwtService jwtService;
    private ApplicationConfig seguridad;

    public String login(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findTopByUsername(loginDTO.getUsername());

        if(usuario != null && seguridad.passwordencoder().matches(loginDTO.getPassword(), usuario.getPassword())) {

            // Verificar si la cuenta ya esta verificada
            if (!usuario.isCuentaVerificada()) {
                throw new RuntimeException("Debes verificar tu cuenta antes de iniciar sesión.");
            }

            return jwtService.generateToken(usuario);
        }

        return "Fallo de autentificación";

    }
}
