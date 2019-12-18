package com.yanghyeonjin.androidexamples.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yanghyeonjin.androidexamples.R;
import com.yanghyeonjin.androidexamples.adapter.SearchResultAdapter;
import com.yanghyeonjin.androidexamples.model.KakaoLocalKeywordDocument;
import com.yanghyeonjin.androidexamples.model.KakaoLocalKeywordResult;
import com.yanghyeonjin.androidexamples.model.SearchResultItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class KakaoSearchFragment extends Fragment {

    private View view;

    private RecyclerView rvKakaoResult;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SearchResultItem> searchResultItems;
    private SearchResultAdapter searchResultAdapter;

    private Button btnKakaoSearch;
    private EditText etKakaoSearch;

    private String kakaoLocalAppKey;
    private OkHttpClient kakaoHttpClient;
    private Handler kakaoHandler;
    private KakaoLocalKeywordResult kakaoResult;


    public static KakaoSearchFragment newInstance() {
        KakaoSearchFragment kakaoSearchFragment = new KakaoSearchFragment();
        return kakaoSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kakao_search, container, false);

        /* 카카오 REST API APP KEY */
        kakaoLocalAppKey = getString(R.string.kakao_local_app_key);



        /* 아이디 연결 */
        rvKakaoResult = view.findViewById(R.id.rv_kakao_search_result);
        btnKakaoSearch = view.findViewById(R.id.btn_kakao_search_local);
        etKakaoSearch = view.findViewById(R.id.et_kakao_search_local);



        /* 리사이클러뷰 셋팅 */
        rvKakaoResult.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvKakaoResult.setLayoutManager(layoutManager);

        searchResultItems = new ArrayList<>();

        searchResultAdapter = new SearchResultAdapter(searchResultItems, getContext());
        rvKakaoResult.setAdapter(searchResultAdapter);



        /* okhttp 초기화 */
        kakaoHttpClient = new OkHttpClient();

        /* 핸들러 초기화 */
        kakaoHandler = new Handler(Looper.getMainLooper());




        /* 검색어 없을 때 나타나는 AlertDialog 초기화 */
        AlertDialog.Builder builder = initNoKeyword();



        /* 카카오 검색 눌렀을 때 */
        btnKakaoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kakaoKeyword = etKakaoSearch.getText().toString();

                if (kakaoKeyword.isEmpty()) {
                    /* 검색창 빈칸이면 */
                    builder.show();
                } else {
                    /* 빈칸 아니면 검색 시작 */
                    searchKakaoLocal(kakaoKeyword);
                }
            }
        });


        return view;
    }

    private AlertDialog.Builder initNoKeyword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    List<KakaoLocalKeywordDocument> resultDoc = new ArrayList<>();
                    resultDoc = kakaoResult.getDocuments();

                    for (KakaoLocalKeywordDocument items : resultDoc) {
                        /* 결과 리스트의 개수 만큼 SearchResultItem 객체 만들어서 리사이클러뷰 arrayList에 추가 */
                        SearchResultItem resultItem = new SearchResultItem();
                        resultItem.setPlaceName(items.getPlaceName());
                        resultItem.setPlaceRoadAddress(items.getRoadAddressName());
                        resultItem.setPlaceCategory(items.getCategoryName());
                        searchResultItems.add(resultItem);
                    }

                    kakaoHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            /* UI 변경은 여기서 */

                            /* 어댑터에 데이터 변경 알려주기 */
                            searchResultAdapter.notifyDataSetChanged();
                        }
                    });

                } else {
                    Log.e("kakao search", "Response Body is null");
                }
            }
        });
    }
}
