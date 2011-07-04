package net.swapspace.apps.wd;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

public class WasherDryer extends Activity {
    public static final long TIME_TICK = 1000;

    private NotificationManager notificationManager;

    private Appliance washer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        LinearLayout appliances = (LinearLayout) this.findViewById(R.id.Appliances);
        ApplianceFactory factory = new ApplianceFactory();
        washer = factory.createAppliance(this, "Washer");
        appliances.addView(washer.createLayout());
    }

    @Override
    protected void onResume() {
        super.onResume();
        notificationManager.cancelAll();
    }
}