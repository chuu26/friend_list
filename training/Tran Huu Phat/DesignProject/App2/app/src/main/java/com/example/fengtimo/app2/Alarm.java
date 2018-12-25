package com.example.fengtimo.app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String str =intent.getExtras().getString("extra");
        Intent id = new Intent(context,Music.class);
        id.putExtra("extra",str);
        context.startService(id);
    }
}
