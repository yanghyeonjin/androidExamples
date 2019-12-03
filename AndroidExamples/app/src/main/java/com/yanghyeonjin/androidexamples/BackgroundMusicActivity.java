package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yanghyeonjin.androidexamples.service.MusicService;

public class BackgroundMusicActivity extends AppCompatActivity {

    private Button btn_background_music_start, btn_background_music_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_music);

        btn_background_music_start = (Button) findViewById(R.id.btn_background_music_start);
        btn_background_music_stop = (Button) findViewById(R.id.btn_background_music_stop);

        btn_background_music_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(), MusicService.class));
            }
        });

        btn_background_music_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getApplicationContext(), MusicService.class));
            }
        });
    }
}
