package com.example.demo.bilibili;


import com.example.demo.LatestSync;
import com.example.demo.bilibili.plugin.service.LatestSyncDao;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.hsqldb.lib.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@ConfigurationProperties
public class BiliBiliPlugin {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    public static final String DB_NAME = "ncc";
    public static final Gson GSON = new Gson();
    private static final ExecutorService service = Executors.newSingleThreadExecutor();

    private static final BlockingQueue<Root> BLOCKING_QUEUE = new ArrayBlockingQueue<>(1000);
    private static final CountDownLatch LATCH = new CountDownLatch(1);

    private static int allsuccess = 0;
    private static AtomicBoolean isSyncing = new AtomicBoolean();

    @Autowired
    private LatestSyncDao syncDao;

    public BlockingQueue<Root> watchBlockingQueue() {

        BlockingQueue<Root> blockingQueue = new LinkedBlockingQueue<>(BLOCKING_QUEUE);
        return blockingQueue;
    }

    @Async
    @CacheEvict(value = "goodcache",allEntries = true)
    public void sync() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        if(true)
            return;
        if (!isSyncing.get()) {
            isSyncing.compareAndSet(false, true);
            StopWatch stopWatch = new StopWatch();
            URI build = new URIBuilder("https://mall.bilibili.com/mall-magic-c/internet/c2c/v2/list").build();
            CookieStore store = new BasicCookieStore();
            final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).setConnectionRequestTimeout(3000).build();

//        BasicClientCookie cookie = new BasicClientCookie("SESSDATA", "057af4d4%2C1727164306%2C3167f%2A31CjAptDQ1WcgxZdsqDDardMclIjwpt-0KBuLSONOyqFm4Ch81EhmhQ_Ufac0JoahI_OASVkJrc3J6Ymg5Vm5pM0JoWXZmX2M4Z0xJeHFhYWlIRzJ3NGthZnRBSEpnLUVwUS1USWwyc09YczkzcUpZbjhqVl9zeXJ5dmUyUllZd09NMlcyUGVTdVd3IIEC");

            String cookieStrOld = "SESSDATA=057af4d4%2C1727164306%2C3167f%2A31CjAptDQ1WcgxZdsqDDardMclIjwpt-0KBuLSONOyqFm4Ch81EhmhQ_Ufac0JoahI_OASVkJrc3J6Ymg5Vm5pM0JoWXZmX2M4Z0xJeHFhYWlIRzJ3NGthZnRBSEpnLUVwUS1USWwyc09YczkzcUpZbjhqVl9zeXJ5dmUyUllZd09NMlcyUGVTdVd3IIEC; _uuid=5D426A8A-4577-66F3-9452-34E2D1BC41F538761infoc; buvid3=98EA12CB-9251-4FE2-99A2-E1A2DD28B30027548infoc; rpdid=|(u))~|RR~kY0J'u~uumYklll; _xid=DR%5CM69ArlWOcqqkIZgEdmcXOEhnIuhzehu1oXV9Km1N7A9aYrjw5ogp8TMAJOO885i4bWf3AkKd9XXhCoIgRVbgiy479mFfLu3v2n7BgZsNJMAyJqdjpnWLJtZoHN6x5; 9AD585D8A7CB034A=yizDkS3h-1712045043363-8ddabe6a20a191157320539; 1735D64331DF397E=1RmeF%5CzGpVhbTTmIMRi9hkXsn10ioZwFweYpRDmX2xZmkIKZdjDO3eTAaoTH2QjrU8434BdPGJeofSQ5giFyLSJynsYrLEy7k2TYWktXK7YkK2rA4gegNiEwlClEqhrA; b_nut=100; CURRENT_BLACKGAP=0; buvid4=2A102AE4-067E-2930-9307-EDD23753970608863-023082109-tzUqlq9E3xqp7ZyZuTLbtw%3D%3D; fingerprint=e2885d18ef7e4a5dfbfb38f84fa07565; buvid_fp_plain=undefined; CURRENT_QUALITY=80; bp_t_offset_283872216=930906330024116324; buvid_fp=e2885d18ef7e4a5dfbfb38f84fa07565; Hm_lvt_8d8d2f308d6e6dffaf586bd024670861=1715237043,1715333339,1715583221,1715754827; LIVE_BUVID=AUTO7117163593944770; PVID=4; CURRENT_FNVAL=4048";
            String cookieStr = "_uuid=5D426A8A-4577-66F3-9452-34E2D1BC41F538761infoc; buvid3=98EA12CB-9251-4FE2-99A2-E1A2DD28B30027548infoc; rpdid=|(u))~|RR~kY0J'u~uumYklll; _xid=DR%5CM69ArlWOcqqkIZgEdmcXOEhnIuhzehu1oXV9Km1N7A9aYrjw5ogp8TMAJOO885i4bWf3AkKd9XXhCoIgRVbgiy479mFfLu3v2n7BgZsNJMAyJqdjpnWLJtZoHN6x5; 9AD585D8A7CB034A=yizDkS3h-1712045043363-8ddabe6a20a191157320539; 1735D64331DF397E=1RmeF%5CzGpVhbTTmIMRi9hkXsn10ioZwFweYpRDmX2xZmkIKZdjDO3eTAaoTH2QjrU8434BdPGJeofSQ5giFyLSJynsYrLEy7k2TYWktXK7YkK2rA4gegNiEwlClEqhrA; b_nut=100; CURRENT_BLACKGAP=0; buvid4=2A102AE4-067E-2930-9307-EDD23753970608863-023082109-tzUqlq9E3xqp7ZyZuTLbtw%3D%3D; buvid_fp_plain=undefined; CURRENT_QUALITY=80; Hm_lvt_8d8d2f308d6e6dffaf586bd024670861=1715237043,1715333339,1715583221,1715754827; LIVE_BUVID=AUTO7117163593944770; PVID=4; fingerprint=56a46cf97908f905daee6316f3b38f51; DedeUserID=283872216; DedeUserID__ckMd5=8797b7fe8cc4f163; buvid_fp=56a46cf97908f905daee6316f3b38f51; SESSDATA=b805fd65%2C1737016000%2Ca128e%2A71CjAx3k4K-BTLAqILVHelnJikvXK2o855zXAXNxYMLy39RfnlHAYLZhhug72IzXPcmfYSVlAzUF9sa0ZUT184VlF6Zk5SNEVRTVBoMm9FMFlaZFRjVktobDdUQXVIV0JpMDRXS0pqYjJXQ2p5M2k0QllVN3pXa3hrRUlraWdyVEFNSGc0LWwzSlp3IIEC; bili_jct=37dd1e27664a5c14c7272ebab14e3106; sid=6o7skpn9; CURRENT_FNVAL=4048";

