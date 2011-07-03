package net.swapspace.apps.wd;

import android.os.CountDownTimer;
import android.util.Log;

public class ApplianceTimer extends CountDownTimer {
    private long TIME_TICK = WasherDryer.TIME_TICK;
    private DisplayHandler displayHandler;
    private NotificationHandler notificationHandler;

    public ApplianceTimer(DisplayHandler displayHandler, NotificationHandler notificationHandler, long millisInFuture, long countDownInterval) {
	super(millisInFuture, countDownInterval);
	this.displayHandler = displayHandler;
	this.notificationHandler = notificationHandler;
    }

    public DisplayHandler getDisplayHandler() {
	return displayHandler;
    }

    public void setDisplayHandler(DisplayHandler displayHandler) {
	this.displayHandler = displayHandler;
    }

    public NotificationHandler getNotificationHandler() {
	return notificationHandler;
    }

    public void setNotificationHandler(NotificationHandler notificationHandler) {
	this.notificationHandler = notificationHandler;
    }

    @Override
    public void onFinish() {
	displayHandler.handleChange(0);
	notificationHandler.handleComplete();
    }

    @Override
    public void onTick(long millisUntilFinished) {
	Log.d(this.getClass().toString(), "millisUntilFinished=" + millisUntilFinished);
	displayHandler.handleChange(((long) Math.ceil((double) millisUntilFinished / TIME_TICK)));
    }
}