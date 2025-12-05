package com.safa.cabezon_backend.Security;

import com.safa.cabezon_backend.Modelos.Rol;
import com.safa.cabezon_backend.Modelos.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public String generateToken(Usuario usuario) {
        TokenDTO tokenDTO = TokenDTO
                .builder()
                .username(usuario.getUsername())
                .rol(usuario.getRol())
                .fecha_creacion(LocalDateTime.now().toString())
                .fecha_expiracion(LocalDateTime.now().plusHours(2).toString())
                .build();

        return Jwts
                .builder()
                .claim("tokenDTO", tokenDTO)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateVerificationToken(String username) {
        return Jwts
                .builder()
                .setSubject(username) // Guardamos solo el email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 15))) // 15 min
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extraerToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public TokenDTO extraerTokenDTO(String token) {
        Claims claims = extraerToken(token);
        Map<String, Object> mapa = (Map<String, Object>) claims.get("tokenDTO");
        return  TokenDTO.builder()
                .username((String) mapa.get("username"))
                .fecha_creacion((String) mapa.get("fecha_creacion"))
                .fecha_expiracion((String) mapa.get("fecha_expiracion"))
                .rol(Rol.valueOf((String)  mapa.get("rol")))
                .build();
    }

    public String getUsernameFromToken(String token) {
        return extraerToken(token).getSubject();
    }

    public boolean validateToken(String token) {
        return extraerToken(token).getExpiration().after(new Date());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Metodo que me dice si el token a expirado
    public boolean isExpired(String token) {
        return LocalDateTime.parse(extraerTokenDTO(token).getFecha_expiracion()).isBefore(LocalDateTime.now());
    }


}
