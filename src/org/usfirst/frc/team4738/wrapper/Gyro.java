package org.usfirst.frc.team4738.wrapper;

import org.usfirst.frc.team4738.utils.MovingAverage;

import edu.wpi.first.wpilibj.AnalogGyro;

public class Gyro {
	public AnalogGyro gyro;
	MovingAverage average;
	double offset = 0;

	public Gyro(int port) {
		gyro = new AnalogGyro(port);
		gyro.setSensitivity(0.001695);
		gyro.reset();
		average = new MovingAverage(5);
	}

	/**
	 * Gets the angle from the gyro and wraps it if necessary.
	 * @return Gyro Angle
	 */
	public double getAngle() {
		double angle = gyro.getAngle();
		angle = (Math.abs(angle / 360) - (int) (Math.abs(angle / 360)));
		return average.average(angle * 360);
	}
	
	public double getOffsetAngle() {
		return getAngle() + offset;
	}

	public void reset() {
		offset += getAngle();
		gyro.reset();
	}
}
