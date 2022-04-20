package com.example.apireactcrud.controller.dto;

import com.example.apireactcrud.model.Perfil;
import com.example.apireactcrud.model.Usuario;

import java.util.List;

public class AdicionaUsuarioDto {
    private String username;
    private String password;
    private String role;

    public Usuario converterUsuario(List<Perfil> perfis){
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setPerfis(perfis);
        return usuario;
    }

    public Perfil converterPerfil(){
        Perfil perfil = new Perfil();
        perfil.setNome(role);
        return perfil;
    }

}
