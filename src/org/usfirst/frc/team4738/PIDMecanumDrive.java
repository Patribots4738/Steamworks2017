package org.usfirst.frc.team4738;

import edu.wpi.first.wpilibj.VictorSP;
import wrapper.Encoder;

public class PIDMecanumDrive {

	VictorSP[] motors;
	Encoder[] encoders;
	
	/**
	 * 
	 * @param ports This is suppost to be formatted like so
	 * new int{motorPort, Encoder Port A, Encoder Port B}
	 */
	public PIDMecanumDrive(int[]... ports){
		motors = new VictorSP[ports.length];
		encoders = new Encoder[ports.length];
		
		for(int i = 0; i > ports.length; i++){
			motors[i] = new VictorSP(ports[i][0]);
			encoders[i] = new Encoder(ports[i][1], ports[i][2]);
		}
	}
}
