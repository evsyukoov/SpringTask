package com.service.controller;


import com.service.ExchangeRatesComparator;
import com.service.feignclient.ExchangeClient;
import com.service.feignclient.ImageClient;
import com.service.feigndata.ExchangeData;
import com.service.feigndata.ImageData.ImageData;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@RestController
@RequestMapping
public class ApiController {

    @Autowired
    private ExchangeClient exchangeClient;
    @Autowired
    private ImageClient imageClient;

    @Value("${base.currency}")
    private String currency;

    public ApiController(String currency) {
        this.currency = currency;
    }

    public ApiController() {
    }

    private String getYesterdayDate()
    {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        return yesterday.format(DateTimeFormatter.ISO_DATE);

    }

    private    ResponseStatusException analizeFeignExceptionCode(FeignException e, String apiName)
    {
        if (e.status() == 429)
            return new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, String.format("Превышен лимит запросов к %s", apiName));
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Проблемы на сервере %s", apiName));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleException(ResponseStatusException e)
    {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @GetMapping("/compare/{code}")
    public Object API(@PathVariable String code) {
        code = code.toUpperCase();
        if (code.equals(currency))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Сравнение не несет смысла");
        String param = String.format("%s,%s",currency, code);
        return getDataFromServices(exchangeClient, imageClient, code);
    }

    public  RedirectView getDataFromServices(ExchangeClient exchangeClient, ImageClient imageClient, String code)
    {
        ExchangeData curr;
        ExchangeData yesterday;
        String param = String.format("%s,%s", currency,code);
        try {
            curr = exchangeClient.getCurrentData(param);
            yesterday = exchangeClient.getYesterdayData(param, getYesterdayDate());
        }
        catch (FeignException e)
        {
            throw analizeFeignExceptionCode(e, "ExchangeAPI");
        }
        HashMap<String, Double> m1 = curr.getRates();
        HashMap<String, Double> m2 = yesterday.getRates();
        if (m1.size() == 1 || m2.size() == 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Необходимо задать правильную валюту");
        ExchangeRatesComparator erc = new ExchangeRatesComparator(m1, m2, code, currency);
        String tag = erc.compare();
        ImageData imgData;
        try {
            imgData = imageClient.getImage(tag);
        }
        catch (FeignException e)
        {
            throw analizeFeignExceptionCode(e, "GiphyAPI");
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(imgData.getData().getImage_url());
        return redirectView;
    }

}
