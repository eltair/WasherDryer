package net.swapspace.apps.wd;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.Toast;

public class Android4NotificationHandler implements NotificationHandler {
    private Activity activity;
    private NotificationManager notificationManager;
    private CharSequence text;

    public Android4NotificationHandler(CharSequence text, Activity activity, NotificationManager notificationManager) {
        this.text = text;
        this.activity = activity;
        this.notificationManager = notificationManager;
    }

    @Override
    public void handleComplete() {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
        long when = System.currentTimeMillis();
        Notification notification = new Notification(R.drawable.icon, text, when);
        CharSequence contentTitle = "WasherDryer Alarm";
        Intent notificationIntent = new Intent(activity, WasherDryer.class);
        PendingIntent contentIntent = PendingIntent.getActivity(activity, 0, notificationIntent, 0);
        notification.setLatestEventInfo(activity, contentTitle, text, contentIntent);
        notificationManager.notify(1, notification);
    }
}
