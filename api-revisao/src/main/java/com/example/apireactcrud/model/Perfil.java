package com.example.apireactcrud.model;


import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serial;

@Setter
@Entity
@Table(name="perfil")
public class Perfil implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    
    @Override
    public String getAuthority(){
        return nome;
    }
    

}
