package org.usfirst.frc.team4738.wrapper;

import org.usfirst.frc.team4738.interfaces.Gamepad;

public class PIDMecanumDrive {
	public PIDVictorSP[] motors;
	Gamepad pad;
	
	/**
	 * @param ports This is supposed to be formatted like so
	 * new int{motorPort, Encoder Port A, Encoder Port B}
	 */
	
	public PIDMecanumDrive(double radius, double Kp, double Ki, double Kd, int... ports){ // Changed int... ports to int[] ports
		motors = new PIDVictorSP[ports.length];
		
		for(int i = 0; i < ports.length; i++){
//		for(int i : ports){
			motors[i] = new PIDVictorSP(ports[i], 2*ports[i], 2*ports[i] + 1, 4, Kp, Ki, Kd);
		}
	}
	
	public PIDMecanumDrive(PIDVictorSP[] motors) {
		this.motors = motors;
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
		return speed; // WHY!? Why does this exist!?
	}
	
	public void linearMecanum(double x, double y, double rotation, double dampening){
		
		double r = Math.hypot(y, x);
		double robotAngle = Math.PI/2 - Math.atan2(y, x);
		motors[0].lerpSet(r * Math.sin(robotAngle + Math.PI/4) + rotation, dampening);
		motors[1].lerpSet(r * Math.cos(robotAngle + Math.PI/4) + rotation, dampening);
		motors[2].lerpSet(-(r * Math.cos(robotAngle + Math.PI/4) - rotation), dampening);
		motors[3].lerpSet(-(r * Math.sin(robotAngle + Math.PI/4) - rotation), dampening);
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
	public void parabolicMecanum(double x, double y, double rotation, double dampening){
		
		x *= Math.abs(x);
		y *= Math.abs(y);
		rotation *= Math.abs(rotation);
		
		linearMecanum(x, y, rotation, dampening);
	}
	
	public void parabolicMecanum(double x, double y, double rotation){
		x *= Math.abs(x);
		y *= Math.abs(y);
		rotation *= Math.abs(rotation);
		
		linearMecanum(x, y, rotation);
	}
}