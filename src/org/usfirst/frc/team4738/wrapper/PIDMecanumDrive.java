package org.usfirst.frc.team4738.wrapper;

import org.usfirst.frc.team4738.interfaces.Gamepad;
import org.usfirst.frc.team4738.utils.Mathd;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDMecanumDrive {
	public PIDVictorSP[] motors;
	Gamepad pad;
	
	/**
	 * @param ports This is supposed to be formatted like so
	 * new int{motorPort, Encoder Port A, Encoder Port B}
	 */
	public PIDMecanumDrive(double radius, double Kp, double Ki, double Kd, int... ports){
		motors = new PIDVictorSP[ports.length];
		
		for(int i = 0; i < ports.length; i++){
			motors[i] = new PIDVictorSP(i, 2*i, 2*i + 1, 4, Kp, Ki, Kd);
		}
	}
	
	public PIDMecanumDrive(double radius, double Kp, double Ki, double Kd, PIDVictorSP... motors){
		this.motors = motors;
	}
	
	public void setPID(double Kp, double Ki, double Kd){
		for(int i = 0; i < motors.length; i++){
			motors[i].setPID(Kp, Ki, Kd);
		}
	}
	
	public double getCurrentSpeed(double speed){
		return speed;
	}
	
	public void linearMecanum(double x, double y, double rotation, double dampening){
		
		double r = Math.hypot(y, x);
		double robotAngle = Math.PI/2 - Math.atan2(y, x);
		motors[0].cerpSet(r * Math.sin(robotAngle + Math.PI/4) + rotation, dampening);
		motors[1].cerpSet(r * Math.cos(robotAngle + Math.PI/4) + rotation, dampening);
		motors[2].cerpSet(-(r * Math.cos(robotAngle + Math.PI/4) - rotation), dampening);
		motors[3].cerpSet(-(r * Math.sin(robotAngle + Math.PI/4) - rotation), dampening);
	}
	
	public void linearMecanum(double x, double y, double rotation){
		linearMecanum(x, y, rotation, 8);
	}

	/**@author Garett
	 * @param x
	 * @param y
	 * @param rotation
	 */
	public void parabolicMecanum(double x, double y, double rotation, double dampening){
		
		x *= Math.abs(x);
		y *= Math.abs(y);
		rotation *= Math.abs(rotation);
		
		linearMecanum(x, y, rotation);
		
		/*
		motors[0].set(r * Math.sin(robotAngle + Math.PI/4) + rotation);
		motors[1].set(r * Math.cos(robotAngle + Math.PI/4) + rotation);
		motors[2].set(-(r * Math.cos(robotAngle + Math.PI/4) - rotation));
		motors[3].set(-(r * Math.sin(robotAngle + Math.PI/4) - rotation));
		*/
	}
	
	public void parabolicMecanum(double x, double y, double rotation){
		parabolicMecanum(x, y, rotation, 8);
	}
	
	/**
	 * @author Sami
	 * @param x
	 * @param y
	 * @param absoluteRotation
	 * @param gyro
	 */
	
	double rot;
	
	public void linearHeadlessMecanum(double x, double y, double absoluteRotation, double gyro, double speed) {
		if(absoluteRotation == -1){
			double rot = (absoluteRotation - gyro);
			rot += Math.abs(rot) > 180 ? -360*(rot/Math.abs(rot)) : 0;
			this.rot = rot;
		}
				
		linearMecanum(x / Math.cos(gyro), y / Math.sin(gyro), (this.rot / 360) * speed);
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