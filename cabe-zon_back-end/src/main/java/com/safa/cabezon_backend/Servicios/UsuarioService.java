package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.LoginDTO;
import com.safa.cabezon_backend.Dto.RegistroDTO;
import com.safa.cabezon_backend.Dto.RespuestaDTO;
import com.safa.cabezon_backend.Mapper.UsuarioMapper;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Rol;
import com.safa.cabezon_backend.Modelos.Usuario;
import com.safa.cabezon_backend.Repositorios.IUsuarioRepository;
import com.safa.cabezon_backend.Security.ApplicationConfig;
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

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private IUsuarioRepository usuarioRepository;
    private ApplicationConfig seguridad;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findTopByUsername(username);
    }

    public void registrarUsuario(RegistroDTO registrodto){
        Usuario usuario = new Usuario();
        usuario.setUsername(registrodto.getUsername());
        usuario.setPassword(seguridad.passwordencoder().encode(registrodto.getPassword()));
        usuario.setRol(Rol.CLIENTE);

        Cliente cliente = new Cliente();
        cliente.setNombre(registrodto.getNombre());
        cliente.setApellidos(registrodto.getApellidos());

        cliente.setUsuario(usuario);
        usuario.setCliente(cliente);

        usuarioRepository.save(usuario);
    }

    public List<RegistroDTO> buscarUsuarios(){
        return usuarioMapper.listToDTO(usuarioRepository.findAll());
    }
}
