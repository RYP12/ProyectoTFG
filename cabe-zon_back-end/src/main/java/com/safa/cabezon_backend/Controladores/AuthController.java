package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.LoginDTO;
import com.safa.cabezon_backend.Dto.RegistroDTO;
import com.safa.cabezon_backend.Modelos.Usuario;
import com.safa.cabezon_backend.Security.LoginService;
import com.safa.cabezon_backend.Servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

}
