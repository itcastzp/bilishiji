package com.example.demo.bilibili.plugin.service;

import com.example.demo.LatestSync;

public interface GoodsService {
    void saveLatestSyncDao(LatestSync latestSync);

    int cleanExpiredGoods();
}
