package com.safa.cabezon_backend.Security;

import com.safa.cabezon_backend.Modelos.Usuario;
import com.safa.cabezon_backend.Servicios.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class FiltroJWT extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (request.getServletPath().contains("/auth")){
            filterChain.doFilter(request,response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        TokenDTO tokenDTO = jwtService.extraerTokenDTO(token);

        if (tokenDTO != null && SecurityContextHolder.getContext().getAuthentication() == null && !jwtService.isExpired(token)) {

            Usuario usuario = (Usuario) usuarioService.loadUserByUsername(tokenDTO.getUsername());

            if(usuario!=null && !jwtService.validateToken(token)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        usuario.getUsername(),
                        usuario.getPassword(),
                        usuario.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
