package com.example.demo.bilibili.plugin.service;

import com.example.demo.bilibili.Daum;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DaumDao extends JpaRepository<Daum, String> {


    @Cacheable("goodcache")
    @Query(value = "select * from GENERIC where CREATION_TIME >=?", nativeQuery = true)
    List<Daum> findAllByLatestTime(String latestSyncTime);

    List<Daum> findByC2cItemsNameContainsOrderByShowPrice(String name, Pageable pageable);

    List<Daum> findByOrderByShowPrice(Pageable pageable);

    @Cacheable("goodcache")
    @Query(value = "select trim(a.C2CITEMSNAME) as C2CITEMSNAME, a.SHOWPRICE, MAX(a.C2CITEMSID) as C2CITEMID\n" + "\n" + "      from GENERIC a\n" + "         , (select C2CITEMSNAME, min(SHOWPRICE) as minPrice\n" + "            from GENERIC\n" + "            group by C2CITEMSNAME) temp\n" + "         , GENERIC_DETAILDTOLIST\n" + "      where temp.C2CITEMSNAME = a.C2CITEMSNAME\n" + "        and GENERIC_DETAILDTOLIST.GENERIC_ID = a.ID\n" + "        and a.SHOWPRICE = temp.minPrice\n" + "        and SHOWPRICE * 100 / SHOWMARKETPRICE < 50\n" + "        and (a.C2CITEMSNAME like '%%')\n" + "        and a.SHOWPRICE >= 50\n" + "        and a.SHOWPRICE <= 800\n" + "        and a.CREATION_TIME > '2024-04-01 16:20:39'\n" + "      group by a.C2CITEMSNAME, a.SHOWPRICE ", nativeQuery = true)
    List<Daum> findMinPrice(Pageable pageable);

    @Cacheable("goodcache")
    @Query(value = "select MAX(a.C2CITEMSID) as C2CITEMID\n" + "from GENERIC a\n" + "   , (select C2CITEMSNAME, min(SHOWPRICE) as minPrice\n" + "      from GENERIC\n" + "      group by C2CITEMSNAME) temp\n" + "where temp.C2CITEMSNAME = a.C2CITEMSNAME\n" + "  and a.SHOWPRICE = temp.minPrice\n" + "  and SHOWPRICE * 100 / SHOWMARKETPRICE < :discount \n" + "  and (a.C2CITEMSNAME like %:name%  )\n" + "  and a.SHOWPRICE >= :minprice \n" + "  and a.SHOWPRICE <= :maxprice \n" + "  and a.CREATION_TIME >=  :latestSyncTime \n" + "group by a.C2CITEMSNAME, a.SHOWPRICE ", nativeQuery = true)
    List<Long> findAllMinPriceId(int minprice, int maxprice, String name, int discount, String latestSyncTime);


    @Cacheable("goodcache")
    List<Daum> findByC2cItemsIdInOrderByShowPrice(List<Long> ids, Pageable pageable);


}