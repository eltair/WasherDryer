package net.swapspace.apps.wd;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

public class ApplianceFactory {
    public Appliance createAppliance(Activity activity, String name) {
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationHandler notificationHandler = new Android4NotificationHandler(name + " done!", activity, notificationManager);

        Appliance appliance = new Appliance(activity);

        Button startButton = new Button(activity);
        startButton.setText("Start " + name);
        appliance.setStartButton(startButton);

        Button stopButton = new Button(activity);
        stopButton.setText("Stop " + name);
        appliance.setStopButton(stopButton);

        Button resetButton = new Button(activity);
        resetButton.setText("Reset " + name);
        appliance.setResetButton(resetButton);

        TextView cycleLengthEdit = (TextView) activity.findViewById(R.id.WashCycleLength);
        appliance.setCycleLengthEdit(cycleLengthEdit);

        TextView timeLeftLabel = new TextView(activity);
        timeLeftLabel.setText(name+" time left: ");
        appliance.setTimeLeftLabel(timeLeftLabel);

        TextView timeLeftView = new TextView(activity);
        timeLeftView.setText(cycleLengthEdit.getText().toString());
        appliance.setTimeLeftView(timeLeftView);

        ApplianceTimerFactory timerFactory = new ApplianceTimerFactory(new TextDisplayHandler(timeLeftView), notificationHandler, WasherDryer.TIME_TICK);
        appliance.setTimerFactory(timerFactory);

        ApplianceListener applianceListener = new ApplianceListener(activity, appliance, timerFactory);
        startButton.setOnClickListener(applianceListener);
        stopButton.setOnClickListener(applianceListener);
        resetButton.setOnClickListener(applianceListener);

        return appliance;
    }
}
