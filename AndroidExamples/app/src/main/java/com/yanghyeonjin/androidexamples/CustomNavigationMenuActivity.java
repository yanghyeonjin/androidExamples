package com.yanghyeonjin.androidexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class CustomNavigationMenuActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_navigation_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawer);

        Button openNavigationMenuButton = findViewById(R.id.btn_open_navigation_menu);
        openNavigationMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        Button closeNavigationMenuButton = findViewById(R.id.btn_close_navigation_menu);
        closeNavigationMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });

        /* deprecated */
        /*drawerLayout.setDrawerListener(listener);*/
        drawerLayout.addDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
}
