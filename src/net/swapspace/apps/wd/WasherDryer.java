package net.swapspace.apps.wd;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WasherDryer extends Activity {
    public static final long TIME_TICK = 1000;

    private TextView timeLeftView;
    private NotificationManager notificationManager;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	final WasherListener washerListener = new WasherListener();

	final Button washReset = (Button) findViewById(R.id.WashReset);
	washReset.setOnClickListener(washerListener);
	final Button washStart = (Button) findViewById(R.id.WashStart);
	washStart.setOnClickListener(washerListener);
	final Button washStop = (Button) findViewById(R.id.WashStop);
	washStop.setOnClickListener(washerListener);

	final String ns = Context.NOTIFICATION_SERVICE;
	timeLeftView = (TextView) findViewById(R.id.WashTimeLeft);
	notificationManager = (NotificationManager) getSystemService(ns);
	notificationManager.cancelAll();
    }

    public class WasherListener implements OnClickListener, OnKeyListener {
	private Appliance washerTimer;
	private final TextView maxTimeView = (TextView) findViewById(R.id.WashCycleLength);
	private final TextView timeLeftView = (TextView) findViewById(R.id.WashTimeLeft);
	private int cycleLength;

	public WasherListener() {
	    cycleLength = Integer.parseInt(maxTimeView.getText().toString());
	    washerTimer = new Appliance(cycleLength * TIME_TICK, TIME_TICK);
	}

	public void onClick(View v) {
	    if (v.getId() == R.id.WashReset) {
		cycleLength = Integer.parseInt(maxTimeView.getText().toString());
		washerTimer.cancel();
		washerTimer = new Appliance(cycleLength * TIME_TICK, TIME_TICK/10);
		timeLeftView.setText("" + cycleLength);
	    } else if (v.getId() == R.id.WashStart) {
		washerTimer.start();
		Toast.makeText(WasherDryer.this, "Washer timer started", Toast.LENGTH_SHORT).show();
	    } else if (v.getId() == R.id.WashStop) {
		washerTimer.cancel();
		Toast.makeText(WasherDryer.this, "Washer timer stopped", Toast.LENGTH_SHORT).show();
		washerTimer = new Appliance(Long.parseLong(timeLeftView.getText().toString()) * TIME_TICK,
			TIME_TICK);
	    }
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
	    char c = event.getNumber();
	    switch (c) {
	    case '0':
	    case '1':
	    case '2':
	    case '3':
	    case '4':
	    case '5':
	    case '6':
	    case '7':
	    case '8':
	    case '9':
		return true;
	    default:
		return false;
	    }
	}
    }

    public class Appliance extends CountDownTimer {
	public Appliance(long millisInFuture, long countDownInterval) {
	    super(millisInFuture, countDownInterval);
	}

	@Override
	public void onFinish() {
	    timeLeftView.setText("0");
	    Toast.makeText(WasherDryer.this, "Washer done!", Toast.LENGTH_SHORT).show();

	    CharSequence tickerText = "Washer done!";
	    long when = System.currentTimeMillis();

	    Notification notification = new Notification(R.drawable.icon, tickerText, when);
	    CharSequence contentTitle = "WasherDryer Alarm";
	    CharSequence contentText = "Washer done!";
	    Intent notificationIntent = new Intent(WasherDryer.this, WasherDryer.class);
	    PendingIntent contentIntent = PendingIntent.getActivity(WasherDryer.this, 0, notificationIntent, 0);
	    notification.setLatestEventInfo(WasherDryer.this, contentTitle, contentText, contentIntent);
	    notificationManager.notify(1, notification);
	}

	@Override
	public void onTick(long millisUntilFinished) {
	    Log.d(this.getClass().toString(), "millisUntilFinished=" + millisUntilFinished);
	    timeLeftView.setText(Integer.toString((int) Math.ceil((double) millisUntilFinished / (double) TIME_TICK)));
	}
    }
}