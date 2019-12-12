package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.yanghyeonjin.androidexamples.adapter.BoxOfficeAdapter;
import com.yanghyeonjin.androidexamples.model.BoxOfficeResult;
import com.yanghyeonjin.androidexamples.model.DailyBoxOfficeList;
import com.yanghyeonjin.androidexamples.model.MovieResult;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoxOfficeRetrofitActivity extends AppCompatActivity {

    final String BASE_URL = "http://www.kobis.or.kr";
    Retrofit retrofit;
    BoxOfficeService boxOfficeService;
    RecyclerView rvBoxOffice;

    List<DailyBoxOfficeList> dailyBoxOfficeLists;
    BoxOfficeAdapter boxOfficeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office_retrofit);

        dailyBoxOfficeLists = new ArrayList<>();

        /* 아이디 연결 */
        rvBoxOffice = findViewById(R.id.rv_box_office);


        /* 레트로핏 객체 생성 */
        /* addConverterFactory(GsonConverterFactory.create())는 JSON을 우리가 원하는 형태로 만들어주는 Gson 라이브러리와 Retrofit2에 연결하는 코드입니다. */
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        boxOfficeService = retrofit.create(BoxOfficeService.class);

        boxOfficeService.getBoxOffice(getString(R.string.box_office_api_key), "20190608").enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful()) {
                    Log.e("BoxOfficeRetrofit", 1 + "");
                    MovieResult movieResult = response.body();

                    if (movieResult != null) {
                        BoxOfficeResult boxOfficeResult = movieResult.getBoxOfficeResult();
                        List<DailyBoxOfficeList> dailyBoxOfficeLists2 = boxOfficeResult.getDailyBoxOfficeList();

                        dailyBoxOfficeLists.addAll(dailyBoxOfficeLists2);

                        boxOfficeAdapter = new BoxOfficeAdapter(dailyBoxOfficeLists, BoxOfficeRetrofitActivity.this);
                        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(BoxOfficeRetrofitActivity.this);
                        rvBoxOffice.setLayoutManager(linearLayoutManager);
                        rvBoxOffice.setAdapter(boxOfficeAdapter);
                    }
                } else {
                    Log.e("BoxOfficeRetrofit", 2 + "Error");
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }
}
