package com.jbpi.BearingCalculatorExample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.bearingcalculatorexample.R;
import com.jbpi.BearingCalculatorExample.BearingCalculator.OnBearingChangeListener;

public class BearingActivity extends Activity implements OnBearingChangeListener {

	private TextView textViewPitch;
	private TextView textViewRoll;
	private TextView textViewYaw;

	private BearingCalculator bearingCalculator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.bearing_activity);

		this.textViewPitch = (TextView) findViewById(R.id.bearing_textview_pitch);
		this.textViewRoll = (TextView) findViewById(R.id.bearing_textview_roll);
		this.textViewYaw = (TextView) findViewById(R.id.bearing_textview_yaw);

		this.bearingCalculator = new BearingCalculator(this);
	}

	@Override
	public void onResume() {

		super.onResume();

		this.bearingCalculator.registerListener();
	}

	@Override
	public void onPause() {

		super.onPause();

		this.bearingCalculator.unregisterListener();
	}

	@Override
	public void onBearingChanged() {

		double pitch = this.bearingCalculator.getBearingPitch();
		double roll = this.bearingCalculator.getBearingRoll();
		double yaw = this.bearingCalculator.getBearingYaw();

		this.textViewPitch.setText(String.valueOf(pitch));
		this.textViewRoll.setText(String.valueOf(roll));
		this.textViewYaw.setText(String.valueOf(yaw));

		Log.d("BEARINGS", "Roll: " + roll + ", pitch: " + pitch + ", yaw: " + yaw);
	}
}