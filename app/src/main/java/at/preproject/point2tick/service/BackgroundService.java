package at.preproject.point2tick.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import at.preproject.point2tick.Application;
import at.preproject.point2tick.R;
import at.preproject.point2tick.activity.AlarmActivity;

/**
 * Created by Mike on 28.04.2017.
 */

public class BackgroundService extends Service {

    private Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "start", Toast.LENGTH_LONG).show();
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 10000L);

        startForeground(1, updateNotification(getString(R.string.app_name), "running"));
        return START_REDELIVER_INTENT;
    }


    private Notification updateNotification(String title, String text) {
        final Intent intent = new Intent(this, Application.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        return new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(text)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    public void tick() {
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        stopSelf();
    }
}
