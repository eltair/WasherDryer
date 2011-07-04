package net.swapspace.apps.wd;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ApplianceFactory {
    private Activity activity;
    public ApplianceFactory(Activity activity) {
        this.activity = activity;
    }
    public Appliance createAppliance(String name) {
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

        TextView cycleLengthLabel = new TextView(activity);
        cycleLengthLabel.setText("Full "+name+" Cycle Time (in minutes)");
        appliance.setCycleLengthLabel(cycleLengthLabel);

        final EditText cycleLengthEdit = new EditText(activity);
        cycleLengthEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        cycleLengthEdit.setText(R.string.wash_cycle_length);
        cycleLengthEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cycleLengthEdit.setSelection(0, cycleLengthEdit.getText().length());
            }
        });
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
