package com.example.apireactcrud.controller.dto;

import com.example.apireactcrud.model.Perfil;
import com.example.apireactcrud.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AdicionaUsuarioDto {
    private String username;
    private String password;
    private String role;


    public Usuario converterUsuario(List<Perfil> perfis){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode(password));
        usuario.setPerfis(perfis);
        return usuario;
    }

    public Perfil converterPerfil(){
        Perfil perfil = new Perfil();
        perfil.setNome(role);
        return perfil;
    }

}
