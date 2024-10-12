package com.example.demo.bilibili.plugin.web;

import com.example.demo.bilibili.plugin.service.FigureCrawlerService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerController {
    @Autowired
    private FigureCrawlerService figureCrawlerService;

    @GetMapping("/crawl")
    public String crawl(String url) {
        figureCrawlerService.crawler();
        JsonObject jsonObject  = new JsonObject();
        jsonObject.addProperty("success", true);
        jsonObject.addProperty("msg", "OK");
        return jsonObject.toString();

    }

}
