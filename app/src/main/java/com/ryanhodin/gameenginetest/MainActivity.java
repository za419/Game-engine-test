package com.ryanhodin.gameenginetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	private TextView tv; // The currently displayed textview.
	private ViewGroup layout; // A handle to the main frame

	private double factor=2.5; // Timing delay factor

	private Thread worker;

	protected TextContainer text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout=(ViewGroup)findViewById(R.id.mainLayout);
		tv=(TextView)findViewById(R.id.mainText);

		text=new TextContainer(new ChangeHandler());

		reinitializeWorker();
	}

	private void reinitializeWorker() {
		if (worker!=null)
			worker.interrupt();

		worker=new Thread(new Runnable() {
			@Override
			public void run() {
				updateText();
			}
		});
		worker.start();
	}

	private class ChangeHandler implements TextContainer.Callable {
		@Override
		public void call(String old, final String replacement) {
			final TextView overlay=new TextView(MainActivity.this);
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					layout.addView(overlay);
					overlay.setText(replacement);
				}
			});
			sleep(250 / factor);
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					layout.removeView(tv);
					tv=overlay;
				}
			});
		}
	}

	public boolean sleep(long millis) {
		try {
			Thread.sleep(Math.round(factor*millis));
		} catch (InterruptedException e) {
			return true;
		}
		return false;
	}

	public boolean sleep(double millis) { // Rounds its argument as a helper.
		return sleep(Math.round(millis));
	}

	protected void updateText() {
		text.text("Hello there.");
		sleep(2500);
		text.append("\n\nYou must be wondering why I brought you here.");
		sleep(2500+(1 / factor));
		text.append("\nWell, I'll tell you.");
		sleep(2500);
		text.prepend("You're awake... Finally. We don't have a lot of time.\n");
		sleep(1250);
		text.prepend("Just remember... This is not what it seems to be.\n\n");
		sleep(3500);
		text.forceCallbackSuspend();
		text.prepend("Just wait, I'll find a way to get us out of here, I swear!\n\n");
		text.append("\n\nYou see, I happen to know a great many things.");
		text.forceCallbackContinue();
		sleep(1750);
		text.forceCallbackSuspend();
		text.append("\nThings that you'd rather never see the light.");
		String currentText=text.prepend("Please... I'm...\n\n");
		text.forceCallbackContinue();
		sleep(1500);
		text.forceCallbackSuspend();
		text.text(currentText);
		text.append("\nJust like you never will again.");
		text.prepend("Please... I'm... I'm scared.\n\n");
		text.forceCallbackContinue();
		sleep(2000);
		text.prepend("I'm sorry. We will get away from...\nBut I swear, I will get us out of here.\n");
		sleep(1000);
		text.prepend("From him.\n");
		sleep(1250);
		text.append("\n\nJust stay still, stay calm. No point in fighting now.\n\n\n");
		sleep(2000);
		String closer="Everything is going to be alright.";

		text.forceCallbackSuspend();
		text.prepend(closer+"\n\n");
		text.append(closer);
		text.forceCallbackContinue();
	}
}
