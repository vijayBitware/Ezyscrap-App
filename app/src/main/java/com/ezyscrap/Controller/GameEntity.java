package com.ezyscrap.Controller;

/**
 * Created by marco.granatiero on 03/02/2015.
 */
public class GameEntity {
   // public  imageResId;
    //public int titleResId;
    public String orderNo, scrapType, scrapUnit, scrapDate, scrapImage1,scrapImage2,scrapImage3,scrapImage4, scrapId, scrapDesc;

    public GameEntity(String orderNo, String scrapType, String scrapUnit, String scrapDate, String scrapImage1,String scrapImage2,String scrapImage3
            ,String scrapImage4, String scrapId,
    String scrapDesc) {
        this.scrapDesc = scrapDesc;
        this.scrapImage1 = scrapImage1;
        this.scrapImage2=scrapImage2;
        this.scrapImage3=scrapImage3;
        this.scrapImage4=scrapImage4;
        this.scrapId = scrapId;
        this.orderNo = orderNo;
        this.scrapType = scrapType;
        this.scrapUnit = scrapUnit;
        this.scrapDate = scrapDate;
    }
}
