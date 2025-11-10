package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.CambiarPasswordDTO;
import com.safa.cabezon_backend.Dto.LoginDTO;
import com.safa.cabezon_backend.Dto.RegistroDTO;
import com.safa.cabezon_backend.Dto.RespuestaJWTDTO;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Rol;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import com.safa.cabezon_backend.Security.JwtService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void registrar(RegistroDTO req) {
        // 1️⃣ Verificar que el correo no exista
        if (clienteRepository.findByCorreo(req.getCorreo()).isPresent()) {
            throw new RuntimeException("Correo ya registrado");
        }

        // 2️⃣ Crear el cliente
        Cliente cliente = new Cliente();
        cliente.setNombre(req.getNombre());
        cliente.setApellidos(req.getApellidos());
        cliente.setCorreo(req.getCorreo());
        cliente.setPasswordHash(passwordEncoder.encode(req.getPassword())); // ✅ cifrar contraseña
        cliente.setRol(req.getRol() != null ? req.getRol() : Rol.CLIENTE);

        // 3️⃣ Guardar en la base de datos
        clienteRepository.save(cliente);

        // 4️⃣ (Opcional) Enviar correo de verificación
    }

    public RespuestaJWTDTO login(LoginDTO req) {
        // 1️⃣ Validar credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getCorreo(), req.getPassword())
        );

        // 2️⃣ Obtener cliente de la base de datos
        Cliente cliente = clienteRepository.findByCorreo(req.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3️⃣ Generar JWT
        String token = jwtService.generateToken(cliente.getCorreo());

        // 4️⃣ Devolver token al frontend
        return new RespuestaJWTDTO(token);
    }
    public void cambiarPassword(CambiarPasswordDTO req) {
        // 1️⃣ Buscar cliente por correo
        Cliente cliente = clienteRepository.findByCorreo(req.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2️⃣ Verificar contraseña actual
        if (!passwordEncoder.matches(req.getPasswordActual(), cliente.getPasswordHash())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        // 3️⃣ Cifrar y actualizar nueva contraseña
        cliente.setPasswordHash(passwordEncoder.encode(req.getPasswordNuevo()));
        clienteRepository.save(cliente);
    }
}
