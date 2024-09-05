package com.example.demo.bilibili;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GoodQueryData {
    private String c2cItemsId;
    private Integer type;
    private String c2cItemsName;
    private List<DetailDtoListBean> detailDtoList;
    private Integer totalItemsCount;
    private Integer price;
    private String showPrice;
    private String marketPrice;
    private Integer remainSecond;
    private String uid;
    private Integer publishStatus;
    private String dropReason;
    private Boolean isMyPublish;
    private Boolean isMyBuyer;
    private Integer saleStatus;
    private String buyerUid;
    private String buyerName;
    private String buyerFace;
    private Integer saleTime;
    private String buyerNotice;
    private Integer startBuyTime;
    private Integer publishTime;
    private String orderId;
    private String hiddenFudaiImg;
    private String uname;
    private String uspaceJumpUrl;
    private String uface;

    @Data
    @Accessors(chain = true)
    public static class DetailDtoListBean {
        private Integer blindBoxId;
        private Integer itemsId;
        private Integer skuId;
        private String name;
        private String img;
        private Integer marketPrice;
        private String showMarketPrice;
        private Boolean forbidExchange;
        private Boolean isDraw;
        private Integer style;
        private Integer boxItemsId;
        private Integer boxSkuId;
        private Integer type;
        private String predictArriveTime;
        private Boolean isHidden;
        private String cateName;
    }
}
