package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.LoginDTO;
import com.safa.cabezon_backend.Dto.RegistroDTO;
import com.safa.cabezon_backend.Dto.RespuestaDTO;
import com.safa.cabezon_backend.Modelos.Usuario;
import com.safa.cabezon_backend.Servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // Permite que Angular se conecte
public class AuthController {

    private final UsuarioService usuarioService;

    // Registro de usuarios
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody RegistroDTO registrodto){
        return ResponseEntity.ok(usuarioService.registrarUsuario(registrodto));
    }

    // Login de usuarios
    @PostMapping("/login")
    public ResponseEntity<RespuestaDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            return usuarioService.login(loginDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build(); // O devuelve un mensaje de error
        }
    }

    // Confirmar cuenta
    @GetMapping("/confirmar")
    public ResponseEntity<String> confirmarCuenta(@RequestParam("token") String token) {
        try {
            usuarioService.confirmarCuenta(token);
            return ResponseEntity.ok("Cuenta verificada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al verificar: " + e.getMessage());
        }
    }
}