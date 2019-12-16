package com.yanghyeonjin.androidexamples;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class KakaoLocalOkhttp3Activity extends AppCompatActivity {

    private String kakaoLocalAppKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_local);

        kakaoLocalAppKey = getString(R.string.kakao_local_app_key);



        /* okhttp3 */
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://dapi.kakao.com/v2/local/search/keyword.json").newBuilder();
        urlBuilder.addEncodedQueryParameter("query", "파스타");
        String requestUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .addHeader("Authorization", kakaoLocalAppKey)
                .url(requestUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("KakaoLocal", "error + Connect Server Error is " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("KakaoLocal", "Response Body is " + response.body().string());
            }
        });
    }
}
