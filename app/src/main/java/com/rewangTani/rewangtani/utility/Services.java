package com.rewangTani.rewangtani.utility;

import static com.rewangTani.rewangtani.utility.App.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.upperbar.infoperingatancuaca.BerandaInfoPeringatanCuaca;
import com.rewangTani.rewangtani.upperbar.infoperingatancuaca.TambahInfoPeringatanCuaca;

import java.nio.file.NotLinkException;
import java.util.Locale;

public class Services extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String judul = intent.getStringExtra("judul").toUpperCase(Locale.ROOT);
        String ket = intent.getStringExtra("ket");

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent = new Intent(this, BerandaInfoPeringatanCuaca.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(judul)
                .setContentText(ket)
                .setSmallIcon(R.drawable.icon_logo_alert)
                .setContentIntent(pendingIntent)
                .setTimeoutAfter(10000)
                .setSound(defaultSoundUri)
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