            StringTokenizer tokenizer = new StringTokenizer(cookieStr);
            while (tokenizer.hasMoreTokens()) {

                String trim = tokenizer.nextToken(";").trim();
                System.out.println(trim);
                String[] split = trim.split("=");
                String name = split[0].trim();
                String value = split[1].trim();
                BasicClientCookie cookie = new BasicClientCookie(name, value);
                cookie.setDomain("mall.bilibili.com");
                cookie.setPath("/");
                cookie.setVersion(0);
                java.util.Date from = Date.from(Instant.now().plus(150, ChronoUnit.DAYS));
                cookie.setExpiryDate(from);
                store.addCookie(cookie);

            }


            System.out.println(store.getCookies());
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultCookieStore(store).build();
            HttpPost request = new HttpPost(build);
            request.setConfig(requestConfig);
            request.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0"));
            request.addHeader(new BasicHeader("Referer", "https://mall.bilibili.com/neul-next/index.html?page=magic-market_index"));
            request.addHeader(new BasicHeader("Origin", "https://mall.bilibili.com"));
            HttpClientContext httpClientContext = HttpClientContext.create();
            httpClientContext.setCookieStore(store);
            JsonObject jsonObject = buildFirstRequestParam();

            request.setEntity(new StringEntity(GSON.toJson(jsonObject), ContentType.APPLICATION_JSON));
            CloseableHttpResponse execute = closeableHttpClient.execute(request, httpClientContext);
            HttpPost monitor = new HttpPost("https://mall.bilibili.com/mall-dayu/h5/monitor");
            monitor.setEntity(new StringEntity("{\"event\":\"lens.magic.network\",\"time\":1711699334035,\"mid\":\"\",\"platform\":1,\"buvid\":\"3731A7EF-543B-412A-894D-7086543839D827566infoc\",\"appV\":\"0\",\"ua\":\"Mozilla%2F5.0%20(iPhone%3B%20CPU%20iPhone%20OS%2016_6%20like%20Mac%20OS%20X)%20AppleWebKit%2F605.1.15%20(KHTML%2C%20like%20Gecko)%20Version%2F16.6%20Mobile%2F15E148%20Safari%2F604.1%20Edg%2F123.0.0.0\",\"isbili\":0,\"url\":\"mall.bilibili.com%2Fneul-next%2Findex.html%3Fpage%3Dmagic-market_index\",\"referer\":\"-\",\"bid\":\"MagicAwards\",\"lensV\":\"0.2.8\",\"ext\":{\"category\":\"service\",\"from\":\"\",\"msource\":\"\",\"magicNetwork\":{\"method\":\"post\",\"url\":\"//mall.bilibili.com/mall-magic-c/internet/c2c/v2/list\",\"payload\":\"{\\\"priceFilters\\\":[\\\"5000-10000\\\"],\\\"categoryFilter\\\":\\\"2312\\\",\\\"nextId\\\":null}\",\"duration\":467.59999999403954,\"traceid_svr\":\"\",\"response\":\"%7B%22data%22%3A%5B%7B%22c2cItemsId%22%3A34559558472%2C%22type%22%3A1%2C%22c2cItemsName%22%3A%22FuRyu%20%E9%9B%B7%E5%A7%86%20%E8%8A%B1%E4%BB%99%E5%AD%90%20%E6%99%AF%E5%93%81%E6%89%8B%E5%8A%9E%22%2C%22detailDtoList%22%3A%5B%7B%22blindBoxId%22%3A174999890%2C%22itemsId%22%3A10234180%2C%22skuId%22%3A1000522724%2C%22name%22%3A%22FuRyu%20%E9%9B%B7%E5%A7%86%20%E8%8A%B1%E4%BB%99%E5%AD%90%20%E6%99%AF%E5%93%81%E6%89%8B%E5%8A%9E%22%2C%22img%22%3A%22%2F%2Fi0.hdslb.com%2Fbfs%2Fmall%2Fmall%2F81%2F29%2F81292f4d5844945c64d44293fb502782.png%22%2C%22marketPrice%22%3A11500%2C%22type%22%3A1%2C%22isHidden%22%3Afalse%7D%5D%2C%22totalItemsCount%22%3A1%2C%22price%22%3A6500%2C%22showPrice%22%3A%2265%22%2C%22showMarketPrice%22%3A%22115%22%2C%22uid%22%3A%2235***9\",\"vitalDataError\":false,\"vitalDataErrorInfo\":\"\"},\"stage\":\"WINDOW_LOADED\"}}", ContentType.APPLICATION_JSON));
            System.out.println(EntityUtils.toString(closeableHttpClient.execute(monitor, httpClientContext).getEntity()));
            Root root = GSON.fromJson(EntityUtils.toString(execute.getEntity()), Root.class);
            BLOCKING_QUEUE.add(root);

