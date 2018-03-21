package com.ezyscrap.Model.Login;

/**
 * Created by Admin on 12/20/2017.
 */

public class BuyerReqModel {
    String registrationFee,orderNo, scrapType, unit, scrapId, scrapDesc, apprAmnt, flag, scrapImg1,scrapImg2,scrapImg3,scrapImg4, bidFlag, gst, bidAmnt;
    boolean interestflag;
    int soldFlag;

    public String getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(String registrationFee) {
        this.registrationFee = registrationFee;
    }

    public String getBidAmnt() {
        return bidAmnt;
    }

    public void setBidAmnt(String bidAmnt) {
        this.bidAmnt = bidAmnt;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getBidFlag() {
        return bidFlag;
    }

    public void setBidFlag(String bidFlag) {
        this.bidFlag = bidFlag;
    }

    public String getScrapImg1() {
        return scrapImg1;
    }

    public void setScrapImg1(String scrapImg1) {
        this.scrapImg1 = scrapImg1;
    }

    public String getScrapImg2() {
        return scrapImg2;
    }

    public void setScrapImg2(String scrapImg2) {
        this.scrapImg2 = scrapImg2;
    }

    public String getScrapImg3() {
        return scrapImg3;
    }

    public void setScrapImg3(String scrapImg3) {
        this.scrapImg3 = scrapImg3;
    }

    public String getScrapImg4() {
        return scrapImg4;
    }

    public void setScrapImg4(String scrapImg4) {
        this.scrapImg4 = scrapImg4;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getScrapDesc() {
        return scrapDesc;
    }

    public void setScrapDesc(String scrapDesc) {
        this.scrapDesc = scrapDesc;
    }

    public String getApprAmnt() {
        return apprAmnt;
    }

    public void setApprAmnt(String apprAmnt) {
        this.apprAmnt = apprAmnt;
    }

    public String getScrapId() {
        return scrapId;
    }

    public void setScrapId(String scrapId) {
        this.scrapId = scrapId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getScrapType() {
        return scrapType;
    }

    public void setScrapType(String scrapType) {
        this.scrapType = scrapType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isInterestflag() {return interestflag;}

    public void setInterestflag(boolean interestflag)
    {
        this.interestflag = interestflag;}

    public int getSoldFlag() {
        return soldFlag;
    }

    public void setSoldFlag(int soldFlag) {
        this.soldFlag = soldFlag;
    }
}
