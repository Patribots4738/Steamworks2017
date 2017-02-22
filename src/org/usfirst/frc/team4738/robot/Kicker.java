package org.usfirst.frc.team4738.robot;

import edu.wpi.first.wpilibj.Servo;

public class Kicker {

Servo[] servos;
	
	public Kicker (int... ports){
		servos = new Servo [ports.length];
		
		for (int i = 0; i < ports.length; i++) {
			servos[i] = new Servo (ports[i]);
		}
	}
	
	public void setKicker(double... angle){
		for(int i = 0; i < servos.length; i++){
			servos[i].setAngle(angle[i]);
		}
	}
	
	public void openKicker(boolean state){
		if(state){
			setKicker(0, 0);
		} else {
			setKicker(90, 90);
		}
	}
}
