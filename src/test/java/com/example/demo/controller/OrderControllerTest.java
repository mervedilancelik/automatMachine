package com.example.demo.controller;

import com.example.demo.exception.GeneralException;
import com.example.demo.model.dto.PaymentTypeDto;
import com.example.demo.service.OrderService;
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
@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    Gson gson = new Gson();

    @Test
    void selectProduct_Success() throws Exception {
        Integer productId = 1;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/select-product")
                        .param("productId", productId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void selectProduct_Exception() throws Exception {
        Mockito.when(orderService.selectProduct(Mockito.any())).thenThrow(GeneralException.class);
        Integer productId = 1;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/select-product")
                        .param("productId", productId.toString()))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();
    }

    @Test
    void selectProductQuantity_Success() throws Exception {
        Integer quantity = 5;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/select-productQuantity")
                        .param("quantity", quantity.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void selectProductQuantity_Exception() throws Exception {
        Mockito.when(orderService.selectProductQuantity(Mockito.any())).thenThrow(GeneralException.class);
        Integer quantity = 5;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/select-productQuantity")
                        .param("quantity", quantity.toString()))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();
    }

    @Test
    void selectSugarQuantity_Success() throws Exception {
        Integer sugarQuantity = 2;
        Integer productId = 1;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/select-sugarQuantity")
                        .param("sugarQuantity", sugarQuantity.toString())
                        .param("productId", productId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void selectSugarQuantity_Exception() throws Exception {
        Mockito.when(orderService.selectSugarQuantity(Mockito.any(), Mockito.any())).thenThrow(GeneralException.class);
        Integer sugarQuantity = 2;
        Integer productId = 1;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/select-sugarQuantity")
                        .param("sugarQuantity", sugarQuantity.toString())
                        .param("productId", productId.toString()))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();
    }

    @Test
    void selectPaymentType_Success() throws Exception {
        PaymentTypeDto paymentTypeDto = new PaymentTypeDto("CASH", "PAPER MONEY");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/select-paymentType")
                        .content(gson.toJson(paymentTypeDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void selectPaymentType_Exception() throws Exception {
        Mockito.when(orderService.selectPaymentType(Mockito.any())).thenThrow(GeneralException.class);

        PaymentTypeDto paymentTypeDto = new PaymentTypeDto("CASH", "PAPER MONEY");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/select-paymentType")
                        .content(gson.toJson(paymentTypeDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();
    }

    @Test
    void refund_Success() throws Exception {

        Double money = 90.0;
        Integer productId = 1;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/refund")
                        .param("money", money.toString())
                        .param("productId", productId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void refund_Exception() throws Exception {
        Mockito.when(orderService.refundMoney(Mockito.any(), Mockito.any())).thenThrow(GeneralException.class);

        Double money = 90.0;
        Integer productId = 1;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/refund")
                        .param("money", money.toString())
                        .param("productId", productId.toString()))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();
    }

    @Test
    void receipt_Success() throws Exception {
        Integer productId = 1;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/order/receipt")
                        .param("productId", productId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void receipt_Exception() throws Exception {
        Mockito.when(orderService.receipt(Mockito.any())).thenThrow(GeneralException.class);
        Integer productId = 1;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/order/receipt")
                        .param("productId", productId.toString()))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();
    }
}