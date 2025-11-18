package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.RegistroDTO;
import com.safa.cabezon_backend.Modelos.Usuario;
import com.safa.cabezon_backend.Servicios.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private UsuarioService usuarioService;
    @PostMapping("/registro")
    public Usuario registrarUsuario(RegistroDTO registrodto){
        return usuarioService.registrarUsuario(registrodto);
    }


}
