package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.Servo;

public class Arms {

	Servo[] servos;
	
	public Arms (int... ports){
		servos = new Servo [ports.length];
		
		for (int i = 0; i < ports.length; i++) {
			servos[i] = new Servo (ports[i]);
		}
	}
	
	public void setArms(double... angle){
		for(int i = 0; i < servos.length; i++){
			servos[i].setAngle(angle[i]);
		}
	}
	
	public void openArms(boolean state){
		if(state){
			setArms(0, 0);
		} else {
			setArms(90, 90);
		}
	}
}
