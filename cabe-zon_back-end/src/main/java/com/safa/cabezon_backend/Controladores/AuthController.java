package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.CambiarPasswordDTO;
import com.safa.cabezon_backend.Dto.LoginDTO;
import com.safa.cabezon_backend.Dto.RecuperarPasswordDTO;
import com.safa.cabezon_backend.Dto.RegistroDTO;
import com.safa.cabezon_backend.Modelos.Usuario;
import com.safa.cabezon_backend.Security.LoginService;
import com.safa.cabezon_backend.Servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//Controlador de Usuarios
public class AuthController {

    private final UsuarioService usuarioService;
    private final LoginService loginService;

    //Obtener usuarios
    @GetMapping("/usuarios")
    public List<RegistroDTO> getUsuarios() {
        return usuarioService.buscarUsuarios();
    }

    //Login
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return loginService.login(loginDTO);
    }

    //Registro de usuarios
    @PostMapping("/registro")
    public void registrarUsuario(@RequestBody RegistroDTO registrodto){
        usuarioService.registrarUsuario(registrodto);
    }

    //Confirmar cuenta
    @GetMapping("/confirmar")
    public ResponseEntity<String> confirmarCuenta(@RequestParam("token") String token){
        boolean verificado = usuarioService.verificarUsuario(token);
        if(verificado){
            return ResponseEntity.ok().body("Cuenta verificada con éxito");
        }
        else {
            return ResponseEntity.badRequest().body("Token inválido o expirado");
        }
    }

    //Recuperar contraseña
    @PostMapping("/solicitar-recuperacion")
    public ResponseEntity<String> solicitarRecuperacion(@RequestBody String email){
        usuarioService.solicitarRecuperacionPassword(email);
        return ResponseEntity.ok("Si el correo es correcto, se ha enviado un enlace de recuperación.");
    }

    //Establecer nueva contraseña
    @PostMapping("/cambiar-password")
    public ResponseEntity<String> cambiarPassword(@RequestBody RecuperarPasswordDTO recuperarPasswordDTO){
        boolean exito = usuarioService.cambiarPassword(recuperarPasswordDTO.getToken(), recuperarPasswordDTO.getNuevaPassword());
        if(exito){
            return ResponseEntity.ok().body("Contraseña actualizada correctamente");
        } else {
            return ResponseEntity.badRequest().body("Token inválido o expirado");
        }
    }

}
