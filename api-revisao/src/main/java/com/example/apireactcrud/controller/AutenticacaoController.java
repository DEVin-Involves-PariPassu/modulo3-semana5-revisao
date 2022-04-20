package com.example.apireactcrud.controller;

import com.example.apireactcrud.controller.dto.AdicionaUsuarioDto;
import com.example.apireactcrud.controller.dto.LoginDto;
import com.example.apireactcrud.controller.dto.TokenDto;
import com.example.apireactcrud.model.Perfil;
import com.example.apireactcrud.model.Usuario;
import com.example.apireactcrud.repository.PerfilRepository;
import com.example.apireactcrud.repository.UsuarioRepository;
import com.example.apireactcrud.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AutenticacaoController {
    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public AutenticacaoController(AuthenticationManager authManager, TokenService tokenService, UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid LoginDto loginDto){
        UsernamePasswordAuthenticationToken login = loginDto.converter();
        try{
            Authentication authentication = authManager.authenticate(login);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/adduser")
    public Usuario adicionarUsuario(AdicionaUsuarioDto addDto){
        Perfil perfil = addDto.converterPerfil();
        Usuario usuario = addDto.converterUsuario(List.of(perfil));
        perfilRepository.save(perfil);
        return usuarioRepository.save(usuario);
    }
}
