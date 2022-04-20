package com.example.apireactcrud.controller.dto;

import com.example.apireactcrud.model.Produto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class NovoProdutoDto {
    @NotBlank
    private String nome;
    @Pattern(regexp="[0-9]{1,13}(\\.[0-9]{2})?", message = "Campo deve ser um número válido")
    private String valor;
    @NotBlank
    private String descricao;

    public Produto converter(){
        Produto produto = new Produto();
        produto.setDescricao(descricao);
        produto.setNome(nome);
        produto.setValor(Double.valueOf(valor));
        produto.setData(LocalDate.now());
        return produto;
    }
}
