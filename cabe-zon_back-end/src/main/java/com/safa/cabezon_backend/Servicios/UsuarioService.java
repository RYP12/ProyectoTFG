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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private IUsuarioRepository usuarioRepository;
    private ApplicationConfig seguridad;
    private final UsuarioMapper usuarioMapper;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findTopByUsername(username);
    }

    public void registrarUsuario(RegistroDTO registrodto){
        Usuario usuario = new Usuario();
        usuario.setUsername(registrodto.getUsername());
        usuario.setPassword(seguridad.passwordencoder().encode(registrodto.getPassword()));
        usuario.setRol(Rol.CLIENTE);

        // Generar token de verificación
        String token = UUID.randomUUID().toString();
        usuario.setTokenVerificacion(token);
        usuario.setCuentaVerificada(false);

        Cliente cliente = new Cliente();
        cliente.setNombre(registrodto.getNombre());
        cliente.setApellidos(registrodto.getApellidos());

        cliente.setUsuario(usuario);
        usuario.setCliente(cliente);

        usuarioRepository.save(usuario);

        // Enviar correo
        String urlVerificacion = "http://localhost:4200/verificar-cuenta?token=" + token; // URL del Front

        // Usamos HTML para el mensaje por correo. Los '%s' se sustituyen por la variable urlVerificacion
        String mensajeHtml = String.format(
                """
                <div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #e0e0e0; border-radius: 10px; max-width: 600px; margin: auto;'>
                    <h2 style='color: #333;'>Bienvenido a Cabe-zon, %s %s</h2>
                    <p style='color: #555;'>Gracias por registrarte. Para activar tu cuenta y empezar a coleccionar, haz clic en el siguiente botón:</p>
                    <div style='text-align: center; margin: 30px 0;'>
                        <a href='%s' style='background-color: #4CAF50; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; font-size: 16px;'>Activar Cuenta</a>
                    </div>
                    <p style='color: #777; font-size: 14px;'>Si el botón no funciona, copia y pega este enlace en tu navegador:</p>
                    <p style='word-break: break-all; color: #0066cc;'>%s</p>
                    <hr style='border: none; border-top: 1px solid #eee; margin: 20px 0;'>
                    <p style='text-align: center; color: #999; font-size: 12px;'>Este enlace es válido por tiempo limitado.</p>
                </div>
                """,
                registrodto.getNombre(),     // 1º %s: Nombre
                registrodto.getApellidos(),  // 2º %s: Apellidos
                urlVerificacion,             // 3º %s: URL para el botón
                urlVerificacion              // 4º %s: URL para el texto
        );

        emailService.enviarCorreo(usuario.getUsername(), "¡Bienvenido a Cabe-zon! Activa tu cuenta", mensajeHtml);
    }

    // MÉTODO PARA VERIFICAR CUENTA
    public boolean verificarUsuario(String token) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByTokenVerificacion(token);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setCuentaVerificada(true);
            usuario.setTokenVerificacion(null); // Borramos el token usado
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    // RECUPERACIÓN DE CONTRASEÑA - SOLICITUD
    public void solicitarRecuperacionPassword(String email) {
        Usuario usuario = usuarioRepository.findTopByUsername(email);
        if (usuario != null) {
            String token = UUID.randomUUID().toString();
            usuario.setTokenRecuperacion(token);
            usuario.setTokenRecuperacionExpiracion(LocalDateTime.now().plusHours(1)); // Expira en 1 hora
            usuarioRepository.save(usuario);

            String urlRecuperacion = "http://localhost:4200/reset-password?token=" + token; // URL del Front

            // Usamos HTML para el mensaje por correo. Los '%s' se sustituyen por la variable urlVerificacion
            String mensajeHtml = String.format(
                    """
                    <div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #e0e0e0; border-radius: 10px; max-width: 600px; margin: auto; background-color: #fafafa;'>
                        <h2 style='color: #333;'>Recupera tu contraseña</h2>
                        <p style='color: #555;'>Hemos recibido una solicitud para restablecer la contraseña de tu cuenta en <strong>Cabe-zon</strong>.</p>
                        <p style='color: #555;'>Si realizaste esta solicitud, haz clic en el siguiente botón para crear una nueva contraseña:</p>
                        <div style='text-align: center; margin: 30px 0;'>
                            <a href='%s' style='background-color: #007bff; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; font-size: 16px;'>Restablecer Contraseña</a>
                        </div>
                        <p style='color: #777; font-size: 14px;'>Si el botón no funciona, copia y pega este enlace en tu navegador:</p>
                        <p style='word-break: break-all; color: #0066cc;'>%s</p>
                        <hr style='border: none; border-top: 1px solid #eee; margin: 20px 0;'>
                        <p style='color: #999; font-size: 12px; text-align: center;'>Si no solicitaste restablecer tu contraseña, ignora este mensaje.<br>Por motivos de seguridad, este enlace expirará en poco tiempo.</p>
                    </div>
                    """,
                    urlRecuperacion,             // 1º %s: URL para el botón
                    urlRecuperacion              // 2º %s: URL para el texto
            );

            emailService.enviarCorreo(usuario.getUsername(), "Recuperar Contraseña - CabeZon", mensajeHtml);
        }
    }

    // RECUPERACIÓN DE CONTRASEÑA - CAMBIO REAL
    public boolean cambiarPassword(String token, String nuevaPassword) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByTokenRecuperacion(token);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Verificar si el token ha expirado
            if (usuario.getTokenRecuperacionExpiracion().isBefore(LocalDateTime.now())) {
                return false; // Token expirado
            }

            usuario.setPassword(seguridad.passwordencoder().encode(nuevaPassword));
            usuario.setTokenRecuperacion(null);
            usuario.setTokenRecuperacionExpiracion(null);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public List<RegistroDTO> buscarUsuarios(){
        return usuarioMapper.listToDTO(usuarioRepository.findAll());
    }
}
