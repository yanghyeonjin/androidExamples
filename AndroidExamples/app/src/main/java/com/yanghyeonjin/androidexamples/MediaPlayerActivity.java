package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MediaPlayerActivity extends AppCompatActivity {

    private Button btn_play, btn_stop;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        btn_play = findViewById(R.id.btn_play);
        btn_stop = findViewById(R.id.btn_stop);

        /* 재생버튼 눌렀을 때 */
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(MediaPlayerActivity.this, R.raw.groove);
                mediaPlayer.start();
            }
        });

        /* 정지버튼 눌렀을 때 */
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
