package com.example.demo.bilibili.plugin.service;

import com.example.demo.LatestSync;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LatestSyncDao extends JpaRepository<LatestSync, String> {

    @Cacheable("goodcache")
    @Query(value = "select * from GENERIC_LATEST_SYNC where id=(select  max(id) from GENERIC_LATEST_SYNC )", nativeQuery = true)
    LatestSync findLatestSync();
}
