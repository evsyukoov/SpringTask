package com.service;

import java.util.HashMap;

public class ExchangeRatesComparator {
    HashMap<String, Double> current;
    HashMap<String, Double> yesterday;
    String currency;
    String baseCurrency;

    public ExchangeRatesComparator(HashMap<String, Double> current, HashMap<String, Double> yesterday, String currency, String baseCurrency) {
        this.current = current;
        this.yesterday = yesterday;
        this.currency = currency;
        this.baseCurrency = baseCurrency;
    }

    public String compare()
    {
        double now = current.get(baseCurrency) / current.get(currency);
       double history = yesterday.get(baseCurrency) / yesterday.get(currency);
       //про равенство курсов сегодняшнего и вчерашнего ничего не сказано, будуем возвращать хорошую картинку;
       return now >= history ? "rich" : "broken";
    }

}
