
package com.ezyscrap.Model.OrderHistory;

import java.util.List;

import com.ezyscrap.webservice.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistory extends BaseResponse{

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
