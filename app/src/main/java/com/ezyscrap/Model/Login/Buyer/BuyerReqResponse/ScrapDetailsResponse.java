
package com.ezyscrap.Model.Login.Buyer.BuyerReqResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ezyscrap.webservice.BaseResponse;

public class ScrapDetailsResponse extends BaseResponse{

    @SerializedName("scrap_data")
    @Expose
    private ScrapData scrapData;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;

    public ScrapData getScrapData() {
        return scrapData;
    }

    public void setScrapData(ScrapData scrapData) {
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
