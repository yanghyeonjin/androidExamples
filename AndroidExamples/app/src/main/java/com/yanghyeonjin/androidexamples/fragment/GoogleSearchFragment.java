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

public class GoogleSearchFragment extends Fragment {

    private View view;

    private RecyclerView rvGoogleResult;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SearchResultItem> searchResultItems;
    private SearchResultAdapter searchResultAdapter;

    public static GoogleSearchFragment newInstance() {
        GoogleSearchFragment googleSearchFragment = new GoogleSearchFragment();
        return googleSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_google_search, container, false);

        /* 리사이클러뷰 셋팅 */
        rvGoogleResult = view.findViewById(R.id.rv_google_search_result);
        rvGoogleResult.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvGoogleResult.setLayoutManager(layoutManager);
        searchResultItems = new ArrayList<>();

        searchResultAdapter = new SearchResultAdapter(searchResultItems, getContext());
        rvGoogleResult.setAdapter(searchResultAdapter); // 리사이클러뷰 어댑터 연결

        return view;
    }
}
