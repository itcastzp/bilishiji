package com.example.demo.bilibili.plugin.service.impl;

import com.example.demo.LatestSync;
import com.example.demo.QueryRespDto;
import com.example.demo.bilibili.Daum;
import com.example.demo.bilibili.plugin.service.DaumDao;
import com.example.demo.bilibili.plugin.service.GoodsService;
import com.example.demo.bilibili.plugin.service.LatestSyncDao;
import com.google.gson.Gson;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    public static final Gson GSON = new Gson();

    @Autowired
    private DaumDao daumDao;

    @Autowired
    private LatestSyncDao latestSyncDao;

    @Override
    public void saveLatestSyncDao(LatestSync latestSync) {
        latestSyncDao.save(latestSync);
        if (true) {
            throw new RuntimeException("保存报错");
        }
    }

    @Override
    @CacheEvict(value = "goodcache",allEntries = true)
    public int cleanExpiredGoods() {
        AtomicInteger record = new AtomicInteger();
        LatestSync latestSync = latestSyncDao.findLatestSync();
        List<Daum> all = daumDao.findAllByLatestTime("1999-01-01 00:00:00");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).setConnectionRequestTimeout(3000).build();

        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        all.stream().forEach(daumVO -> {
                    String url = "https://mall.bilibili.com/mall-magic-c/internet/c2c/items/queryC2cItemsDetail?c2cItemsId=%s";
                    HttpGet get = new HttpGet(String.format(url, daumVO.getC2cItemsId()));
                    CloseableHttpResponse execute = null;
                    try {
                        execute = closeableHttpClient.execute(get);
                        String responseJson = EntityUtils.toString(execute.getEntity());
                        QueryRespDto queryRespDto = GSON.fromJson(responseJson, QueryRespDto.class);
                        if (queryRespDto.getData() != null) {
                            //发布下架删除。
                            if (2 == queryRespDto.getData().getPublishStatus()) {
                                daumDao.delete(daumVO);
                                record.getAndIncrement();
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

        );
        return record.get();

    }
}
