package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.RegistroDTO;
import com.safa.cabezon_backend.Modelos.Usuario;
import com.safa.cabezon_backend.Servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//Controlador de Usuarios
public class AuthController {


    @Autowired
    private UsuarioService usuarioService;

    //Registro de usuarios
    @PostMapping("/registro")
    public Usuario registrarUsuario(@RequestBody RegistroDTO registrodto){
        return usuarioService.registrarUsuario(registrodto);
    }


}
