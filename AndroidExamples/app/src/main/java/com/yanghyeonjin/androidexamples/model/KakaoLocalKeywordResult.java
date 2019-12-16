package com.yanghyeonjin.androidexamples.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KakaoLocalKeywordResult {
    @SerializedName("meta")
    @Expose
    private KakaoLocalKeywordMeta meta;

    @SerializedName("documents")
    @Expose
    private List<KakaoLocalKeywordDocument> documents;


    public KakaoLocalKeywordMeta getMeta() {
        return meta;
    }

    public void setMeta(KakaoLocalKeywordMeta meta) {
        this.meta = meta;
    }

    public List<KakaoLocalKeywordDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<KakaoLocalKeywordDocument> documents) {
        this.documents = documents;
    }
}
