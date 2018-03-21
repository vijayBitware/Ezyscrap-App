
package com.ezyscrap.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ezyscrap.webservice.BaseResponse;

public class PaytmChecksumResponce extends BaseResponse{

    @SerializedName("check_sum_array")
    @Expose
    private CheckSumArray checkSumArray;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;

    public CheckSumArray getCheckSumArray() {
        return checkSumArray;
    }

    public void setCheckSumArray(CheckSumArray checkSumArray) {
        this.checkSumArray = checkSumArray;
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
