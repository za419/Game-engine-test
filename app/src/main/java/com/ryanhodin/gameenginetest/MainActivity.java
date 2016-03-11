package com.ryanhodin.gameenginetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
	private ViewGroup layout; // A handle to the main frame

	protected TextContainer text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout=(ViewGroup)findViewById(R.id.mainLayout);

		text=new TextContainer(new ChangeHandler());
	}

	private class ChangeHandler implements TextContainer.Callable {
		@Override
		public void call(String old, String replacement) {
			TextView overlay=new TextView(MainActivity.this);
			layout.removeAllViews();
			layout.addView(overlay);
			overlay.setText(replacement);
		}
	}
}
