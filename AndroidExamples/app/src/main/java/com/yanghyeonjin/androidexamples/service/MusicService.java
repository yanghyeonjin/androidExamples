package com.yanghyeonjin.androidexamples.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.yanghyeonjin.androidexamples.R;

public class MusicService extends Service {

    MediaPlayer backgroundMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        backgroundMediaPlayer = MediaPlayer.create(this, R.raw.groove);
        backgroundMediaPlayer.setLooping(false);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        backgroundMediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        backgroundMediaPlayer.stop();
    }
}
