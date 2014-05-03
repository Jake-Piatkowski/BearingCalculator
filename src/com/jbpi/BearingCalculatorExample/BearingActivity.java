package com.jbpi.BearingCalculatorExample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbpi.BearingCalculatorExample.BearingCalculator.OnBearingChangeListener;

public class BearingActivity extends Activity implements OnBearingChangeListener {

	private static final int SENSOR_READING_INTERVAL_GRAVITY = 5000;
	// private static final int LOCATION_READING_INTERVAL_SPEED = 1000;

	// private LocationManager locationManager;
//	private SensorManager sensorManager;
//	private Sensor sensorRotation;

	private ImageView imageViewRollInner;
	private ImageView imageViewPitchBottom;
	private TextView textViewRoll;
	private TextView textViewPitch;
	
	private BearingCalculator bearingCalculator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

//		setContentView(R.layout.activity_main);

		this.bearingCalculator = new BearingCalculator(this);
		
//		// this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//		this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//		this.sensorRotation = this.sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
//
//		this.imageViewRollInner = (ImageView) findViewById(R.id.imageViewRollInner);
//		this.imageViewPitchBottom = (ImageView) findViewById(R.id.imageViewPitchBottom);
//
//		this.textViewRoll = (TextView) findViewById(R.id.textViewBearingRoll);
//		this.textViewPitch = (TextView) findViewById(R.id.textViewBearingPitch);
	}

	@Override
	public void onResume() {

		super.onResume();
		
		this.bearingCalculator.registerListener();

		// this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		// LOCATION_READING_INTERVAL_SPEED, 0, this);
//		this.sensorManager.registerListener(this, this.sensorRotation,
//				SENSOR_READING_INTERVAL_GRAVITY);
	}

	@Override
	public void onPause() {

		super.onPause();
		
		this.bearingCalculator.unregisterListener();

//		this.sensorManager.unregisterListener(this);
		// this.locationManager.removeUpdates(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {

			openOptionsMenu();
			return true;
		}
		return false;
	}

	@Override
	public void onBearingChanged() {

		double roll = this.bearingCalculator.getBearingRoll();
		double pitch = this.bearingCalculator.getBearingPitch();
		double yaw = this.bearingCalculator.getBearingYaw();
		
		Log.d("BEARINGS", "Roll: " + roll + ", pitch: " + pitch + ", yaw: " + yaw);
	}

	// BEGIN menu methods

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		switch (item.getItemId()) {
//
//			case R.id.menuItemExit:
//
//				this.finish();
//				return true;
//
//			default:
//
//				return super.onOptionsItemSelected(item);
//		}
//	}

	// END menu methods
	// BEGIN sensor callback methods

//	@Override
//	public void onSensorChanged(SensorEvent event) {
//
//		Sensor sensor = event.sensor;
//
//		if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
//
//			float[] rotationMatrix = new float[16];
//			SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
//
//			// Note: code inspired by:
//			// http://en.wikipedia.org/wiki/Rotation_formalisms_in_three_dimensions#Conversion_formulae_between_formalisms
//			// If there's ever need for yaw, here it is
//			// double yaw = Math.toDegrees(-Math.atan2(rotationMatrix[2], rotationMatrix[6]));
//			double angleRoll = Math.toDegrees(Math.atan2(rotationMatrix[8], rotationMatrix[9]));
//			double anglePitch = Math.toDegrees(Math.acos(rotationMatrix[10]));
//			
//			double anglePitchAndroid = -anglePitch + 90;
//
//			this.imageViewRollInner.setRotation((float) angleRoll);
//			this.imageViewPitchBottom.setTranslationY((float)anglePitchAndroid);
//			
//			
//			this.textViewRoll.setText(String.format("%.2f", angleRoll));
//			this.textViewPitch.setText(String.format("%.2f", -anglePitchAndroid));
//		}
//	}
//
//	@Override
//	public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//	}

	// END sensor callback methods
	// BEGIN location callback methods

//	@Override
//	public void onLocationChanged(Location location) {
//
//		// if (location.hasSpeed()) {
//		//
//		// this.textViewSpeed.setText(String.valueOf(location.getSpeed()));
//		// Log.e("test", "speed: " + location.getSpeed());
//		// }
//		// else {
//		//
//		// this.textViewSpeed.setText("n/a");
//		// Log.e("test", "speed NOT AVAILABLE");
//		// }
//	}

//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//
//	}

	// END location callback methods
}