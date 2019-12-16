package com.yanghyeonjin.androidexamples;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yanghyeonjin.androidexamples.model.KakaoLocalKeywordResult;
import com.yanghyeonjin.androidexamples.model.NaverSearchLocalResult;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchLocalOkhttp3Activity extends AppCompatActivity {

    private String kakaoLocalAppKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_local);

        kakaoLocalAppKey = getString(R.string.kakao_local_app_key);



        /* 카카오 */
        OkHttpClient kakaoHttpClient = new OkHttpClient();

        HttpUrl.Builder kakaoUrlBuilder = HttpUrl.parse("https://dapi.kakao.com/v2/local/search/keyword.json").newBuilder();
        kakaoUrlBuilder.addEncodedQueryParameter("query", "파스타");
        String kakaoRequestUrl = kakaoUrlBuilder.build().toString();

        Request kakaoRequest = new Request.Builder()
                .addHeader("Authorization", kakaoLocalAppKey)
                .url(kakaoRequestUrl)
                .build();

        kakaoHttpClient.newCall(kakaoRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("kakao: onFailure", "error + Connect Server Error is " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    /* response.body().string()은 한번만 호출하자. */
                    String result = response.body().string();

                    Gson gson = new Gson();
                    KakaoLocalKeywordResult kakaoLocalKeywordResult = gson.fromJson(result, KakaoLocalKeywordResult.class);

                    Log.e("placeName", kakaoLocalKeywordResult.getDocuments().get(0).getPlaceName());
                    Log.e("address", kakaoLocalKeywordResult.getDocuments().get(0).getAddressName());
                    Log.e("roadAddress", kakaoLocalKeywordResult.getDocuments().get(0).getRoadAddressName());
                } else {
                    Log.e("kakao: onResponse", "Response Body is null");
                }
            }
        });


        /* 네이버 */
        final String naverClientID = getString(R.string.naver_client_id);
        final String naverClientSecret = getString(R.string.naver_client_secret);

        OkHttpClient naverHttpClient = new OkHttpClient();

        HttpUrl.Builder naverUrlBuilder = HttpUrl.parse("https://openapi.naver.com/v1/search/local.json").newBuilder();
        naverUrlBuilder.addEncodedQueryParameter("query", "파스타");
        String naverRequestUrl = naverUrlBuilder.build().toString();

        Request request = new Request.Builder()
                .addHeader("X-Naver-Client-Id", naverClientID)
                .addHeader("X-Naver-Client-Secret", naverClientSecret)
                .url(naverRequestUrl)
                .build();

        naverHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Naver: onFailure", "error + Connect Server Error is " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    /* response.body().string()은 한번만 호출하자. */
                    String result = response.body().string();

                    Gson gson = new Gson();
                    NaverSearchLocalResult naverSearchLocalResult = gson.fromJson(result, NaverSearchLocalResult.class);

                    Log.e("title", naverSearchLocalResult.getItems().get(0).getTitle());
                    Log.e("address", naverSearchLocalResult.getItems().get(0).getAddress());
                    Log.e("roadAddress", naverSearchLocalResult.getItems().get(0).getRoadAddress());
                } else {
                    Log.e("Naver: onResponse", "Response Body is null");
                }

            }
        });
    }
}
