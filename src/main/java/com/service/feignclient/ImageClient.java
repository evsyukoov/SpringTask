package com.service.feignclient;


import com.service.feigndata.ImageData.ImageData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


//curl -v https://api.giphy.com/v1/gifs/random?api_key=CmFSRbRbE5AjOyu1tqhr7UQiS8uRxcvQ&tag=rich

@FeignClient(url="${images.address}", name = "imageclient")
public interface ImageClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/gifs/random?api_key=${images.key}&tag={param}")
    ImageData getImage(@RequestParam("tag") String param);
}
