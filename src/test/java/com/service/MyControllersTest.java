package com.service;

import com.service.controller.ApiController;
import org.junit.jupiter.api.Test;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MyControllersTest {


    @Autowired
    private ApiController apiController;

    @Autowired
    private MockMvc mockMvc;

    @Value("${base.currency}")
    private String currency;


    @Test
    public void contextLoads() throws Exception {
        assertThat(apiController).isNotNull();
    }

    @Test
    public void testMyMainPage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    public void testMyServiceError404() throws Exception {
        this.mockMvc.perform(get("/fwefeqf")).andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void testMyServiceErrorCurrency() throws Exception {
        this.mockMvc.perform(get("/compare/" + currency)).andDo(print())
                .andExpect(status().is(400)).
                andExpect(content().string("400 BAD_REQUEST \"Сравнение не несет смысла\""));
    }

    @Test
    public void testMyServiceGoodRequest() throws Exception {
        this.mockMvc.perform(get("/compare/EUR")).andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testMyServiceError400() throws Exception {
        this.mockMvc.perform(get("/compare/EURRRR")).andDo(print())
                .andExpect(status().is(400)).
                andExpect(content().string("400 BAD_REQUEST \"Необходимо задать правильную валюту\""));
    }

    @Test
    public void testMyServiceError400_2() throws Exception {
        this.mockMvc.perform(get("/compare/EUR/heh")).andDo(print())
                .andExpect(status().is(404));
    }


}
