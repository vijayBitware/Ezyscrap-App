
package com.ezyscrap.Model.Login.SellerHomeResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ezyscrap.webservice.BaseResponse;

public class SellerHomeResponse extends BaseResponse {

    @SerializedName("scrap_type")
    @Expose
    private List<ScrapType> scrapType = null;
    @SerializedName("sold_scrap")
    @Expose
    private List<SoldScrap> soldScrap = null;
    @SerializedName("live_scrap")
    @Expose
    private List<LiveScrap> liveScrap = null;

    @SerializedName("code")
    @Expose
    private String code ;

    @SerializedName("status")
    @Expose
    private String status ;

    @SerializedName("description")
    @Expose
    private String description ;

    public List<ScrapType> getScrapType() {
        return scrapType;
    }

    public void setScrapType(List<ScrapType> scrapType) {
        this.scrapType = scrapType;
    }

    public List<SoldScrap> getSoldScrap() {
        return soldScrap;
    }

    public void setSoldScrap(List<SoldScrap> soldScrap) {
        this.soldScrap = soldScrap;
    }

    public List<LiveScrap> getLiveScrap() {
        return liveScrap;
    }

    public void setLiveScrap(List<LiveScrap> liveScrap) {
        this.liveScrap = liveScrap;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
