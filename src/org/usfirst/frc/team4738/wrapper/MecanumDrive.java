package org.usfirst.frc.team4738.wrapper;

import edu.wpi.first.wpilibj.VictorSP;

/**
 * @author Garett
 */

/*
 * Make a constructor that sets 4 VicterSP motor Controllers class variables. Then create a "LinearMecanum" function that gets 3 axes (doubles) labled
 * X, Y, Rotation. Have the mechanum drive set the Motors according to the Math shown in the other code. 
 */

public class MecanumDrive {
	
	//creating an array for all the motors to be assigned to
	VictorSP[] motors;

	public MecanumDrive(int... ports){
		motors = new VictorSP[ports.length];
		
		motors[0] = new VictorSP(ports[0]);
		motors[1] = new VictorSP(ports[1]);
		motors[2] = new VictorSP(ports[2]);
		motors[3] = new VictorSP(ports[3]);
		
		motors[2].setInverted(true);
		motors[3].setInverted(true);
		
	}
	
	public MecanumDrive(VictorSP... motors){
		this.motors = motors;
	}
	
	public void linearMecanum(double x, double y, double rotation){
		
		double r = Math.hypot(y, x);
		double robotAngle = Math.PI/2 - Math.atan2(y, x);
		motors[0].set(r * Math.sin(robotAngle + Math.PI/4) + rotation);
		motors[1].set(r * Math.cos(robotAngle + Math.PI/4) + rotation);
		motors[2].set(-(r * Math.cos(robotAngle + Math.PI/4) - rotation));
		motors[3].set(-(r * Math.sin(robotAngle + Math.PI/4) - rotation));
	}

	/**@author Garett
	 * @param x
	 * @param y
	 * @param rotation
	 */
	public void parabolicMecanum(double x, double y, double rotation){
		
		x *= Math.abs(x);
		y *= Math.abs(y);
		rotation *= Math.abs(rotation);
		
		
		double r = Math.hypot(y, x);
		double robotAngle = Math.PI/2 - Math.atan2(y, x);
		motors[0].set(r * Math.sin(robotAngle + Math.PI/4) + rotation);
		motors[1].set(r * Math.cos(robotAngle + Math.PI/4) + rotation);
		motors[2].set(-(r * Math.cos(robotAngle + Math.PI/4) - rotation));
		motors[3].set(-(r * Math.sin(robotAngle + Math.PI/4) - rotation));
	}
	
	/**
	 * @author Sami
	 * @param x
	 * @param y
	 * @param absoluteRotation
	 * @param gyro
	 */
	public void linearHeadlessMecanum(double x, double y, double absoluteRotation, double gyro, double speed) {
		double rot = (absoluteRotation - gyro);
		rot += Math.abs(rot) > 180 ? -360*(rot/Math.abs(rot)) : 0;
		linearMecanum(x / Math.cos(gyro), y / Math.sin(gyro), (rot / 360) * speed);
	}
	
	public void linearHeadlessMecanum(double x, double y, double absoluteRotation, double gyro) {
		linearHeadlessMecanum(x, y, absoluteRotation, gyro, 1);
	}
	
	/**
	 * @author Sami
	 * @param x
	 * @param y
	 * @param absoluteRotation
	 * @param gyro
	 */
	public void parabolicHeadlessMecanum(double x, double y, double absoluteRotation, double gyro, double speed) {
		double rot = (absoluteRotation - gyro);
		rot += Math.abs(rot) > 180 ? -360*(rot/Math.abs(rot)) : 0;
		parabolicMecanum(x / Math.cos(gyro), y / Math.sin(gyro), (rot / 360) * speed);
	}
	
	public void parabolicHeadlessMecanum(double x, double y, double absoluteRotation, double gyro) {
		parabolicHeadlessMecanum(x, y, absoluteRotation, gyro, 1);
	}
	
}