package com.yanghyeonjin.androidexamples.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yanghyeonjin.androidexamples.R;

public class TuesdayFragment extends Fragment {

    private View view;

    public static TuesdayFragment newInstance() {
        TuesdayFragment tuesdayFragment = new TuesdayFragment();
        return tuesdayFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tuesday, container, false);
        return view;
    }
}
