package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.RegistroDTO;
import com.safa.cabezon_backend.Modelos.Rol;
import com.safa.cabezon_backend.Modelos.Usuario;
import com.safa.cabezon_backend.Repositorios.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

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
}
