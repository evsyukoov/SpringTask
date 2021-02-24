package com.service;

import com.service.controller.MainController;
import org.junit.jupiter.api.Test;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {


    @Autowired
    private MainController mainController;

    @Autowired
    private MockMvc mockMvc;

    @Value("${base.currency}")
    private String currency;


    @Test
    public void contextLoads() throws Exception {
        assertThat(mainController).isNotNull();
    }

    @Test
    public void testMyServiceError() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().is4xxClientError()).
                andExpect(content().string("400 BAD_REQUEST \"Неверный запрос к сервису. См. документацию\""));
    }

    @Test
    public void testMyServiceError2() throws Exception {
        this.mockMvc.perform(get("/fwefeqf")).andDo(print())
                .andExpect(status().is4xxClientError()).
                andExpect(content().string("400 BAD_REQUEST \"Неверный запрос к сервису. См. документацию\""));
    }

    @Test
    public void testMyServiceErrorCurrency() throws Exception {
        this.mockMvc.perform(get("/compare/" + currency)).andDo(print())
                .andExpect(status().is4xxClientError()).
                andExpect(content().string("400 BAD_REQUEST \"Сравнение не несет смысла\""));
    }

    @Test
    public void testMyServiceGoodRequest() throws Exception {
        this.mockMvc.perform(get("/compare/EUR")).andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testMyServiceErrorCurrency3() throws Exception {
        this.mockMvc.perform(get("/compare/EURRRR")).andDo(print())
                .andExpect(status().is4xxClientError()).
                andExpect(content().string("400 BAD_REQUEST \"Необходимо задать правильную валюту\""));
    }


}
