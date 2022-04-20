package com.example.apireactcrud;

import com.example.apireactcrud.controller.dto.LoginDto;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        // gerando o token
        String body = "{\"username\":\"user\",\"password\":\"pass\"}";
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/auth").content(body))
                .andExpect(status().isOk()).andReturn();

        // extraindo o token
        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        String token = (String) json.get("token");

        //executando controller com o token
        mockMvc.perform(MockMvcRequestBuilders.get("/produto")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }
}
