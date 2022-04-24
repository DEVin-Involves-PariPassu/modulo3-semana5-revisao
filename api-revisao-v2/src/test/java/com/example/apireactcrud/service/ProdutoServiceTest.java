package com.example.apireactcrud.service;

import com.example.apireactcrud.model.Produto;
import com.example.apireactcrud.repository.ProdutoRepository;
import com.example.apireactcrud.service.ProdutoService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProdutoServiceTest {

    @Mock
    ProdutoRepository repository;

    @InjectMocks
    ProdutoService service;

    @BeforeEach
    void initUseCase() {

    }

    @Test
    public void test4(){
//        ProdutoService service = new ProdutoService(repository);
        Produto produto = new Produto();
        produto.setNome("nome");
        produto.setDescricao("descricao");
        produto.setValor(10.0);
        produto.setId(1L);

        when(repository.save(any(Produto.class))).thenReturn(produto);

        Produto resultado = service.adicionaProduto(produto);
//        verify(service).adicionaProduto(produto);

        Assertions.assertEquals(produto, resultado);
    }

}
