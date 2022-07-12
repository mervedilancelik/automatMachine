package com.example.demo.controller;

import com.example.demo.exception.GeneralException;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.service.ProductService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ActiveProfiles("test")
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    Gson gson = new Gson();

    @Test
    void addProduct_Success() throws Exception {
        ProductDto productDto = new ProductDto("TEA", "HOT DRINK", 5.12);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/product/add")
                        .content(gson.toJson(productDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void addProduct_Exception() throws Exception {
        Mockito.when(productService.addProduct(Mockito.any())).thenThrow(GeneralException.class);
        ProductDto productDto = new ProductDto("TEA", "HOT DRINK", 5.12);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/product/add")
                        .content(gson.toJson(productDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();

    }
}