            String sql = "INSERT INTO PUBLIC.GENERIC(ID, C2CITEMSID, TYPE, C2CITEMSNAME, TOTALITEMSCOUNT, PRICE, SHOWPRICE, SHOWMARKETPRICE, UID, PAYMENTTIME, ISMYPUBLISH, UNAME, USPACEJUMPURL, UFACE, NEXTID) " + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            String subsql = "INSERT INTO PUBLIC.GENERIC_DETAILDTOLIST (GENERIC_ID, ID, BLINDBOXID, ITEMSID, SKUID, NAME, IMG, MARKETPRICE, TYPE, ISHIDDEN) VALUES " + "(?,?,?,?,?,?,?,?,?,?)";
            Connection connection = null;
            PreparedStatement mainPrep = null;
            PreparedStatement subPrep = null;
            try {
                //            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/" + DB_NAME + "", "SA", "");
                connection = DriverManager.getConnection(jdbcUrl, "SA", "");


                mainPrep = connection.prepareStatement(sql);
                subPrep = connection.prepareStatement(subsql);
                connection.setAutoCommit(false);


                Connection finalConnection = connection;
                PreparedStatement finalMainPrep = mainPrep;
                PreparedStatement finalSubPrep = subPrep;
                service.submit(() -> {
                    try {
                        insert(BLOCKING_QUEUE, finalConnection, finalMainPrep, finalSubPrep);
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });


                String nextId = root.getData().getNextId();
                int i = 1;
                do {
                    final double v = 500 * Math.sin(System.currentTimeMillis()) + 5000;
                    if (i % 500 == 0) {
                        TimeUnit.SECONDS.sleep(30);
                        System.out.println("睡眠30s");
                    } else {
                        System.out.println("暂停" + v + "s");

                        TimeUnit.MILLISECONDS.sleep((long) v);
                    }
                    JsonObject sec = buildRequestParam(nextId);

                    //                sec.put("discountFilters", Lists.newArrayList(
                    //                        "30-50",
                    //                        "50-70",
                    //                        "0-30"
                    //                ));
                    //                sec.put("priceFilters", Lists.newArrayList(
                    //                        "2000-3000",
                    //                        "3000-5000",
                    //                        "5000-10000"
                    //                        "20000-0",
                    //                        "10000-20000"
                    //                ));
                    request.setEntity(new StringEntity(GSON.toJson(sec), ContentType.APPLICATION_JSON));
                    Root secondRoot = null;
                    try {
                        CloseableHttpClient newClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultCookieStore(store).build();

                        secondRoot = GSON.fromJson(EntityUtils.toString(newClient.execute(request, httpClientContext).getEntity()), Root.class);
                    } catch (Exception e) {
                        System.out.println("触发接口限制:" + EntityUtils.toString(closeableHttpClient.execute(request, httpClientContext).getEntity(), StandardCharsets.UTF_8));
                        e.printStackTrace();
                        System.out.println("休眠30s");
                        TimeUnit.MINUTES.sleep(5);
                    }
                    if (secondRoot != null) {
                        final Data rootData = secondRoot.getData();
                        if (rootData != null) {
                            List<Daum> data = rootData.getData();
                            if (data != null) {
//                                System.out.println("请求第" + (++i) + "次查询到：");
                                BLOCKING_QUEUE.put(secondRoot);
                                nextId = secondRoot.getData().getNextId();
                            }
                        }
                    }
                } while (nextId != null);
                System.out.println("等待关闭链接");
                LATCH.await(10, TimeUnit.SECONDS);

                System.out.println("总共本次拉取数据：" + allsuccess + "条");

                LatestSync entity = new LatestSync();
                entity.setSyncNum(allsuccess);

                entity.setLatestSync(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now));
                entity.setSyncElapsedTime(stopWatch.elapsedTime() + "ms");
                syncDao.save(entity);
                isSyncing.compareAndSet(true, false);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                subPrep.close();
                mainPrep.close();
                connection.close();
            }
        }


    }


    private static JsonObject buildFirstRequestParam() {
        JsonObject jsonObje = new JsonObject();
        jsonObje.addProperty("nextId", (String) null);

//        jsonObject.put("sortType", "TIME_DESC");
//        jsonObject.put("discountFilters", Lists.newArrayList("30-50"));
//        jsonObject.put("priceFilters", Lists.newArrayList("5000-10000"));
        return jsonObje;
    }

    private static JsonObject buildRequestParam(String nextId) {
        JsonObject jsonObje = new JsonObject();
        jsonObje.addProperty("nextId", nextId);
//        sec.put("sortType", "TIME_DESC");
//        sec.put("priceFilters", Lists.newArrayList("5000-10000"));
        return jsonObje;
    }


    private static void insert(BlockingQueue<Root> roots, Connection connection, PreparedStatement mainPrep, PreparedStatement subPrep) throws InterruptedException, SQLException {
        int groupNumber = 10;
        int groupIndex = 0;
        int i = 0;
        BitSet set = new BitSet();
        while (true) {
            {
                Root root = roots.take();
                String nextId = root.getData().getNextId();
                List<Daum> itemList = root.getData().getData();
                boolean isBatch = false;
//                System.out.println("本次NEXTID:" + nextId + "循环请求：" + (++i));
                for (Daum item : itemList) {
                    String c2cItemId = item.getC2cItemsId().toString();
                    ResultSet c2cItemIdresultSet = connection.createStatement().executeQuery("select 1  from GENERIC where C2CITEMSID='" + c2cItemId + "' ");
                    if (c2cItemIdresultSet.next()) {
                        continue;
                    }

                    String mainId = UUID.randomUUID().toString();
                    mainPrep.setString(1, mainId);
                    mainPrep.setString(2, c2cItemId);
                    mainPrep.setString(3, item.getType().toString());
                    mainPrep.setString(4, item.getC2cItemsName());
                    mainPrep.setString(5, item.getTotalItemsCount().toString());
                    mainPrep.setString(6, item.getPrice().toString());
                    mainPrep.setString(7, item.getShowPrice());
                    mainPrep.setString(8, item.getShowMarketPrice());
                    mainPrep.setString(9, item.getUid());
                    mainPrep.setString(10, item.getPaymentTime().toString());
                    mainPrep.setBoolean(11, item.getMyPublish());
                    mainPrep.setString(12, item.getUname());
                    mainPrep.setString(13, item.getUspaceJumpUrl());
                    mainPrep.setString(14, item.getUface());
                    mainPrep.setString(15, nextId);
                    for (DetailDtoList detailDtoList : item.getDetailDtoList()) {
                        subPrep.setString(1, mainId);
                        subPrep.setString(2, UUID.randomUUID().toString());
                        subPrep.setString(3, detailDtoList.getBlindBoxId().toString());
                        subPrep.setString(4, detailDtoList.getItemsId().toString());
                        subPrep.setString(5, detailDtoList.getSkuId().toString());
                        subPrep.setString(6, detailDtoList.getName());
                        subPrep.setString(7, detailDtoList.getImg());
                        subPrep.setString(8, detailDtoList.getMarketPrice().toString());
                        subPrep.setString(9, detailDtoList.getType().toString());
                        subPrep.setBoolean(10, detailDtoList.getHidden());
                        subPrep.addBatch();
                    }
                    isBatch = true;
                    mainPrep.addBatch();
                    set.set(i, false);


                }
                if (nextId == null) {
                    int[] executeBatch = mainPrep.executeBatch();
                    int sum = Arrays.stream(executeBatch).sum();
                    allsuccess = allsuccess + sum;
                    subPrep.executeBatch();
                    System.out.println("最后本批处理完成" + sum);
                    connection.commit();
                    System.out.println("终结拉取数据 单本次拉取数据：" + allsuccess + "条");
                    LATCH.countDown();
                }
                if (i % groupNumber == 0 && isBatch) {
                    int[] executeBatch = mainPrep.executeBatch();
                    int sum = Arrays.stream(executeBatch).sum();
                    allsuccess = allsuccess + sum;
                    ++groupIndex;
//                    System.out.println("第" + groupIndex + "组");
//                    System.out.println("本批处理完成" + sum);
                    subPrep.executeBatch();
                    connection.commit();
//                    System.out.println("累计拉取数据：" + allsuccess + "条");
                }

            }
        }
    }

    public static void main(String[] args) {
        String cookie = "SESSDATA=057af4d4%2C1727164306%2C3167f%2A31CjAptDQ1WcgxZdsqDDardMclIjwpt-0KBuLSONOyqFm4Ch81EhmhQ_Ufac0JoahI_OASVkJrc3J6Ymg5Vm5pM0JoWXZmX2M4Z0xJeHFhYWlIRzJ3NGthZnRBSEpnLUVwUS1USWwyc09YczkzcUpZbjhqVl9zeXJ5dmUyUllZd09NMlcyUGVTdVd3IIEC; _uuid=5D426A8A-4577-66F3-9452-34E2D1BC41F538761infoc; buvid3=98EA12CB-9251-4FE2-99A2-E1A2DD28B30027548infoc; rpdid=|(u))~|RR~kY0J'u~uumYklll; _xid=DR%5CM69ArlWOcqqkIZgEdmcXOEhnIuhzehu1oXV9Km1N7A9aYrjw5ogp8TMAJOO885i4bWf3AkKd9XXhCoIgRVbgiy479mFfLu3v2n7BgZsNJMAyJqdjpnWLJtZoHN6x5; 9AD585D8A7CB034A=yizDkS3h-1712045043363-8ddabe6a20a191157320539; 1735D64331DF397E=1RmeF%5CzGpVhbTTmIMRi9hkXsn10ioZwFweYpRDmX2xZmkIKZdjDO3eTAaoTH2QjrU8434BdPGJeofSQ5giFyLSJynsYrLEy7k2TYWktXK7YkK2rA4gegNiEwlClEqhrA; b_nut=100; CURRENT_BLACKGAP=0; buvid4=2A102AE4-067E-2930-9307-EDD23753970608863-023082109-tzUqlq9E3xqp7ZyZuTLbtw%3D%3D; fingerprint=e2885d18ef7e4a5dfbfb38f84fa07565; buvid_fp_plain=undefined; CURRENT_QUALITY=80; bp_t_offset_283872216=930906330024116324; buvid_fp=e2885d18ef7e4a5dfbfb38f84fa07565; Hm_lvt_8d8d2f308d6e6dffaf586bd024670861=1715237043,1715333339,1715583221,1715754827; LIVE_BUVID=AUTO7117163593944770; PVID=4; CURRENT_FNVAL=4048";
        StringTokenizer tokenizer = new StringTokenizer(cookie);
        while (tokenizer.hasMoreTokens()) {
            String trim = tokenizer.nextToken(";").trim();
            System.out.println(trim);
            StringTokenizer eq = new StringTokenizer(trim);
            while (eq.hasMoreTokens()) {
                System.out.println(eq.nextToken("=").trim());
            }
        }
    }

}
