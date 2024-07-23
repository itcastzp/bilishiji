package com.example.demo.bilibili;

import javax.persistence.*;
import java.util.List;

@Table(name = "GENERIC")
@Entity
public class Daum {
    @Id
    private String id;
    private Long c2cItemsId;
    private Long type;
    private String c2cItemsName;
    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "GENERIC_ID") // join column is in table for DetailDtoList
    private List<DetailDtoList> detailDtoList;
    private Long totalItemsCount;
    private Long price;
    private String showPrice;
    private String showMarketPrice;
    private String uid;
    private Long paymentTime;
    private Boolean isMyPublish;
    private String uname;
    private String uspaceJumpUrl;
    private String uface;
    private String creationTime;
    private String nextId;

    @Override
    public String toString() {
        return "Daum{" +
                "id='" + id + '\'' +
                ", c2cItemsId=" + c2cItemsId +
                ", type=" + type +
                ", c2cItemsName='" + c2cItemsName + '\'' +
                ", detailDtoList=" + detailDtoList +
                ", totalItemsCount=" + totalItemsCount +
                ", price=" + price +
                ", showPrice='" + showPrice + '\'' +
                ", showMarketPrice='" + showMarketPrice + '\'' +
                ", uid='" + uid + '\'' +
                ", paymentTime=" + paymentTime +
                ", isMyPublish=" + isMyPublish +
                ", uname='" + uname + '\'' +
                ", uspaceJumpUrl='" + uspaceJumpUrl + '\'' +
                ", uface='" + uface + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", nextId='" + nextId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getC2cItemsId() {
        return c2cItemsId;
    }

    public void setC2cItemsId(Long c2cItemsId) {
        this.c2cItemsId = c2cItemsId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getC2cItemsName() {
        return c2cItemsName;
    }

    public void setC2cItemsName(String c2cItemsName) {
        this.c2cItemsName = c2cItemsName;
    }

    public List<DetailDtoList> getDetailDtoList() {
        return detailDtoList;
    }

    public void setDetailDtoList(List<DetailDtoList> detailDtoList) {
        this.detailDtoList = detailDtoList;
    }

    public Long getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setTotalItemsCount(Long totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(String showPrice) {
        this.showPrice = showPrice;
    }

    public String getShowMarketPrice() {
        return showMarketPrice;
    }

    public void setShowMarketPrice(String showMarketPrice) {
        this.showMarketPrice = showMarketPrice;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Long paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Boolean getMyPublish() {
        return isMyPublish;
    }

    public void setMyPublish(Boolean myPublish) {
        isMyPublish = myPublish;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUspaceJumpUrl() {
        return uspaceJumpUrl;
    }

    public void setUspaceJumpUrl(String uspaceJumpUrl) {
        this.uspaceJumpUrl = uspaceJumpUrl;
    }

    public String getUface() {
        return uface;
    }

    public void setUface(String uface) {
        this.uface = uface;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }
}


