package com.example.apireactcrud.service;

import com.example.apireactcrud.model.Produto;
import com.example.apireactcrud.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest2 {

    @Test
    public void test4(){
//        ProdutoService service = new ProdutoService(repository);
        Produto produto = new Produto();
        produto.setNome("nome");
        produto.setDescricao("descricao");
        produto.setValor(10.0);
        produto.setId(1L);

        ProdutoRepository repository = mock(ProdutoRepository.class);

        when(repository.save(any(Produto.class))).thenReturn(produto);

        ProdutoService service = new ProdutoService(repository);

//        Produto resultado = service.adicionaProduto(produto);
        verify(service).adicionaProduto(produto);

//        Assertions.assertEquals(produto, resultado);
    }

}
