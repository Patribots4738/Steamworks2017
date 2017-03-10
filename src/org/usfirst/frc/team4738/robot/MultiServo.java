package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.Servo;

public class MultiServo {

	Servo[] servos;
	double[][] bounds;
	
	public MultiServo (int... ports){
		servos = new Servo [ports.length];
		
		for (int i = 0; i < ports.length; i++) {
			servos[i] = new Servo (ports[i]);
		}
	}
	
	public void setServos(double... angle){
		for(int i = 0; i < servos.length; i++){
			servos[i].setAngle(angle[i]);
		}
	}
	//chagned ... to []
	public void setBounds(double[][]  bounds){
		this.bounds = bounds;
	}
	
	
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
