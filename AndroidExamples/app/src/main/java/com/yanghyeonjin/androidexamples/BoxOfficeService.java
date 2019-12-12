package com.yanghyeonjin.androidexamples;

import com.yanghyeonjin.androidexamples.model.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BoxOfficeService {
    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?")
    Call<MovieResult> getBoxOffice(@Query("key") String key, @Query("targetDt") String targetDt);
}
