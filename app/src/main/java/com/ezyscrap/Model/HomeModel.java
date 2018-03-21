package com.ezyscrap.Model;

/**
 * Created by marco.granatiero on 03/02/2015.
 */
public class HomeModel {
    public String orderNumber,scrapType,scrapQuantity,date, desc;
    public int titleResId;


    public HomeModel(String orderNumber, String scrapType, String scrapQuantity, String date, int titleResId, String desc) {
        this.orderNumber = orderNumber;
        this.scrapType = scrapType;
       this.scrapQuantity = scrapQuantity;
       this.date = date;
        this.titleResId=titleResId;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getScrapType() {
        return scrapType;
    }

    public void setScrapType(String scrapType) {
        this.scrapType = scrapType;
    }

    public String getScrapQuantity() {
        return scrapQuantity;
    }

    public void setScrapQuantity(String scrapQuantity) {
        this.scrapQuantity = scrapQuantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public void setTitleResId(int titleResId) {
        this.titleResId = titleResId;
    }
}
