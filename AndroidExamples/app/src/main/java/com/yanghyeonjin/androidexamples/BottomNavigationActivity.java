package com.yanghyeonjin.androidexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yanghyeonjin.androidexamples.fragment.AccountFragment;
import com.yanghyeonjin.androidexamples.fragment.AlarmFragment;
import com.yanghyeonjin.androidexamples.fragment.EventFragment;
import com.yanghyeonjin.androidexamples.fragment.TrainFragment;
import com.yanghyeonjin.androidexamples.fragment.WCFragment;

public class BottomNavigationActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 하단 네비게이션 뷰
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private AccountFragment accountFragment;
    private AlarmFragment alarmFragment;
    private EventFragment eventFragment;
    private TrainFragment trainFragment;
    private WCFragment wcFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_account:
                        setFragment(0);
                        break;
                    case R.id.menu_alarm:
                        setFragment(1);
                        break;
                    case R.id.menu_event:
                        setFragment(2);
                        break;
                    case R.id.menu_train:
                        setFragment(3);
                        break;
                    case R.id.menu_wc:
                        setFragment(4);
                        break;
                }
                return true;
            }
        });

        accountFragment = new AccountFragment();
        alarmFragment = new AlarmFragment();
        eventFragment = new EventFragment();
        trainFragment = new TrainFragment();
        wcFragment = new WCFragment();

        setFragment(0); // 첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택
    }

    // 프래그먼트 교체가 일어나는 실행문
    private void setFragment(int i) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (i) {
            case 0:
                fragmentTransaction.replace(R.id.bottom_navigation_frame, accountFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.bottom_navigation_frame, alarmFragment);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.bottom_navigation_frame, eventFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.bottom_navigation_frame, trainFragment);
                fragmentTransaction.commit();
                break;
            case 4:
                fragmentTransaction.replace(R.id.bottom_navigation_frame, wcFragment);
                fragmentTransaction.commit();
                break;
        }
    }
}
