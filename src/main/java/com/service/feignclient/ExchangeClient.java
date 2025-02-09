package com.service.feignclient;

import com.service.feigndata.ExchangeData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//curl -v https://openexchangerates.org/api/latest.json\?app_id\=30dc03f0ff7148cdbf3911c72e517c4a\&symbols\=RUB,EUR

@FeignClient(url="${exchange.address}", name = "exchangeclient")
public interface ExchangeClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/latest.json?app_id=${exchange.key}&symbols={params}")
    ExchangeData getCurrentData(@RequestParam("symbols") String params);

    @RequestMapping(method = RequestMethod.GET, value = "/api/historical/{date}.json?app_id=${exchange.key}&symbols={params}")
    ExchangeData getYesterdayData(@RequestParam("symbols") String params, @PathVariable("date") String date);
}
