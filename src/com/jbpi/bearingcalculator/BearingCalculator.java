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

	private double bearingRoll;
	private double bearingPitch;
	private double bearingYaw;

	private OnBearingChangeListener onBearingChangeListener;

	@Override
	public void onSensorChanged(SensorEvent event) {

		Sensor sensor = event.sensor;

		if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {

			float[] rotationMatrix = new float[16];
			SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

			// Note: code inspired by:
			// http://en.wikipedia.org/wiki/Rotation_formalisms_in_three_dimensions#Conversion_formulae_between_formalisms
			this.bearingPitch = Math.toDegrees(Math.acos(rotationMatrix[10]));
			this.bearingRoll = Math.toDegrees(Math.atan2(rotationMatrix[8], rotationMatrix[9]));
			this.bearingYaw = Math.toDegrees(-Math.atan2(rotationMatrix[2], rotationMatrix[6]));

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

	public double getBearingRoll() {

		return this.bearingRoll;
	}

	public double getBearingPitch() {

		// This change is to account for differences in Android's bearing measurements
		return -this.bearingPitch + 90;
	}

	public double getBearingYaw() {

		return this.bearingYaw;
	}
}
