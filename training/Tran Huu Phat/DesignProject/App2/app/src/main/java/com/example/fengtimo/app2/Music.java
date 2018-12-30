package com.example.fengtimo.app2;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import javax.annotation.Nullable;

public class Music extends Service {
    MediaPlayer mediaPlayer;
    int id;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        String str=intent.getExtras().getString("extra");

        if (str.equals("on")){
            id = 1;
        }else if (str.equals("off")){
            id = 0;
        }


        if (id ==1 ){
            mediaPlayer = MediaPlayer.create(this,R.raw.nc);
            mediaPlayer.start();
            id = 0;
        }else if (id == 0){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }



        return START_NOT_STICKY;
    }
}
