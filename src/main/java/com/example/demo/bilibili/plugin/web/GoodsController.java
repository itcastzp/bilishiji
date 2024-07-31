package com.example.demo.bilibili.plugin.web;

import com.example.demo.LatestSync;
import com.example.demo.bilibili.BiliBiliPlugin;
import com.example.demo.bilibili.Data;
import com.example.demo.bilibili.Daum;
import com.example.demo.bilibili.Root;
import com.example.demo.bilibili.plugin.service.DaumDao;
import com.example.demo.bilibili.plugin.service.LatestSyncDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoodsController {


    private final DaumDao daumDao;

    public GoodsController(DaumDao daumDao,
                           LatestSyncDao latestSyncDao) {
        this.daumDao = daumDao;
        this.latestSyncDao = latestSyncDao;
    }

    @Autowired
    private BiliBiliPlugin biliBiliPlugin;
    private final LatestSyncDao latestSyncDao;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


    @GetMapping("/Root")
    public Root listAll(int pageIndex) {
        List<Daum> all = daumDao.findByOrderByShowPrice(PageRequest.of(pageIndex, 10));
        Root root = new Root();
        Data data = new Data();
        data.setData(all);
        root.setData(data);
        System.out.println("查询页数：" + pageIndex);
        return root;
    }

    @GetMapping("/findByName")
    public Root findByName(String name, int pageIndex) {
        List<Daum> daumByC2cItemsNameIsLike = daumDao.findByC2cItemsNameContainsOrderByShowPrice(name, PageRequest.of(pageIndex, 10));
        Root root = new Root();
        Data data = new Data();
        data.setData(daumByC2cItemsNameIsLike);
        root.setData(data);
        return root;
    }


    @GetMapping("/findMinPrice")
    public Root findMinPrice(@RequestParam(name = "name", required = false, defaultValue = "") String name, @RequestParam(name = "minPrice", defaultValue = "50", required = false) int minPrice, @RequestParam(name = "maxPrice", defaultValue = "100", required = false) int maxPrice, Pageable pageable) {
        LatestSync latestSync = latestSyncDao.findLatestSync();
        List<Long> allMinPriceId = daumDao.findAllMinPriceId(minPrice, maxPrice, name, latestSync.getLatestSync());
        List<Daum> byC2cItemsIdIn = daumDao.findByC2cItemsIdInOrderByShowPrice(allMinPriceId, pageable);
        Root root = new Root();
        Data data = new Data();
        data.setData(byC2cItemsIdIn);
        root.setData(data);
        return root;
    }

    @GetMapping("/sync")
    public String sync() throws Exception {
        biliBiliPlugin.sync();
        return "Scheduling";
    }


}
