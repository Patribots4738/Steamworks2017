package org.usfirst.frc.team4738.wrapper;

import org.usfirst.frc.team4738.utils.Mathd;

import edu.wpi.first.wpilibj.VictorSP;

public class PIDVictorSP {
	
	public VictorSP victor;
	PIDController pid;
	public Encoder encoder;
	int port;
	
	/**
	 * @param victorPort Port for the motor.
	 * @param encoderA Encoder port 1
	 * @param encoderB Encoder port 2
	 * @param radius Radius of the wheels
	 * @param Kp The proportional constant for PID
	 * @param Ki The integral constant for PID
	 * @param Kd The derivative constant for PID
	 */
	public PIDVictorSP(int victorPort, int encoderA, int encoderB, double radius, double Kp, double Ki, double Kd){
		/*   Initializes all the values and internal classes   */
		port = victorPort;
		victor = new VictorSP(victorPort);
		encoder = new Encoder(encoderA, encoderB, radius);
		pid = new PIDController(Kp, Ki, Kd);
	}
	
	/**
	 * Sets the PID based speed of the motor.
	 * @param speed The normalized desired speed value for the robot.
	 */
	public void set(double speed){
		double pidVal = pid.calcPID(speed, encoder.getSpeed() / Constants.TOP_SPEED); // Normalizes top speed value
		victor.set(speed+pidVal); // TODO: Change this to work with SmartDashboard, whether or not we should use PID
	}
	
	/**
	 * Curved Interpolation drive method.
	 * @param speed The normalized desired speed value for the robot
	 * @param dampening The dampening value, closer to zero is more dampening.
	 */
	public void cerpSet(double speed, double dampening){
		set(Mathd.cerp(encoder.getSpeed() / Constants.TOP_SPEED, speed, dampening * pid.deltaTime));
	}
	
	/**
	 * Linear Interpolation drive method.
	 * @param speed The normalized desired speed value for the robot
	 * @param dampening The dampening value, closer to zero is more dampening.
	 */
	public void lerpSet(double speed, double dampening){
		set(Mathd.lerp(victor.get(), speed, dampening * pid.deltaTime));
	}
	
	/**
	 * Sets the PID constants for the PID controller.
	 * @param Kp The proportional constant for PID
	 * @param Ki The integral constant for PID
	 * @param Kd The derivative constant for PID
	 */
	public void setPID(double Kp, double Ki, double Kd){
		pid.setPIDConstants(Kp, Ki, Kd);
	}
}
