package com.yanghyeonjin.androidexamples.model;

public class SearchResultItem {
    private String placeName;
    private String placeRoadAddress;
    private String placeCategory;

    public SearchResultItem() {}

    public SearchResultItem(String placeName, String placeRoadAddress, String placeCategory) {
        this.placeName = placeName;
        this.placeRoadAddress = placeRoadAddress;
        this.placeCategory = placeCategory;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceRoadAddress() {
        return placeRoadAddress;
    }

    public void setPlaceRoadAddress(String placeRoadAddress) {
        this.placeRoadAddress = placeRoadAddress;
    }

    public String getPlaceCategory() {
        return placeCategory;
    }

    public void setPlaceCategory(String placeCategory) {
        this.placeCategory = placeCategory;
    }
}
