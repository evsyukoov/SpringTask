package com.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.*;


@SpringBootApplication
@EnableFeignClients
public class WebApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(WebApplication.class, args);
    }
}