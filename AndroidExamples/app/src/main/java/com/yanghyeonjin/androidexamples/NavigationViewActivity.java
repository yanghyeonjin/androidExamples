package com.yanghyeonjin.androidexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class NavigationViewActivity extends AppCompatActivity {

    private NavigationView nvvPages;
    private TextView tvPageName;
    private DrawerLayout drawerPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);

        /* 아이디 연결 */
        nvvPages = findViewById(R.id.nvv_pages);
        tvPageName = findViewById(R.id.tv_page_name);
        drawerPages = findViewById(R.id.drawer_pages);


        nvvPages.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                tvPageName.setText(item.getTitle());

                drawerPages.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}
