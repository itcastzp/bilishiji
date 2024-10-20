package com.example.demo.bilibili.plugin.service.impl;

import com.example.demo.bilibili.Figure;
import com.example.demo.bilibili.FigureSeries;
import com.example.demo.bilibili.plugin.service.FigureCrawlerService;
import com.example.demo.bilibili.plugin.service.FigureDao;
import com.example.demo.bilibili.plugin.service.FigureSeriesDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Service
//@Transactional
public class BanPrestoCrawlerService implements FigureCrawlerService {
    private ExecutorService executor = Executors.newCachedThreadPool();
    @Autowired
    private FigureDao figureDao;

    @Autowired
    private FigureSeriesDao figureSeriesDao;


    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map, boolean isDesc) {
        Map<K, V> result = Maps.newLinkedHashMap();
        if (isDesc) {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey().reversed())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        } else {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }
        return result;
    }

    @Override
    @Async

    public void crawler() {


        Connection session = Jsoup.newSession();

        Document document = null;
        FutureTask<Document> task = new FutureTask(() -> {
            try {
                log.info("开始访问bandi页面-----------------------------");
                return session.newRequest().url("https://www.banpresto.jp/cn/prize/index.html").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        try {

            executor.execute(task);
            document = task.get(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            throw new RuntimeException("获取 bandi 页面数据超时");
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

        Map<String, String> sortByKey = sortByKey(seriesImageMap, true);
        List<FigureSeries> needSaveSeries = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : sortByKey.entrySet()) {
            String mainImgURI = stringStringEntry.getValue();
            FigureSeries figureSeries = FigureSeries.builder().imageUrl(mainImgURI).name(stringStringEntry.getKey()).build();
            List<FigureSeries> byName = figureSeriesDao.findByName(figureSeries.getName());
            if (!StringUtils.isEmpty(mainImgURI)) {

                if (byName.stream().findAny().isEmpty()) {
                    needSaveSeries.add(figureSeries);
                }

            }
        }
        log.info("保存系列：{}", needSaveSeries);
        figureSeriesDao.saveAllAndFlush(needSaveSeries);
        Map<String, String> sortByKeyseriesMap = sortByKey(seriesMap, true);
        int i = 0;
        for (Map.Entry<String, String> seriesHtmlMapping : sortByKeyseriesMap.entrySet()) {

            String seriesName = seriesHtmlMapping.getKey();
            String seriesHtmlIndex = seriesHtmlMapping.getValue();
            log.info("开始访问系列{}页面：{}", seriesName, seriesHtmlIndex);
            Document dragonBallSeriesDocument = null;
            try {
                dragonBallSeriesDocument = session.newRequest(seriesHtmlIndex).get();
            } catch (IOException e) {
                log.error("错误访问系列{}页面：{}", seriesName, seriesHtmlIndex);
                throw new RuntimeException(e);
            }
            try {
                TimeUnit.SECONDS.sleep(5);
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
            int num = 0;
            List<Figure> needSaveFigures = Lists.newArrayList();
            for (Map.Entry<String, String> stringStringEntry : objectObjectHashMap.entrySet()) {
                String name = stringStringEntry.getKey();
                if (name != null && !name.trim().isEmpty()) {
                    String url = stringStringEntry.getValue();
                    String mainImgURI = stringStringEntry.getValue();
                    FigureSeries series = figureSeriesDao.findByName(seriesName).stream().findFirst().get();
                    Figure figureVO = Figure.builder().name(name).series(FigureSeries.builder().id(series.getId()).build()).imageUrl(mainImgURI).url(url).build();
                    if (!StringUtils.isEmpty(mainImgURI) && figureDao.findByName(name).stream().findAny().isEmpty()) {
                        i++;
                        num++;
                        log.info("保存手办：{}", figureVO);
                        needSaveFigures.add(figureVO);

                    }
                }
            }
            figureDao.saveAllAndFlush(needSaveFigures);
            log.info("---------{}系列存储{}条", seriesName, num);

        }


        log.error("本次保存手办数据总共：{}", i);


    }

    public static void main(String[] args) throws Exception {
        new BanPrestoCrawlerService().crawler();
    }
}
