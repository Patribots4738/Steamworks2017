package org.usfirst.frc.team4738.wrapper;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import org.usfirst.frc.team4738.interfaces.Gamepad;
import org.usfirst.frc.team4738.utils.Mathd;

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
		motors[0].set(Mathd.lerp(motors[0].encoder.getSpeed(), r * Math.sin(robotAngle + Math.PI/4) + rotation, dampening * motors[0].pid.deltaTime));
		motors[1].set(Mathd.lerp(motors[1].encoder.getSpeed(), r * Math.cos(robotAngle + Math.PI/4) - rotation, dampening * motors[1].pid.deltaTime));
		motors[2].set(Mathd.lerp(motors[2].encoder.getSpeed(), -r * Math.cos(robotAngle + Math.PI/4) + rotation, dampening * motors[2].pid.deltaTime));
		motors[3].set(Mathd.lerp(motors[3].encoder.getSpeed(), -r * Math.sin(robotAngle + Math.PI/4) - rotation, dampening * motors[3].pid.deltaTime));
	}
	
	public void linearMecanum(double x, double y, double rotation){
		linearMecanum(x, y, rotation, 0);
	}

	/**@author Garett
	 * @param x
	 * @param y
	 * @param rotation
	 */
	public void parabolicMecanum(double x, double y, double rotation, double dampening){
		
		x *= -Math.abs(x);
		y *= -Math.abs(y);
		rotation *= Math.abs(rotation);
		
		double r = Math.hypot(y, x);
		double robotAngle = Math.PI/2 - Math.atan2(y, x);
		motors[0].set(Mathd.cerp(motors[0].victor.get(), r * Math.sin(robotAngle + Math.PI/4) + rotation, dampening * motors[0].pid.deltaTime));
		motors[1].set(Mathd.cerp(motors[1].victor.get(), r * Math.cos(robotAngle + Math.PI/4) - rotation, dampening * motors[1].pid.deltaTime));
		motors[2].set(Mathd.cerp(motors[2].victor.get(), -r * Math.cos(robotAngle + Math.PI/4) + rotation, dampening * motors[2].pid.deltaTime));
		motors[3].set(Mathd.cerp(motors[3].victor.get(), -r * Math.sin(robotAngle + Math.PI/4) - rotation, dampening * motors[3].pid.deltaTime));
	}
	
	public void parabolicMecanum(double x, double y, double rotation){
		parabolicMecanum(x, y, rotation, 0);
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
	
	//I think we're supposed to be solving for velocity
	//velocity, robot angle, and speed are constants we need to import. However, we need to solve for velocity,
	//so I think our math might be wrong.
	/*public void wheel1XY(double x, double y, double rotation, double vel, double robotAngle, double speed){
		double solvedX = Math.sqrt((vel*vel) / Math.pow(((Math.sin(robotAngle)) + (Math.PI/4) + speed), 2));
		double solvedY = Math.sqrt((vel*vel) / Math.pow(((Math.cos(robotAngle)) + (Math.PI/4) + speed), 2)); */
}