package com.service;

import com.service.FeignClient.ExchangeClient;
import com.service.FeignClient.ImageClient;
import com.service.FeignData.ExchangeData;
import com.service.FeignData.ImageData.ImageData;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


//curl -v https://openexchangerates.org/api/latest.json\?app_id\=30dc03f0ff7148cdbf3911c72e517c4a\&symbols\=RUB,EUR

@SpringBootApplication
@RestController
@EnableFeignClients
public class WebApplication {
    @Autowired
    private ExchangeClient exchangeClient;
    @Autowired
    private ImageClient imageClient;

    @Value("${base.currency}")
    private String currency;

    private String getYesterdayDate()
    {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        return yesterday.format(DateTimeFormatter.ISO_DATE);

    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleException(ResponseStatusException e)
    {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    private    ResponseStatusException analizeFeignExceptionCode(FeignException e, String apiName)
    {
        if (e.status() == 429)
            return new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, String.format("Превышен лимит запросов к %s", apiName));
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Проблемы на сервере %s", apiName));
    }

    @GetMapping("/*")
    public Object error()
    {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный запрос к сервису. См. документацию");
    }

    @GetMapping("/compare/{code}")
    public Object entrypoint(@PathVariable String code) {
        code = code.toUpperCase();
        if (code.equals(currency))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Сравнение не несет смысла");
        String param = String.format("%s,%s",currency, code);
        ExchangeData curr;
        ExchangeData yesterday;
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

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}