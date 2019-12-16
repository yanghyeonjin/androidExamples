package com.yanghyeonjin.androidexamples.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KakaoLocalKeywordMeta {
    @SerializedName("total_count")
    @Expose
    private int totalCount;

    @SerializedName("pageable_count")
    @Expose
    private int pageableCount;

    @SerializedName("is_end")
    @Expose
    private boolean isEnd;

    @SerializedName("same_name")
    @Expose
    private KakaoLocalKeywordSameName sameName;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageableCount() {
        return pageableCount;
    }

    public void setPageableCount(int pageableCount) {
        this.pageableCount = pageableCount;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public KakaoLocalKeywordSameName getSameName() {
        return sameName;
    }

    public void setSameName(KakaoLocalKeywordSameName sameName) {
        this.sameName = sameName;
    }
}
