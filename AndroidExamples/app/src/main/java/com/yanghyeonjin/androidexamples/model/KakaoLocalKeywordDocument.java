package com.yanghyeonjin.androidexamples.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KakaoLocalKeywordDocument {

    @SerializedName("id")
    @Expose
    private String placeID;

    @SerializedName("place_name")
    @Expose
    private String placeName;

    @SerializedName("category_name")
    @Expose
    private String categoryName;

    @SerializedName("category_group_code")
    @Expose
    private String categoryGroupCode;

    @SerializedName("category_group_name")
    @Expose
    private String categoryGroupName;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address_name")
    @Expose
    private String addressName;

    @SerializedName("road_address_name")
    @Expose
    private String roadAddressName;

    @SerializedName("x")
    @Expose
    private String placeX;

    @SerializedName("y")
    @Expose
    private String placeY;

    @SerializedName("place_url")
    @Expose
    private String placeURL;

    @SerializedName("distance")
    @Expose
    private String distance;


    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryGroupCode() {
        return categoryGroupCode;
    }

    public void setCategoryGroupCode(String categoryGroupCode) {
        this.categoryGroupCode = categoryGroupCode;
    }

    public String getCategoryGroupName() {
        return categoryGroupName;
    }

    public void setCategoryGroupName(String categoryGroupName) {
        this.categoryGroupName = categoryGroupName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getRoadAddressName() {
        return roadAddressName;
    }

    public void setRoadAddressName(String roadAddressName) {
        this.roadAddressName = roadAddressName;
    }

    public String getPlaceX() {
        return placeX;
    }

    public void setPlaceX(String placeX) {
        this.placeX = placeX;
    }

    public String getPlaceY() {
        return placeY;
    }

    public void setPlaceY(String placeY) {
        this.placeY = placeY;
    }

    public String getPlaceURL() {
        return placeURL;
    }

    public void setPlaceURL(String placeURL) {
        this.placeURL = placeURL;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
