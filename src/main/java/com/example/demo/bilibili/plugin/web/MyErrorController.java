package com.example.demo.bilibili.plugin.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyErrorController implements ErrorController {


    private final Gson gson;

    public MyErrorController(Gson gson) {
        this.gson = gson;
    }


    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String handleError() {
        //do something like logging
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", 100000);
        jsonObject.addProperty("message", "非法请求服务器出现错误");
        jsonObject.addProperty("status", "500");
        return gson.toJson(jsonObject);
    }
}