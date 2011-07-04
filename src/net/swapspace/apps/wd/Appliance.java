package net.swapspace.apps.wd;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Appliance {
    public static final long TIME_TICK = 1000;

    private Context context;
    private TextView timeLeftLabel;
    private TextView timeLeftView;
    private TextView cycleLengthEdit;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private ApplianceTimerFactory timerFactory;

    public Appliance(Context context) {
        this.context = context;
    }

    public TextView getTimeLeftLabel() {
        return timeLeftLabel;
    }

    public void setTimeLeftLabel(TextView timeLeftLabel) {
        this.timeLeftLabel = timeLeftLabel;
    }

    public TextView getTimeLeftView() {
        return timeLeftView;
    }

    public void setTimeLeftView(TextView timeLeftView) {
        this.timeLeftView = timeLeftView;
    }

    public TextView getCycleLengthEdit() {
        return cycleLengthEdit;
    }

    public void setCycleLengthEdit(TextView cycleLengthEdit) {
        this.cycleLengthEdit = cycleLengthEdit;
    }

    public Button getStartButton() {
        return startButton;
    }

    public void setStartButton(Button startButton) {
        this.startButton = startButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public void setStopButton(Button stopButton) {
        this.stopButton = stopButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public void setResetButton(Button resetButton) {
        this.resetButton = resetButton;
    }

    public ApplianceTimerFactory getTimerFactory() {
        return timerFactory;
    }

    public void setTimerFactory(ApplianceTimerFactory timerFactory) {
        this.timerFactory = timerFactory;
    }

    public int getCycleLength() {
        return Integer.parseInt(cycleLengthEdit.getText().toString());
    }

    public int getTimeLeft() {
        return Integer.parseInt(timeLeftView.getText().toString());
    }

    public View createLayout() {
        LinearLayout timeLeft = new LinearLayout(context);
        timeLeft.addView(timeLeftLabel);
        timeLeft.addView(timeLeftView);

        LinearLayout buttons = new LinearLayout(context);
        buttons.addView(startButton);
        buttons.addView(stopButton);
        buttons.addView(resetButton);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(timeLeft);
        layout.addView(buttons);
        return layout;
    }
}
