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
	}
}
