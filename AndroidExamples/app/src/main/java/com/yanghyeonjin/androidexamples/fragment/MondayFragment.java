package com.yanghyeonjin.androidexamples.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yanghyeonjin.androidexamples.R;

public class MondayFragment extends Fragment {

    private View view;

    public static MondayFragment newInstance() {
        MondayFragment mondayFragment = new MondayFragment();
        return mondayFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_monday, container, false);
        return view;
    }
}
