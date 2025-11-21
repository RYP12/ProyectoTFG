package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.LoginDTO;
import com.safa.cabezon_backend.Dto.RegistroDTO;
import com.safa.cabezon_backend.Dto.RespuestaDTO;
import com.safa.cabezon_backend.Modelos.Rol;
import com.safa.cabezon_backend.Modelos.Usuario;
import com.safa.cabezon_backend.Repositorios.IUsuarioRepository;
import com.safa.cabezon_backend.Security.JwtService;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final JwtService jwtService;
    private IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordencoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findTopByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public Usuario registrarUsuario(RegistroDTO registrodto){
        Usuario usuario = new Usuario();
        usuario.setUsername(registrodto.getUsername());
        usuario.setPassword(passwordencoder.encode(registrodto.getPassword()));
        usuario.setRol(Rol.CLIENTE);

        return usuarioRepository.save(usuario);
    }

    public ResponseEntity<RespuestaDTO> login(LoginDTO dto) throws BadRequestException {
        Optional<Usuario> usuarioOptional= usuarioRepository.findTopByUsername(dto.getUsername());
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            if(passwordencoder.matches(dto.getPassword(),usuario.getPassword())){
                String token = jwtService.generateToken(usuario);
                return ResponseEntity.ok(RespuestaDTO.builder().estado(HttpStatus.OK.value()).token(token).build());
            }else {
                throw new BadRequestException("Contraseña Incorrecta");
            }

        }else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
