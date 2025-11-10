package com.safa.cabezon_backend.Security;

import com.safa.cabezon_backend.Dto.CambiarPasswordDTO;
import com.safa.cabezon_backend.Dto.LoginDTO;
import com.safa.cabezon_backend.Dto.RegistroDTO;
import com.safa.cabezon_backend.Dto.RespuestaJWTDTO;
import com.safa.cabezon_backend.Servicios.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // Registro
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated RegistroDTO request) {
        authService.registrar(request);
        return new ResponseEntity<>("Usuario registrado correctamente. Revisa tu correo para activar la cuenta.", HttpStatus.CREATED);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<RespuestaJWTDTO> login(@RequestBody @Validated LoginDTO request) {
        RespuestaJWTDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // Cambiar contraseña
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody @Validated CambiarPasswordDTO request) {
        authService.cambiarPassword(request);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }
}
