
package com.ezyscrap.Model.Login.Notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotifDatum {

    @SerializedName("notif_id")
    @Expose
    private String notifId;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("date")
    @Expose
    private String date;

    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
