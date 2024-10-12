package com.example.demo.bilibili.plugin.service.impl;

import com.example.demo.bilibili.Figure;
import com.example.demo.bilibili.FigureSeries;
import com.example.demo.bilibili.plugin.service.FigureCrawlerService;
import com.example.demo.bilibili.plugin.service.FigureDao;
import com.example.demo.bilibili.plugin.service.FigureSeriesDao;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BanPrestoCrawlerService implements FigureCrawlerService {

    @Autowired
    private FigureDao figureDao;

    @Autowired
    private FigureSeriesDao figureSeriesDao;

    @Override
    @Async
    public void crawler() {


        Connection session = Jsoup.newSession().timeout(0).userAgent("FooBar 2000");


        Document document = null;
        try {
            document = session.newRequest().url("https://www.banpresto.jp/prize/index.html").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements figure = document.getElementsByTag("figure");
        Elements figureLink = figure.select("a[href]");
        Map<String, String> seriesMap = new HashMap<String, String>();
        Map<String, String> seriesImageMap = new HashMap<String, String>();
        for (Element s : figureLink) {
            String series = s.select("p").text();
            String seriesIndex = s.attr("abs:href");
            seriesMap.put(series, seriesIndex);
            System.out.println(series + "系列：" + seriesIndex);
            String mainImgURI = s.select("img").attr("abs:src");
            seriesImageMap.put(series, mainImgURI);
        }

        for (Map.Entry<String, String> stringStringEntry : seriesImageMap.entrySet()) {
            String mainImgURI = stringStringEntry.getValue();
            FigureSeries figureSeries = FigureSeries.builder().imageUrl(mainImgURI).name(stringStringEntry.getKey()).build();
            List<FigureSeries> byName = figureSeriesDao.findByName(figureSeries.getName());
            if (byName.stream().findAny().isEmpty()) {
                figureSeriesDao.save(figureSeries);
            }
        }

        int i = 0;
        for (Map.Entry<String, String> seriesHtmlMapping : seriesMap.entrySet()) {

            String seriesName = seriesHtmlMapping.getKey();
            String seriesHtmlIndex = seriesHtmlMapping.getValue();
            log.error("开始方位页面：{}", seriesHtmlIndex);
            Document dragonBallSeriesDocument = null;
            try {
                dragonBallSeriesDocument = session.newRequest(seriesHtmlIndex).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Elements drgonBallFigureElements = dragonBallSeriesDocument.select("figure");
            HashMap<String, String> objectObjectHashMap = Maps.newHashMap();
            for (Element dragonBallSeries : drgonBallFigureElements) {
                String name = dragonBallSeries.select("figcaption").text();
                String imageURI = dragonBallSeries.select("img[src]").attr("abs:src");
                objectObjectHashMap.put(name, imageURI);
            }
            for (Map.Entry<String, String> stringStringEntry : objectObjectHashMap.entrySet()) {
                String name = stringStringEntry.getKey();
                if (name != null || !name.trim().isEmpty()) {
                    String url = stringStringEntry.getValue();
                    String mainImgURI = stringStringEntry.getValue();
                    FigureSeries series = figureSeriesDao.findByName(seriesName).stream().findFirst().get();
                    Figure figureVO = Figure.builder().name(name).series(FigureSeries.builder().id(series.getId()).build()).imageUrl(mainImgURI).url(url).build();
                    if (figureDao.findByName(name).stream().findAny().isEmpty()) {
                        i++;
                        figureDao.save(figureVO);
                    }
                }
            }

        }


        log.error("本次保存手办数据总共：{}", i);


    }

    public static void main(String[] args) throws Exception {
        new BanPrestoCrawlerService().crawler();
    }
}
