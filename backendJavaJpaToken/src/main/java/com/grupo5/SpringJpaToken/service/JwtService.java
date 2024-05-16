package com.grupo5.SpringJpaToken.service;

import com.grupo5.SpringJpaToken.model.Usuario;
import com.grupo5.SpringJpaToken.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "2a08cea74fb63aecf99f43554cea573bf69fe0219a7c90212862ac34e7dcae98";
    private final TokenRepository tokenRepository;

    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }



    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        boolean validToken = tokenRepository
                .findByToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("role");
    }
    public Long extractId(String token) {
        Claims claims = extractAllClaims(token);
        Object idObject = claims.get("id");

        if (idObject instanceof Long) {
            return (Long) idObject;
        } else if (idObject instanceof Integer) {
            return ((Integer) idObject).longValue();
        } else if (idObject instanceof String) {
            return Long.parseLong((String) idObject);
        } else {
            throw new IllegalArgumentException("El ID no es un tipo válido para conversión a Long");
        }
    }



    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()//Crea un parser para el token JWT
                .verifyWith(getSigninKey())//Valida el token
                .build()
                .parseSignedClaims(token)//verifica que no alla sido modificado
                .getPayload();//Extra los datos de este
    }


    public String generateTokenTest(Usuario user) {
        String token = Jwts
                .builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId().toString())
                .claim("name", user.getNombre()+" "+user.getApellido())
                .claim("role", user.getRol().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
