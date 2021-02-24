package com.service.feigndata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;;

import java.util.HashMap;


//данные из exchange api

public class ExchangeData {
    @JsonProperty("rates")
    private HashMap<String, Double> rates = new HashMap<>();

    public HashMap<String, Double> getRates() {
        return rates;
    }

}
