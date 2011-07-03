package net.swapspace.apps.wd;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WasherDryer extends Activity {
    public static final long TIME_TICK = 1000;

    private TextView timeLeftView;
    private NotificationManager notificationManager;
    
    private ApplianceTimer washer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.main);

	timeLeftView = (TextView) findViewById(R.id.WashTimeLeft);
	notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

	int cycleLength = Integer.parseInt(((EditText) findViewById(R.id.WashCycleLength)).getText().toString());
	NotificationHandler washerNotificationHandler = new Android4NotificationHandler("Washer done!", this, notificationManager);
	washer = new ApplianceTimer(new TextDisplayHandler(timeLeftView), washerNotificationHandler, cycleLength * TIME_TICK, TIME_TICK);

	ApplianceListener washerListener = new ApplianceListener(washer);
	findViewById(R.id.WashReset).setOnClickListener(washerListener);
	findViewById(R.id.WashStart).setOnClickListener(washerListener);
	findViewById(R.id.WashStop).setOnClickListener(washerListener);
    }

    @Override
    protected void onResume() {
	super.onResume();
	notificationManager.cancelAll();
    }

    public class ApplianceListener implements OnClickListener {
	private ApplianceTimer applianceTimer;
	private final TextView maxTimeView = (TextView) findViewById(R.id.WashCycleLength);
	private final TextView timeLeftView = (TextView) findViewById(R.id.WashTimeLeft);
	private int cycleLength;

	public ApplianceListener(ApplianceTimer washerTimer) {
	    cycleLength = Integer.parseInt(maxTimeView.getText().toString());
	    this.applianceTimer = washerTimer;
	}

	public void onClick(View v) {
	    if (v.getId() == R.id.WashReset) {
		cycleLength = Integer.parseInt(maxTimeView.getText().toString());
		applianceTimer.cancel();
		applianceTimer = new ApplianceTimer(applianceTimer.getDisplayHandler(), applianceTimer.getNotificationHandler(), cycleLength * TIME_TICK, TIME_TICK/10);
		timeLeftView.setText("" + cycleLength);
	    } else if (v.getId() == R.id.WashStart) {
		applianceTimer.start();
		Toast.makeText(WasherDryer.this, "Timer started", Toast.LENGTH_SHORT).show();
	    } else if (v.getId() == R.id.WashStop) {
		applianceTimer.cancel();
		Toast.makeText(WasherDryer.this, "Timer stopped", Toast.LENGTH_SHORT).show();
		applianceTimer = new ApplianceTimer(applianceTimer.getDisplayHandler(), applianceTimer.getNotificationHandler(), Long.parseLong(timeLeftView.getText().toString()) * TIME_TICK, TIME_TICK);
	    }
	}
    }
}