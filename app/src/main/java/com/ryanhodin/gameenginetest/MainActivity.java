package com.ryanhodin.gameenginetest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	private TextView tv; // The currently displayed textview.
	private ViewGroup layout; // A handle to the main frame

	private int animLength;
	private double factor; // Timing delay factor

	private Thread worker;

	protected TextContainer text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout=(ViewGroup)findViewById(R.id.mainLayout);
		tv=(TextView)findViewById(R.id.mainText);
		tv.setAlpha(1);

		text=new TextContainer(new ChangeHandler());

		Resources resources=getResources();
		animLength=resources.getInteger(R.integer.animTime);
		factor=resources.getFraction(R.fraction.pauseFactor, 1, 1);

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
					overlay.setAlpha(0);

					overlay.animate()
							.alpha(1)
							.setDuration(animLength)
							.setListener(null);

					tv.animate()
							.alpha(0)
							.setDuration(animLength)
							.setListener(new AnimatorListenerAdapter() {
								@Override
								public void onAnimationEnd(Animator animation) {
									layout.removeView(tv);
									tv=overlay;
								}
							});
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
		sleep(2500 + (1 / factor));
		text.append("\nWell, I'll tell you.");
		sleep(2500);
		text.prepend("You're awake... Finally. We don't have a lot of time.\n");
		sleep(1250);
		text.prepend("Just remember... This is not what it seems to be.\n\n");
		sleep(3500);
		text.forceCallbackSuspend();
		animLength+=250;
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
		animLength+=250;
		text.text(currentText);
		text.append("\nJust like you never will again.");
		text.prepend("Please... I'm... I'm scared.\n\n");
		text.forceCallbackContinue();
		sleep(2000);
		animLength-=500;
		text.prepend("I'm sorry. We will get away from...\nBut I swear, I will get us out of here.\n");
		sleep(1000);
		text.prepend("From him.\n");
		sleep(1250);
		text.append("\n\nJust stay still, stay calm. No point in fighting now.\n\n\n");
		sleep(2000);
		String closer="Everything is going to be alright.";

		text.forceCallbackSuspend();
		animLength+=1500;
		text.prepend(closer + "\n\n");
		text.append(closer);
		text.forceCallbackContinue();
		sleep(1000);
		animLength-=1500;
	}
}
