package com.ryanhodin.gameenginetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
	public TextView tv; // A handle to the main text field
	public ViewGroup layout; // A handle to the main frame
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv=(TextView)findViewById(R.id.mainText);
		layout=(ViewGroup)findViewById(R.id.mainLayout);
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
