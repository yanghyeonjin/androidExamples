package com.yanghyeonjin.androidexamples;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.yanghyeonjin.androidexamples.adapter.ViewPagerSearchLocalAdapter;

public class SearchLocalOkhttp3Activity extends AppCompatActivity {

    private FragmentPagerAdapter fragmentPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_local);

        /* 뷰페이저 세팅 */
        ViewPager viewPager = findViewById(R.id.vp_search_local);
        fragmentPagerAdapter = new ViewPagerSearchLocalAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        TabLayout tabLayout = findViewById(R.id.tab_search_local);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
