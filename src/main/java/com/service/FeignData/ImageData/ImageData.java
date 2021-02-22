package com.service.FeignData.ImageData;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class ImageData {
    @JsonProperty("data")
    private Data data;

    @JsonProperty("meta")
    private HashMap<String, String> meta;

    public Data getData() {
        return data;
    }

    public HashMap<String, String> getMeta() {
        return meta;
    }
}

