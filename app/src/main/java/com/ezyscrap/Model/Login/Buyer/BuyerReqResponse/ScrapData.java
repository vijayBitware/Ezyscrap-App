
package com.ezyscrap.Model.Login.Buyer.BuyerReqResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScrapData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("image1")
    @Expose
    private String image1;
    @SerializedName("image2")
    @Expose
    private String image2;
    @SerializedName("image3")
    @Expose
    private String image3;
    @SerializedName("image4")
    @Expose
    private String image4;
    @SerializedName("min_amt")
    @Expose
    private String minAmt;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("bid")
    @Expose
    private String bid;
    @SerializedName("sold_flag")
    @Expose
    private Integer soldFlag;
    @SerializedName("bid_amt")
    @Expose
    private Integer bidAmt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getMinAmt() {
        return minAmt;
    }

    public void setMinAmt(String minAmt) {
        this.minAmt = minAmt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public Integer getSoldFlag() {
        return soldFlag;
    }

    public void setSoldFlag(Integer soldFlag) {
        this.soldFlag = soldFlag;
    }

    public Integer getBidAmt() {
        return bidAmt;
    }

    public void setBidAmt(Integer bidAmt) {
        this.bidAmt = bidAmt;
    }

}
