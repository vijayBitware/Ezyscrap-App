package com.ezyscrap.Model.Login.Forgot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ezyscrap.webservice.BaseResponse;

/**
 * Created by bitware on 21/12/17.
 */

public class ForgotPassresponce extends BaseResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("new_password")
    @Expose
    private String new_password;

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

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
