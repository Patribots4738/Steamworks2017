package org.usfirst.frc.team4738.wrapper;

import org.usfirst.frc.team4738.interfaces.Gamepad;

public class PIDMecanumDrive {
	public PIDVictorSP[] motors;
	Gamepad pad;
	
	/**
	 * @param radius The radius of the wheels
	 * @param Kp The proportional PID constant.
	 * @param Ki The integral PID constant.
	 * @param Kd The derivative PID constant.
	 * @param ports The ports to be used.
	 * @deprecated DO NOT USE, TO BE FIXED
	 */
	public PIDMecanumDrive(double radius, double Kp, double Ki, double Kd, int... ports){
		motors = new PIDVictorSP[ports.length];
		
		// FIXME: This is broken, wrong ports used.
		for(int i = 0; i < ports.length; i++){
//		for(int i : ports){
			motors[i] = new PIDVictorSP(ports[i], 2*ports[i], 2*ports[i] + 1, 4, Kp, Ki, Kd);
		}
	}
	
	/**
	 * @param motors An array of all the PID enabled motors to use.
	 */
	public PIDMecanumDrive(PIDVictorSP[] motors) {
		this.motors = motors;
	}
	
	/**
	 * @param radius The radius of the wheels
	 * @param Kp The proportional PID constant.
	 * @param Ki The integral PID constant.
	 * @param Kd The derivative PID constant.
	 * @param motors Motors to be used
	 * @deprecated Use PIDMecanumDrive(PIDVictorSP[] motors) instead.
	 */
	public PIDMecanumDrive(double radius, double Kp, double Ki, double Kd, PIDVictorSP... motors){
		// TODO: Either do something with the constants and radius, or delete constructor.
		this.motors = motors;
	}
	
	/**
	 * Sets the PID constants for the PIDVictorSP motors.
	 * @param Kp
	 * @param Ki
	 * @param Kd
	 */
	public void setPID(double Kp, double Ki, double Kd){
		for(int i = 0; i < motors.length; i++){
			motors[i].setPID(Kp, Ki, Kd);
		}
	}
	
	/**
	 * A really stupid function that returns whatever you give it.
	 * @param speed Number to be returned
	 * @return Whatever you put in for speed
	 */
	public double getCurrentSpeed(double speed){
		return speed; // WHY!? Why does this exist!?
	}
	
	/**
	 * Drives the bot with mecanum code in a linear fashion,
	 * as opposed to parabolic. Also uses Lerp.
	 * @param x The normalized strafing value 
	 * @param y The normalized forward value
	 * @param rotation Normalized clockwise rotation speed.
	 * @param dampening Lerp value, closer to zero is more dampening.
	 */
	public void linearMecanum(double x, double y, double rotation, double dampening){
		
		double r = Math.hypot(y, x);
		double robotAngle = Math.PI/2 - Math.atan2(y, x);
		motors[0].lerpSet(r * Math.sin(robotAngle + Math.PI/4) + rotation, dampening);
		motors[1].lerpSet(r * Math.cos(robotAngle + Math.PI/4) + rotation, dampening);
		motors[2].lerpSet(-(r * Math.cos(robotAngle + Math.PI/4) - rotation), dampening);
		motors[3].lerpSet(-(r * Math.sin(robotAngle + Math.PI/4) - rotation), dampening);
	}
	
	/**
	 * Drives the bot with mecanum code in a linear fashion,
	 * as opposed to parabolic. Does not use lerp.
	 * @param x The normalized strafing value
	 * @param y The forward direction
	 * @param rotation Normalized clockwise rotation speed.
	 */
	public void linearMecanum(double x, double y, double rotation){
		
		double r = Math.hypot(y, x);
		double robotAngle = Math.PI/2 - Math.atan2(y, x);
		motors[0].set(r * Math.sin(robotAngle + Math.PI/4) + rotation);
		motors[1].set(r * Math.cos(robotAngle + Math.PI/4) + rotation);
		motors[2].set(-(r * Math.cos(robotAngle + Math.PI/4) - rotation));
		motors[3].set(-(r * Math.sin(robotAngle + Math.PI/4) - rotation));
	}

	/**
	 * @author Garett
	 * @param x The normalized strafing value
	 * @param y The normalized forward value
	 * @param rotation Normalized clockwise rotation speed.
	 * @param dampening Lerp value, closer to zero is more dampening.
	 */
	public void parabolicMecanum(double x, double y, double rotation, double dampening){
		
		x *= Math.abs(x);
		y *= Math.abs(y);
		rotation *= Math.abs(rotation);
		
		linearMecanum(x, y, rotation, dampening);
	}
	
	/**
	 * This is normalized, but it will square the values for
	 * a response curve.
	 * @param x Same as others
	 * @param y Same as others
	 * @param rotation Same as others
	 */
	public void parabolicMecanum(double x, double y, double rotation){
		x *= Math.abs(x);
		y *= Math.abs(y);
		rotation *= Math.abs(rotation);
		
		linearMecanum(x, y, rotation);
	}
	
	/**
	 * Resets the encoder values, as name suggests.
	 */
	public void resetEncoders() {
		for (PIDVictorSP motor : motors) {
			motor.encoder.reset();
		}
	}
}