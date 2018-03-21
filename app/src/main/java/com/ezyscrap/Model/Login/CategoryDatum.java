
package com.ezyscrap.Model.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ezyscrap.webservice.BaseResponse;

public class CategoryDatum extends BaseResponse{

    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("categoryMainImage")
    @Expose
    private String categoryMainImage;
    @SerializedName("categorySmallImage")
    @Expose
    private String categorySmallImage;

    private int sampleImage;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryMainImage() {
        return categoryMainImage;
    }

    public void setCategoryMainImage(String categoryMainImage) {
        this.categoryMainImage = categoryMainImage;
    }

    public String getCategorySmallImage() {
        return categorySmallImage;
    }

    public void setCategorySmallImage(String categorySmallImage) {
        this.categorySmallImage = categorySmallImage;
    }

    public int getSampleImage() {
        return sampleImage;
    }

    public void setSampleImage(int sampleImage) {
        this.sampleImage = sampleImage;
    }
}
