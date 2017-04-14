package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.Servo;

/**
 * @author Jason & Jacob
 */
public class MultiServo {

	Servo[] servos;
	double[][] bounds;
	
	/**
	 * @param ports The ports for the servos.
	 * Creates multiple servos that toggle within defined bounds together.
	 */
	public MultiServo (int... ports){
		servos = new Servo [ports.length];
		
		for (int i = 0; i < ports.length; i++) {
			servos[i] = new Servo (ports[i]);
		}
	}
	
	/**
	 * @param angle the angles to set for the servos
	 */
	public void setServos(double... angle){
		for(int i = 0; i < servos.length; i++){
			servos[i].setAngle(angle[i]);
		}
	}
	
	/**
	 * @param bounds A 2D array of doubles to set the bounds of the servos
	 * Note: The first value is the on state
	 */
	public void setBounds(double[][]  bounds){
		this.bounds = bounds;
	}
	
	/**
	 * @param state Tells whether the servos should be the first or second values of the bounds.
	 */
	public void servoState(boolean state){
		for(int i = 0; i < bounds.length; i++){
			if(state){
				servos[i].setAngle(bounds[i][0]);
			} else {
				servos[i].setAngle(bounds[i][1]);
			}
		}
	}
}
