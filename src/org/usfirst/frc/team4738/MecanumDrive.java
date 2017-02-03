package org.usfirst.frc.team4738;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * @author Garett
 */

/*
 * Make a constructor that sets 4 VicterSP motor Controllers class variables. Then create a "LinearMecanum" function that gets 3 axes (doubles) labled
 * X, Y, Rotation. Have the mechanum drive set the Motors according to the Math shown in the other code. 
 */

public class MecanumDrive {
	
	//creating an array for all the motors to be assigned to
	VictorSP[] motors;
	
	public MecanumDrive(int... ports){
		motors = new VictorSP[ports.length];
		
		for(int i = 0; i < ports.length; i++){
			motors[i] = new VictorSP(ports[i]);
		}
	}
	
	public void linearMecanum(double x, double y, double rotation){
		
		double r = Math.hypot(y, x);
		double robotAngle = Math.PI/2 - Math.atan2(y, x);
		motors[1].set(r * Math.sin(robotAngle + Math.PI/4) + rotation);
		motors[1].set(r * Math.cos(robotAngle + Math.PI/4) - rotation);
		motors[2].set(r * Math.cos(robotAngle + Math.PI/4) + rotation);
		motors[3].set(r * Math.sin(robotAngle + Math.PI/4) - rotation);
	}

	public void parabolicMecanum(double x, double y, double rotation){
		
		x *= Math.abs(x);
		y *= Math.abs(y);
		rotation *= Math.abs(rotation);
		
		
		double r = Math.hypot(y, x);
		double robotAngle = Math.PI/2 - Math.atan2(y, x);
		motors[1].set(r * Math.sin(robotAngle + Math.PI/4) + rotation);
		motors[1].set(r * Math.cos(robotAngle + Math.PI/4) - rotation);
		motors[2].set(r * Math.cos(robotAngle + Math.PI/4) + rotation);
		motors[3].set(r * Math.sin(robotAngle + Math.PI/4) - rotation);
	}
	
}