package com.example.apireactcrud.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="produto")
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nome")
    private String nome;
    @Column(name="valor")
    private Double valor;
    @Column(name="descricao")
    private String descricao;
    @Column(name="data")
    private LocalDate data;
}
