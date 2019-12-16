package com.yanghyeonjin.androidexamples.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KakaoLocalKeywordSameName {

    @SerializedName("region")
    @Expose
    private List<String> regionList;

    @SerializedName("keyword")
    @Expose
    private String keyword;

    @SerializedName("selected_region")
    @Expose
    private String selectedRegion;

    public List<String> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<String> regionList) {
        this.regionList = regionList;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(String selectedRegion) {
        this.selectedRegion = selectedRegion;
    }
}
