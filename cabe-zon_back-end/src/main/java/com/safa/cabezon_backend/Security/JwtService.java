package com.safa.cabezon_backend.Security;

import com.safa.cabezon_backend.Modelos.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
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
                .rol(usuario.getRol().name())
                .fecha_creacion(System.currentTimeMillis())
                .fecha_expiracion(System.currentTimeMillis() * 1000 * 60 * 60)
                .build();

        return Jwts
                .builder()
                .claim("tokenDTO", tokenDTO)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extraerToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public TokenDTO extraerTokenDTO(String token) {
        Claims claims = extraerToken(token);
        Map<String, Object> mapa = (LinkedHashMap<String, Object>) claims.get("tokenDTO");
        return  TokenDTO.builder()
                .username((String) mapa.get("username"))
                .fecha_creacion((Long) mapa.get("fecha_creacion"))
                .fecha_expiracion((Long) mapa.get("fecha_expiracion"))
                .rol((String) mapa.get("rol"))
                .build();
    }

    public boolean validateToken(String token) {
        return new Date(extraerTokenDTO(token).getFecha_expiracion()).before(new Date());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
