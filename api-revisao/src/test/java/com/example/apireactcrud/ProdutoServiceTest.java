package com.example.apireactcrud;

import com.example.apireactcrud.model.Produto;
import com.example.apireactcrud.repository.ProdutoRepository;
import com.example.apireactcrud.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProdutoServiceTest {

    @MockBean
    ProdutoRepository repository;

    @Autowired
    ProdutoService service;

    @Test
    public void test4(){
        Produto produto = new Produto();
        produto.setNome("nome");
        produto.setDescricao("descricao");
        produto.setValor(10.0);
        produto.setId(1L);

        when(repository.save(any(Produto.class))).thenReturn(produto);

        Produto resultado = service.adicionaProduto(produto);

        Assertions.assertEquals(produto, resultado);
    }

}
