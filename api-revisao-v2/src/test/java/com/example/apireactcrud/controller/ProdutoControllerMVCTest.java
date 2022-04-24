package com.example.apireactcrud.controller;

import com.example.apireactcrud.model.Produto;
import com.example.apireactcrud.repository.ProdutoRepository;
import com.example.apireactcrud.service.ProdutoService;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext
public class ProdutoControllerMVCTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProdutoService service;

    public void addUser() throws Exception{
        String body = "{\n" +
                "    \"username\":\"user\",\n" +
                "    \"password\":\"pass\",\n" +
                "    \"role\":\"Admin\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/adduser")
                        .content(body)
                        .header("Content-Type", "application/json" )
                )
                .andExpect(status().isOk());
    }

    @Test
    public void test() throws Exception {
        addUser();
        // gerando o token
        String body2 = "{\n" +
                "    \"username\":\"user\",\n" +
                "    \"password\":\"pass\"\n" +
                "}";
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/auth")
                        .content(body2)
                        .header("Content-Type", "application/json" )
                )
                .andExpect(status().isOk()).andReturn();

//        extraindo o token
        String response = result.getResponse().getContentAsString();

        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        Assertions.assertNotNull(token);

        //executando controller com o token
        mockMvc.perform(MockMvcRequestBuilders.get("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json" )
                )
                .andExpect(status().isFound());

    }

    @Test
    public void test2() throws Exception {
        addUser();
        mockMvc.perform(MockMvcRequestBuilders.get("/produto")
                        .header("Content-Type", "application/json")
                )
                .andExpect(status().isForbidden());

    }

    @Test
    public void test3() throws Exception {
        addUser();
        // gerando o token
        String body = "{\n" +
                "    \"username\":\"user\",\n" +
                "    \"password\":\"pass\"\n" +
                "}";
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/auth").content(body)
                        .header("Content-Type", "application/json" )
                )
                .andExpect(status().isOk()).andReturn();

        // extraindo o token
        String response = result.getResponse().getContentAsString();

        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        Assertions.assertNotNull(token);

        Produto produto = new Produto();
        produto.setNome("nome");
        produto.setDescricao("descricao");
        produto.setValor(10.0);
        produto.setId(1L);

        when(service.adicionaProduto(any(Produto.class))).thenReturn(produto);

        String bodyRequisicao = "{\n" +
                "    \"nome\":\"nome\",\n" +
                "    \"valor\":10.0,\n" +
                "    \"descricao\":\"descricao\"\n" +
                "}";

//        verify
        //executando controller com o token
        MvcResult resultPost = mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json" )
                        .content(bodyRequisicao)
                )
                .andExpect(status().isOk())
                .andReturn();
        String responsePost = resultPost.getResponse().getContentAsString();
        Assertions.assertNotEquals(responsePost, "");
        Assertions.assertEquals
                ("{\"id\":1,\"nome\":\"nome\",\"valor\":10.0,\"descricao\":\"descricao\",\"data\":null}",
                        responsePost);
    }



}
