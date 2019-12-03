package com.yanghyeonjin.androidexamples.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yanghyeonjin.androidexamples.fragment.MondayFragment;
import com.yanghyeonjin.androidexamples.fragment.TuesdayFragment;
import com.yanghyeonjin.androidexamples.fragment.WednesdayFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    // 프래그먼트 교체를 보여주는 처리를 구현한 곳
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MondayFragment.newInstance();
            case 1:
                return TuesdayFragment.newInstance();
            case 2:
                return WednesdayFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    // 상단의 탭 레이아웃 인디케어터 쪽에 텍스트를 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "월요일";
            case 1:
                return "화요일";
            case 2:
                return "수요일";
            default:
                return null;
        }
    }
}
