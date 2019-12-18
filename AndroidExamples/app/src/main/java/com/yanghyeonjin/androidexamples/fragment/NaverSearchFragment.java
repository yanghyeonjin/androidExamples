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
import com.yanghyeonjin.androidexamples.model.NaverSearchLocalItem;
import com.yanghyeonjin.androidexamples.model.NaverSearchLocalResult;
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

public class NaverSearchFragment extends Fragment {

    private View view;

    private RecyclerView rvNaverResult;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SearchResultItem> searchResultItems;
    private SearchResultAdapter searchResultAdapter;

    private String naverClientID, naverClientSecret;
    private OkHttpClient naverHttpClient;
    private NaverSearchLocalResult naverResult;
    private Handler naverHandler;

    private EditText etNaverSearch;
    private Button btnNaverSearch;

    public static NaverSearchFragment newInstance() {
        NaverSearchFragment naverSearchFragment = new NaverSearchFragment();
        return naverSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_naver_search, container, false);

        /* 네이버 client ID, client secret */
        naverClientID = getString(R.string.naver_client_id);
        naverClientSecret = getString(R.string.naver_client_secret);

        /* 아이디 연결 */
        rvNaverResult = view.findViewById(R.id.rv_naver_search_result);
        etNaverSearch = view.findViewById(R.id.et_naver_search_local);
        btnNaverSearch = view.findViewById(R.id.btn_naver_search_local);


        /* 리사이클러뷰 셋팅 */
        rvNaverResult.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvNaverResult.setLayoutManager(layoutManager);
        searchResultItems = new ArrayList<>();
        searchResultAdapter = new SearchResultAdapter(searchResultItems, getContext());
        rvNaverResult.setAdapter(searchResultAdapter);



        /* okhttp3 초기화 */
        naverHttpClient = new OkHttpClient();


        /* 핸들러 초기화 */
        naverHandler = new Handler(Looper.getMainLooper());


        /* 검색어 없을 때 나타나는 AlertDialog */
        AlertDialog.Builder builder = initNoKeyword();


        /* 네이버 검색 눌렀을 때 */
        btnNaverSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naverKeyword = etNaverSearch.getText().toString();

                if (naverKeyword.isEmpty()) {
                    /* 검색창 빈칸이면 */
                    builder.show();
                } else {
                    /* 빈칸 아니면 검색 시작 */
                    searchNaverLocal(naverKeyword);
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

        naverHttpClient.newCall(naverRequest).enqueue(new Callback() {
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

                    List<NaverSearchLocalItem> resultItems = new ArrayList<>();
                    resultItems = naverResult.getItems();
                    String placeCategory = naverResult.getCategory();

                    /* 기존 배열 리스트가 존재하지 않도록 */
                    searchResultItems.clear();

                    for (NaverSearchLocalItem item : resultItems) {
                        /* 결과 리스트의 개수 만큼 SearchResultItem 객체 만들어서 리사이클러뷰 arrayList에 추가 */
                        SearchResultItem resultItem = new SearchResultItem();
                        resultItem.setPlaceName(item.getTitle());
                        resultItem.setPlaceRoadAddress(item.getRoadAddress());
                        resultItem.setPlaceCategory(placeCategory);
                        searchResultItems.add(resultItem);
                    }

                    naverHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            /* UI 변경은 여기서 */

                            /* 어댑터에 데이터 변경 알려주기 */
                            searchResultAdapter.notifyDataSetChanged();
                        }
                    });

                } else {
                    Log.e("naver search", "Response Body is null");
                }
            }
        });
    }
}
