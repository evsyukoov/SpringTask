package com.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WebApplicationTest {

    @Autowired
    private WebApplication app;

    @Test
    public void contextLoads() throws Exception {
        assertThat(app).isNotNull();
    }

}
