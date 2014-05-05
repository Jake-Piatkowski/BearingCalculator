package com.jbpi.bearingcalculator;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class BearingCalculator implements SensorEventListener {

	private static final int SENSOR_READING_INTERVAL = 1000;

	private Context context;
	private SensorManager sensorManager;
	private Sensor sensorRotation;

	private static float[] rotationMatrix = new float[16];
	private int bearingRoll;
	private int bearingPitch;
	private int bearingYaw;

	private OnBearingChangeListener onBearingChangeListener;

	@Override
	public void onSensorChanged(SensorEvent event) {

		Sensor sensor = event.sensor;

		if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {

			SensorManager.getRotationMatrixFromVector(BearingCalculator.rotationMatrix, event.values);

			// Note: code inspired by:
			// http://en.wikipedia.org/wiki/Rotation_formalisms_in_three_dimensions#Conversion_formulae_between_formalisms
			double pitch = Math.toDegrees(Math.acos(BearingCalculator.rotationMatrix[10]));
			double roll = Math.toDegrees(Math.atan2(BearingCalculator.rotationMatrix[8], BearingCalculator.rotationMatrix[9]));
			double yaw = Math.toDegrees(-Math.atan2(BearingCalculator.rotationMatrix[2], BearingCalculator.rotationMatrix[6]));

			// Note: storing the values as double would be fine if not for the noise:
			// they keep changing all the time hence storing them as int
			this.bearingPitch = Double.valueOf(pitch).intValue();
			this.bearingRoll = Double.valueOf(roll).intValue();
			this.bearingYaw = Double.valueOf(yaw).intValue();

			this.onBearingChangeListener.onBearingChanged();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void registerListener() {

		this.sensorManager.registerListener(this, this.sensorRotation, SENSOR_READING_INTERVAL);
	}

	public void unregisterListener() {

		this.sensorManager.unregisterListener(this);
	}

	public BearingCalculator(Context context, OnBearingChangeListener onBearingChangeListener) {

		this.context = context;
		this.sensorManager = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
		this.sensorRotation = this.sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		this.onBearingChangeListener = onBearingChangeListener;
	}

	/**
	 * Callback interface.
	 */
	public interface OnBearingChangeListener {

		public void onBearingChanged();
	}

	public int getBearingRoll() {

		return this.bearingRoll;
	}

	public int getBearingPitch() {

		// This change is to account for differences in Android's bearing measurements
		return -this.bearingPitch + 90;
	}

	public int getBearingYaw() {

		return this.bearingYaw;
	}
}
