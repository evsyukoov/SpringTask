package com.service.FeignData;

import com.fasterxml.jackson.annotation.JsonProperty;;

import java.util.HashMap;


//данные из exchange api

public class ExchangeData {

    @JsonProperty("rates")
    private HashMap<String, Double> rates = new HashMap<>();

    public HashMap<String, Double> getRates() {
        return rates;
    }

}
