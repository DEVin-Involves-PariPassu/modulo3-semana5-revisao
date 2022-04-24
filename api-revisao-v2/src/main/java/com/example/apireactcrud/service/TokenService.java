package com.example.apireactcrud.service;

import com.example.apireactcrud.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;

@Service
public class TokenService {
    @Value("${security.jwt.expiration}")
    private Long expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date agora = new Date();
        Date expiracao = new Date();
        expiracao.setTime(agora.getTime()+expiration);

        return Jwts.builder()
                .setIssuer("API CRUD")
                .setSubject(String.valueOf(usuario.getId()))
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS256,secret) //encripta todo o jwt com o HS256 e adiciona o secret
                .compact();
    }

    public boolean isTokenValido(String token){
        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Long getIdUsuario(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
