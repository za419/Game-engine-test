package com.ryanhodin.gameenginetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	private ViewGroup layout; // A handle to the main frame

	private Thread worker;

	protected TextContainer text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout=(ViewGroup)findViewById(R.id.mainLayout);

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
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					TextView overlay=new TextView(MainActivity.this);
					layout.removeAllViews();
					layout.addView(overlay);
					overlay.setText(replacement);
				}
			});
		}
	}

	public boolean sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			return true;
		}
		return false;
	}

	protected void updateText() {
		text.text("Hello there.");
		sleep(5000);
		text.append("\n\nYou must be wondering why I brought you here.");
		sleep(6000);
		text.append("\nWell, I'll tell you.");
		sleep(2500);
		text.prepend("We don't have a lot of time.\n");
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
		text.prepend("But I swear, I will get us out of here.\nWe will get away from...\n");
		sleep(1000);
		text.prepend("From him.\n");
		sleep(1250);
		text.append("Just stay still, stay calm. No point in fighting now.\n\n\n");
		sleep(2000);
		String closer="Everything is going to be alright.";

		text.forceCallbackSuspend();
		text.prepend(closer);
		text.append(closer);
		text.forceCallbackContinue();
	}
}
