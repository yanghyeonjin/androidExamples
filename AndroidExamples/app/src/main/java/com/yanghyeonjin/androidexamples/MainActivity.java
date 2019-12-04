package com.yanghyeonjin.androidexamples;

import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yanghyeonjin.androidexamples.fragment.DoitExampleFragment;
import com.yanghyeonjin.androidexamples.fragment.EtcExampleFragment;
import com.yanghyeonjin.androidexamples.fragment.HongdroidExampleFragment;
import com.yanghyeonjin.androidexamples.receiver.NetworkReceiver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mainBottomNavi; // 하단 네비게이션 뷰
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private HongdroidExampleFragment hongdroidExampleFragment;
    private DoitExampleFragment doitExampleFragment;
    private EtcExampleFragment etcExampleFragment;
    private static final String LOG_TAG = "MainActivity";
    private long backBtnTime = 0;
    private NetworkReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 키 해시 알아내기
        getHashKey();

        /* 아이디 연결 */
        mainBottomNavi = findViewById(R.id.bottom_navigation_main);



        mainBottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_hongdroid:
                        setFragment(0);
                        break;
                    case R.id.menu_doit:
                        setFragment(1);
                        break;
                    case R.id.menu_etc:
                        setFragment(2);
                        break;
                }
                return true;
            }
        });

        hongdroidExampleFragment = new HongdroidExampleFragment();
        doitExampleFragment = new DoitExampleFragment();
        etcExampleFragment = new EtcExampleFragment();

        setFragment(0);


        // 브로드 캐스트 리시버 등록 !
        IntentFilter intentFilter = new IntentFilter();
        networkReceiver = new NetworkReceiver();

        // 네트워크 상태 변화되는 값을 IntentFilter에 추가
        // NetworkReceiver 클래스의 intent.getAction()과 연관있음.
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(networkReceiver, intentFilter);
    }

    /* 프래그먼트 교체 */
    private void setFragment(int i) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (i) {
            case 0:
                fragmentTransaction.replace(R.id.frame_main, hongdroidExampleFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.frame_main, doitExampleFragment);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.frame_main, etcExampleFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (gapTime >= 0 && gapTime <= 2000) {
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 브로드 캐스트 리시버 해제 !
        // 광역적으로 네트워크 상태를 체크한다면 아래 구문을 만들 필요가 없다.
        // 그러나 어디에서도 해제하지 않으면 앱을 종료해도 계속 체크하기 때문에 휴대폰 과부하가 올 수 있음.
        // 따라서 어떤 한 액티비티 내에서는 만들어줘야한다.
        unregisterReceiver(networkReceiver);
    }

    private void getHashKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.yanghyeonjin.androidexamples", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(LOG_TAG,"key_hash="+Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
