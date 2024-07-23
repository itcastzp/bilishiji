package com.example.demo.bilibili;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GENERIC_DETAILDTOLIST")
public class DetailDtoList {

    @Id
    private String id;


    @Column(name = "GENERIC_ID")
    private String datumId;
    private Long blindBoxId;
    private Long itemsId;
    private Long skuId;
    private String name;
    private String img;
    private Long marketPrice;
    private Long type;
    private Boolean isHidden;

    private String creationTime;

    @Override
    public String toString() {
        return "DetailDtoList{" +
                "id='" + id + '\'' +
                ", datumId='" + datumId + '\'' +
                ", blindBoxId=" + blindBoxId +
                ", itemsId=" + itemsId +
                ", skuId=" + skuId +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", marketPrice=" + marketPrice +
                ", type=" + type +
                ", isHidden=" + isHidden +
                ", creationTime='" + creationTime + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatumId() {
        return datumId;
    }

    public void setDatumId(String datumId) {
        this.datumId = datumId;
    }

    public Long getBlindBoxId() {
        return blindBoxId;
    }

    public void setBlindBoxId(Long blindBoxId) {
        this.blindBoxId = blindBoxId;
    }

    public Long getItemsId() {
        return itemsId;
    }

    public void setItemsId(Long itemsId) {
        this.itemsId = itemsId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}