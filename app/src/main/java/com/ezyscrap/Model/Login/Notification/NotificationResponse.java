
package com.ezyscrap.Model.Login.Notification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ezyscrap.webservice.BaseResponse;

public class NotificationResponse extends BaseResponse {

    @SerializedName("notif_data")
    @Expose
    private List<NotifDatum> notifData = null;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;

    public List<NotifDatum> getNotifData() {
        return notifData;
    }

    public void setNotifData(List<NotifDatum> notifData) {
        this.notifData = notifData;
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
