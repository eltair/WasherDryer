package net.swapspace.apps.wd;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
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

	timeLeftView = (TextView) findViewById(R.id.WashTimeLeft);
	notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

	TextView maxTimeView = (TextView) findViewById(R.id.WashCycleLength);
	int cycleLength = Integer.parseInt(maxTimeView.getText().toString());

	NotificationHandler washerNotificationHandler = new Android4NotificationHandler("Washer done!", this, notificationManager);
	ApplianceListener washerListener = new ApplianceListener(new Appliance(washerNotificationHandler, cycleLength * TIME_TICK, TIME_TICK));

	findViewById(R.id.WashReset).setOnClickListener(washerListener);
	findViewById(R.id.WashStart).setOnClickListener(washerListener);
	findViewById(R.id.WashStop).setOnClickListener(washerListener);
    }

    @Override
    protected void onResume() {
	super.onResume();
	notificationManager.cancelAll();
    }

    public class ApplianceListener implements OnClickListener, OnKeyListener {
	private Appliance applianceTimer;
	private final TextView maxTimeView = (TextView) findViewById(R.id.WashCycleLength);
	private final TextView timeLeftView = (TextView) findViewById(R.id.WashTimeLeft);
	private int cycleLength;

	public ApplianceListener(Appliance washerTimer) {
	    cycleLength = Integer.parseInt(maxTimeView.getText().toString());
	    this.applianceTimer = washerTimer;
	}

	public void onClick(View v) {
	    if (v.getId() == R.id.WashReset) {
		cycleLength = Integer.parseInt(maxTimeView.getText().toString());
		applianceTimer.cancel();
		applianceTimer = new Appliance(applianceTimer.getNotificationHandler(), cycleLength * TIME_TICK, TIME_TICK/10);
		timeLeftView.setText("" + cycleLength);
	    } else if (v.getId() == R.id.WashStart) {
		applianceTimer.start();
		Toast.makeText(WasherDryer.this, "Timer started", Toast.LENGTH_SHORT).show();
	    } else if (v.getId() == R.id.WashStop) {
		applianceTimer.cancel();
		Toast.makeText(WasherDryer.this, "Timer stopped", Toast.LENGTH_SHORT).show();
		applianceTimer = new Appliance(applianceTimer.getNotificationHandler(), Long.parseLong(timeLeftView.getText().toString()) * TIME_TICK, TIME_TICK);
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
	private DisplayHandler displayHandler = new TextDisplayHandler(timeLeftView);
	private NotificationHandler notificationHandler;
	
	public Appliance(NotificationHandler notificationHandler, long millisInFuture, long countDownInterval) {
	    super(millisInFuture, countDownInterval);
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
	    displayHandler.handleChange(((long)Math.ceil((double) millisUntilFinished / (double) TIME_TICK)));
	}
    }
}