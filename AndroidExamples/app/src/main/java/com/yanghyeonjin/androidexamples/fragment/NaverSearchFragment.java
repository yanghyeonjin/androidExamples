package com.yanghyeonjin.androidexamples.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yanghyeonjin.androidexamples.R;
import com.yanghyeonjin.androidexamples.adapter.SearchResultAdapter;
import com.yanghyeonjin.androidexamples.model.SearchResultItem;

import java.util.ArrayList;

public class NaverSearchFragment extends Fragment {

    private View view;

    private RecyclerView rvNaverResult;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SearchResultItem> searchResultItems;
    private SearchResultAdapter searchResultAdapter;

    public static NaverSearchFragment newInstance() {
        NaverSearchFragment naverSearchFragment = new NaverSearchFragment();
        return naverSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_naver_search, container, false);

        /* 리사이클러뷰 셋팅 */
        rvNaverResult = view.findViewById(R.id.rv_naver_search_result);
        rvNaverResult.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvNaverResult.setLayoutManager(layoutManager);
        searchResultItems = new ArrayList<>();

        searchResultAdapter = new SearchResultAdapter(searchResultItems, getContext());
        rvNaverResult.setAdapter(searchResultAdapter); // 리사이클러뷰 어댑터 연결


        return view;
    }
}
