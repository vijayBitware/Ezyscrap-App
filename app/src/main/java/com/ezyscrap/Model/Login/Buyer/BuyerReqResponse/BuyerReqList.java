
package com.ezyscrap.Model.Login.Buyer.BuyerReqResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ezyscrap.webservice.BaseResponse;

public class BuyerReqList extends BaseResponse{

    @SerializedName("scrap_data")
    @Expose
    private List<ScrapDatum> scrapData = null;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("reg_fee")
    @Expose
    private String regFee;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getRegFee() {
        return regFee;
    }

    public void setRegFee(String regFee) {
        this.regFee = regFee;
    }

    public List<ScrapDatum> getScrapData() {
        return scrapData;
    }

    public void setScrapData(List<ScrapDatum> scrapData) {
        this.scrapData = scrapData;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
