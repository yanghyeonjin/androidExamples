package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BigImageActivity extends AppCompatActivity {

    private ImageView ivBigImage;
    private String bigImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        ivBigImage = findViewById(R.id.iv_big_image);

        Intent intent = getIntent();
        bigImage = intent.getStringExtra("bigImage");

        Glide.with(this)
                .load(bigImage)
                .into(ivBigImage);
    }
}
