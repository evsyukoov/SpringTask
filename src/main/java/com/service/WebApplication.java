package com.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


//curl -v https://openexchangerates.org/api/latest.json\?app_id\=30dc03f0ff7148cdbf3911c72e517c4a\&symbols\=RUB,EUR

@SpringBootApplication
@EnableFeignClients
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}