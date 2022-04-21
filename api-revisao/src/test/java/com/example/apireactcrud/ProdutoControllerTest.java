package com.example.apireactcrud;

import com.example.apireactcrud.controller.dto.LoginDto;
import com.example.apireactcrud.model.Produto;
import com.example.apireactcrud.service.ProdutoService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
public class ProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProdutoService service;

    @Test
    public void test() throws Exception {
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

//        verify
        //executando controller com o token
        mockMvc.perform(MockMvcRequestBuilders.get("/produto")
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json" )
                )
                .andExpect(status().isFound());

    }

    @Test
    public void test2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produto")
                        .header("Content-Type", "application/json")
                )
                .andExpect(status().isForbidden());

    }

    @Test
    public void test3() throws Exception {
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
