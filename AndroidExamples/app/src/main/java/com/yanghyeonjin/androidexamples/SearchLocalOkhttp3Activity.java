package com.yanghyeonjin.androidexamples;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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

    private String kakaoLocalAppKey, naverClientID, naverClientSecret;

    private EditText etKakaoSearchLocal, etNaverSearchLocal;
    private Button btnKakaoSearchLocal, btnNaverSearchLocal;
    private TextView tvKakaoResultName, tvKakaoResultAddress, tvKakaoResultRoadAddress, tvKakaoResultTel;
    private TextView tvNaverResultName, tvNaverResultAddress, tvNaverResultRoadAddress, tvNaverResultTel;

    private OkHttpClient kakaoHttpClient, naverHttpClient;

    private KakaoLocalKeywordResult kakaoResult;
    private NaverSearchLocalResult naverResult;

    private Handler kakaoHandler, naverHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_local);

        kakaoLocalAppKey = getString(R.string.kakao_local_app_key);
        naverClientID = getString(R.string.naver_client_id);
        naverClientSecret = getString(R.string.naver_client_secret);


        /* 아이디 연결 */
        etKakaoSearchLocal = findViewById(R.id.et_kakao_search_local);
        etNaverSearchLocal = findViewById(R.id.et_naver_search_local);

        btnKakaoSearchLocal = findViewById(R.id.btn_kakao_search_local);
        btnNaverSearchLocal = findViewById(R.id.btn_naver_search_local);

        tvKakaoResultName = findViewById(R.id.tv_kakao_result_name);
        tvKakaoResultAddress = findViewById(R.id.tv_kakao_result_address);
        tvKakaoResultRoadAddress = findViewById(R.id.tv_kakao_result_road_address);
        tvKakaoResultTel = findViewById(R.id.tv_kakao_result_tel);

        tvNaverResultName = findViewById(R.id.tv_naver_result_name);
        tvNaverResultAddress = findViewById(R.id.tv_naver_result_address);
        tvNaverResultRoadAddress = findViewById(R.id.tv_naver_result_road_address);
        tvNaverResultTel = findViewById(R.id.tv_naver_result_tel);


        /* 검색어 없을 때 나타나는 AlertDialog 초기화 */
        AlertDialog.Builder builder = initNoKeyword();



        /* okhttp3 초기화 */
        kakaoHttpClient = new OkHttpClient();
        naverHttpClient = new OkHttpClient();


        /* 핸들러 초기화 */
        kakaoHandler = new Handler(Looper.getMainLooper());
        naverHandler = new Handler(Looper.getMainLooper());



        /* 카카오 검색 눌렀을 때 */
        btnKakaoSearchLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kakaoKeyword = etKakaoSearchLocal.getText().toString();

                if (kakaoKeyword.isEmpty()) {
                    /* 검색창 빈칸이면 */
                    builder.show();
                } else {
                    /* 빈칸 아니면 검색 시작 */
                    searchKakaoLocal(kakaoKeyword);
                }
            }
        });

        /* 네이버 검색 눌렀을 때 */
        btnNaverSearchLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naverKeyword = etNaverSearchLocal.getText().toString();

                if (naverKeyword.isEmpty()) {
                    /* 검색창 빈칸이면 */
                    builder.show();
                } else {
                    /* 빈칸 아니면 검색 시작 */
                    searchNaverLocal(naverKeyword);
                }
            }
        });
    }

    private AlertDialog.Builder initNoKeyword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("검색어 없음");
        builder.setMessage("검색어를 입력해주세요.");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return builder;
    }

    /* 카카오 검색 */
    private void searchKakaoLocal(String keyword) {
        HttpUrl.Builder kakaoUrlBuilder = HttpUrl.parse("https://dapi.kakao.com/v2/local/search/keyword.json").newBuilder();
        kakaoUrlBuilder.addEncodedQueryParameter("query", keyword);
        String kakaoRequestUrl = kakaoUrlBuilder.build().toString();

        Request kakaoRequest = new Request.Builder()
                .addHeader("Authorization", kakaoLocalAppKey)
                .url(kakaoRequestUrl)
                .build();

        kakaoHttpClient.newCall(kakaoRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("kakao search", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String result = response.body().string();

                    Gson gson = new Gson();
                    kakaoResult = gson.fromJson(result, KakaoLocalKeywordResult.class);

                    kakaoHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            String placeName = kakaoResult.getDocuments().get(0).getPlaceName();
                            String address = kakaoResult.getDocuments().get(0).getAddressName();
                            String roadAddress = kakaoResult.getDocuments().get(0).getRoadAddressName();
                            String tel = kakaoResult.getDocuments().get(0).getPhone();

                            tvKakaoResultName.setText(placeName);
                            tvKakaoResultAddress.setText(address);
                            tvKakaoResultRoadAddress.setText(roadAddress);
                            tvKakaoResultTel.setText(tel);

                        }
                    });

                } else {
                    Log.e("kakao search", "Response Body is null");
                }
            }
        });
    }

    /* 네이버 검색 */
    private void searchNaverLocal(String keyword) {
        HttpUrl.Builder naverUrlBuilder = HttpUrl.parse("https://openapi.naver.com/v1/search/local.json").newBuilder();
        naverUrlBuilder.addEncodedQueryParameter("query", keyword);
        String naverRequestUrl = naverUrlBuilder.build().toString();

        Request naverRequest = new Request.Builder()
                .addHeader("X-Naver-Client-Id", naverClientID)
                .addHeader("X-Naver-Client-Secret", naverClientSecret)
                .url(naverRequestUrl)
                .build();

        kakaoHttpClient.newCall(naverRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("naver search", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String result = response.body().string();

                    Gson gson = new Gson();
                    naverResult = gson.fromJson(result, NaverSearchLocalResult.class);

                    naverHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            String placeName = naverResult.getItems().get(0).getTitle();
                            String address = naverResult.getItems().get(0).getAddress();
                            String roadAddress = naverResult.getItems().get(0).getRoadAddress();
                            String tel = naverResult.getItems().get(0).getTelephone();

                            tvNaverResultName.setText(placeName);
                            tvNaverResultAddress.setText(address);
                            tvNaverResultRoadAddress.setText(roadAddress);
                            tvNaverResultTel.setText(tel);
                        }
                    });

                } else {
                    Log.e("naver search", "Response Body is null");
                }
            }
        });
    }
}
