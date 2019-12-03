package com.yanghyeonjin.androidexamples.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yanghyeonjin.androidexamples.R;

public class WednesdayFragment extends Fragment {

    private View view;

    public static WednesdayFragment newInstance() {
        WednesdayFragment wednesdayFragment = new WednesdayFragment();
        return wednesdayFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wednesday, container, false);
        return view;
    }
}
