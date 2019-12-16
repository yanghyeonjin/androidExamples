package com.yanghyeonjin.androidexamples.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yanghyeonjin.androidexamples.R;
import com.yanghyeonjin.androidexamples.adapter.ExampleAdapter;
import com.yanghyeonjin.androidexamples.adapter.SearchResultAdapter;
import com.yanghyeonjin.androidexamples.model.SearchResultItem;

import java.util.ArrayList;

public class KakaoSearchFragment extends Fragment {

    private View view;

    private RecyclerView rvKakaoResult;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SearchResultItem> searchResultItems;
    private SearchResultAdapter searchResultAdapter;


    public static KakaoSearchFragment newInstance() {
        KakaoSearchFragment kakaoSearchFragment = new KakaoSearchFragment();
        return kakaoSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kakao_search, container, false);

        /* 리사이클러뷰 셋팅 */
        rvKakaoResult = view.findViewById(R.id.rv_kakao_search_result);
        rvKakaoResult.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvKakaoResult.setLayoutManager(layoutManager);
        searchResultItems = new ArrayList<>();

        searchResultAdapter = new SearchResultAdapter(searchResultItems, getContext());
        rvKakaoResult.setAdapter(searchResultAdapter); // 리사이클러뷰 어댑터 연결


        return view;
    }
}
