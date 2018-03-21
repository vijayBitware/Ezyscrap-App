
package com.ezyscrap.Model.PaymentHistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ezyscrap.webservice.BaseResponse;

public class PaymentHistory extends BaseResponse{

    @SerializedName("payment_data")
    @Expose
    private List<PaymentDatum> paymentData = null;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("wallet_balance")
    @Expose
    private String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<PaymentDatum> getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(List<PaymentDatum> paymentData) {
        this.paymentData = paymentData;
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
