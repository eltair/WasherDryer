package net.swapspace.apps.wd;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

public class WasherDryer extends Activity {
    public static final long TIME_TICK = 1000;

    private NotificationManager notificationManager;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        LinearLayout appliances = (LinearLayout) this.findViewById(R.id.Appliances);
        appliances.setPadding(0, 10, 0, 10);
        ApplianceFactory factory = new ApplianceFactory(this);
        Appliance washer = factory.createAppliance("Washer");
        Appliance dryer = factory.createAppliance("Dryer");
        appliances.addView(washer.createLayout());
        appliances.addView(dryer.createLayout());
        
        LinearLayout washerSettings = new LinearLayout(this);
        washerSettings.addView(washer.getCycleLengthEdit());
        washerSettings.addView(washer.getCycleLengthLabel());
        washerSettings.setPadding(0, 10, 0, 0);
        LinearLayout dryerSettings = new LinearLayout(this);
        dryerSettings.addView(dryer.getCycleLengthEdit());
        dryerSettings.addView(dryer.getCycleLengthLabel());
        dryerSettings.setPadding(0, 10, 0, 0);
        
        LinearLayout settings = (LinearLayout)findViewById(R.id.Settings);
        settings.removeAllViews();
        settings.setOrientation(LinearLayout.VERTICAL);
        settings.addView(washerSettings);
        settings.addView(dryerSettings);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notificationManager.cancelAll();
    }
}