package com.example.apireactcrud.config;

import com.example.apireactcrud.model.Usuario;
import com.example.apireactcrud.repository.UsuarioRepository;
import com.example.apireactcrud.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository repository;


    public AutenticacaoTokenFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);
        boolean valido = tokenService.isTokenValido(token);
        if(valido)
            autenticarCliente(token);

        filterChain.doFilter(request,response);
    }

    private void autenticarCliente(String token){
        Long idUsuario = tokenService.getIdUsuario(token);
        Usuario usuario = repository.findById(idUsuario).orElseThrow();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String recuperarToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token == null || token.isBlank() || !token.startsWith("Bearer ")){
            return null;
        }

        return token.substring(7, token.length());
    }
}
