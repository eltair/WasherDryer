package net.swapspace.apps.wd;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ApplianceListener implements OnClickListener {
    private ApplianceTimer applianceTimer;
    private Appliance appliance;
    private ApplianceTimerFactory timerFactory;
    private Context context;

    public ApplianceListener(Context context, Appliance appliance, ApplianceTimerFactory timerFactory) {
        this.context = context;
        this.appliance = appliance;
        this.timerFactory = timerFactory;
        applianceTimer = timerFactory.create(appliance.getCycleLength());
    }

    public void onClick(View v) {
        Log.w("ApplianceListener", "Got click from " + v.getId());
        if (v == appliance.getResetButton()) {
            applianceTimer.cancel();
            applianceTimer = timerFactory.create(appliance.getCycleLength());
            appliance.getTimeLeftView().setText(Long.toString(appliance.getCycleLength()));
        } else if (v == appliance.getStartButton()) {
            applianceTimer.start();
            Toast.makeText(context, "Timer started", Toast.LENGTH_SHORT).show();
        } else if (v == appliance.getStopButton()) {
            applianceTimer.cancel();
            applianceTimer = timerFactory.create(appliance.getTimeLeft());
            Toast.makeText(context, "Timer stopped", Toast.LENGTH_SHORT).show();
        }
    }
}