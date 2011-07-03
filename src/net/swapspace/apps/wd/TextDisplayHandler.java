package net.swapspace.apps.wd;

import android.widget.TextView;

public class TextDisplayHandler implements DisplayHandler {
    TextView textView;

    public TextDisplayHandler(TextView textView) {
	this.textView = textView;
    }

    @Override
    public void handleChange(long timeLeft) {
	textView.setText(Long.toString(timeLeft));
    }

}
