
package com.ezyscrap.Model.Login.Buyer.BuyerReqResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ezyscrap.webservice.BaseResponse;

public class BidConfirmationRes extends BaseResponse{

    @SerializedName("bid_id")
    @Expose
    private String bid_id;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;

    public String getBid_id() {
        return bid_id;
    }

    public void setBid_id(String bid_id) {
        this.bid_id = bid_id;
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
