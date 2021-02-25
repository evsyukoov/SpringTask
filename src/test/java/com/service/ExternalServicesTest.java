package com.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.service.controller.ApiController;
import com.service.feignclient.ExchangeClient;
import com.service.feignclient.ImageClient;
import com.service.feigndata.ExchangeData;
import com.service.feigndata.ImageData.ImageData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.view.RedirectView;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExternalServicesTest {

    private String currency = "RUB";

    @MockBean
    ExchangeClient exchangeMock = mock(ExchangeClient.class);
    ImageClient imageMock = mock(ImageClient.class);


    ApiController apiController = new ApiController(currency);

    private String getYesterdayDate()
    {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        return yesterday.format(DateTimeFormatter.ISO_DATE);

    }

    @Test
    public void TestGoodResponceWhereGifIsRich() throws Exception {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();
        ExchangeData curr = gson.fromJson(new JsonReader(new FileReader("./src/test/resources/__files/good_exchange_curr1.json")),
                ExchangeData.class);
        ExchangeData yesterday = gson.fromJson(new JsonReader(new FileReader("./src/test/resources/__files/good_exchange_yesterday1.json")),
                ExchangeData.class);
        Mockito.when(exchangeMock.getCurrentData("RUB,EUR")).thenReturn(curr);
        Mockito.when(exchangeMock.getYesterdayData("RUB,EUR", getYesterdayDate())).thenReturn(yesterday);
        ImageData imageRich = gson.fromJson(new JsonReader(new FileReader("./src/test/resources/__files/good_image1.json")),
                ImageData.class);
        ImageData imageBroke = gson.fromJson(new JsonReader(new FileReader("./src/test/resources/__files/broken_image1.json")),
                ImageData.class);
        Mockito.when(imageMock.getImage("rich")).thenReturn(imageRich);
        Mockito.when(imageMock.getImage("broke")).thenReturn(imageBroke);
        RedirectView rv = (RedirectView) apiController.getDataFromServices(exchangeMock, imageMock, "EUR");
        assertEquals("https://media0.giphy.com/media/d2rSV6YRv3DxJRkF0O/giphy.gif", rv.getUrl());
    }

    @Test
    public void TestGoodResponceWhereGifIsBroke() throws Exception {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();
        ExchangeData curr = gson.fromJson(new JsonReader(new FileReader("./src/test/resources/__files/good_exchange_curr2.json")),
                ExchangeData.class);
        ExchangeData yesterday = gson.fromJson(new JsonReader(new FileReader("./src/test/resources/__files/good_exchange_yesterday1.json")),
                ExchangeData.class);

        Mockito.when(exchangeMock.getCurrentData("RUB,EUR")).thenReturn(curr);
        Mockito.when(exchangeMock.getYesterdayData("RUB,EUR", getYesterdayDate())).thenReturn(yesterday);
        ImageData imageRich = gson.fromJson(new JsonReader(new FileReader("./src/test/resources/__files/good_image1.json")),
                ImageData.class);
        ImageData imageBroke = gson.fromJson(new JsonReader(new FileReader("./src/test/resources/__files/broken_image1.json")),
                ImageData.class);
        Mockito.when(imageMock.getImage("rich")).thenReturn(imageRich);
        Mockito.when(imageMock.getImage("broken")).thenReturn(imageBroke);
        RedirectView rv = (RedirectView) apiController.getDataFromServices(exchangeMock, imageMock, "EUR");
        assertEquals("https://media4.giphy.com/media/ehraVuPSZddEl5LW1x/giphy.gif", rv.getUrl());
    }


}
