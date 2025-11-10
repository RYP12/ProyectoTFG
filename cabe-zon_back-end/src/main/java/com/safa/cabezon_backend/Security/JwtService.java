package com.safa.cabezon_backend.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "mi_clave_super_secreta"; // ⚠️ reemplaza por una segura

    // Generar token a partir de un correo
    public String generateToken(String correo) {
        return Jwts.builder()
                .setSubject(correo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validar token
    public boolean validateToken(String token, String correo) {
        String subject = extractCorreo(token);
        return (subject.equals(correo) && !isTokenExpired(token));
    }

    // Extraer correo del token
    public String extractCorreo(String token) {
        return getClaims(token).getSubject();
    }

    // Verificar si el token expiró
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
