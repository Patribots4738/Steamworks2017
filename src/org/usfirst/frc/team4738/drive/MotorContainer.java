package org.usfirst.frc.team4738.drive;

import org.usfirst.frc.team4738.controllers.interfaces.Input;

import edu.wpi.first.wpilibj.VictorSP;

public class MotorContainer {
	
	private VictorSP[] motors;
	
	public MotorContainer(int[] ports) {
		motors = new VictorSP[ports.length];
		
		for (int i=0; i<ports.length; i++) {
			motors[i] = new VictorSP(ports[i]);
		}
	}
	
	public void updateWheelSpeeds(Input input, DriveTypes driveModel) {
		double[] speeds = driveModel.getSpeeds(input);
		for (int i=0; i<motors.length;i++) {
			motors[i].set(speeds[i]);
		}
		
		
	}
	
}