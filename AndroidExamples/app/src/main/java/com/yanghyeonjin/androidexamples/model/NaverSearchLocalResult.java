package com.yanghyeonjin.androidexamples.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class NaverSearchLocalResult {
    @SerializedName("rss")
    @Expose
    private String rss;

    @SerializedName("channel")
    @Expose
    private String channel;

    @SerializedName("lastBuildDate")
    @Expose
    private String lastBuildDate;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("start")
    @Expose
    private int start;

    @SerializedName("display")
    @Expose
    private int display;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("items")
    @Expose
    private List<NaverSearchLocalItem> items;


    public String getRss() {
        return rss;
    }

    public void setRss(String rss) {
        this.rss = rss;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<NaverSearchLocalItem> getItems() {
        return items;
    }

    public void setItems(List<NaverSearchLocalItem> items) {
        this.items = items;
    }
}
