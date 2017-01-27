package org.usfirst.frc.team4738.drive;

import org.usfirst.frc.team4738.controllers.interfaces.Input;
import org.usfirst.frc.team4738.util.PID;

import edu.wpi.first.wpilibj.VictorSP;

public class MotorContainer {
	
	VictorSP[] motors;
	PID[] motorControllers;
	Input input;
	
	public MotorContainer(int[] ports, Input input) {
		motors = new VictorSP[ports.length];
		this.input = input;
		
		for (int i=0; i<ports.length; i++) {
			motors[i] = new VictorSP(ports[i]);
		}
		
		motorControllers = new PID[] {
				new PID(1, 1, 1),
				new PID(1, 1, 1),
				new PID(1, 1, 1),
				new PID(1, 1, 1)
		};
	}
	
	public void resetWheelSpeeds() {
		for (int i=0; i<motors.length; i++) {
			motors[i].set(0);
		}
	}
	
	public void updateWheelSpeeds(double forward, double strafe, double rotate) {
		double[] speeds = getSpeeds(forward, strafe, rotate);
		for (int i=0; i<motors.length;i++) {
			double voltage = motorControllers[i].getPID(speeds[i], motors[i].getSpeed());
			motors[i].set(voltage);
		}
	}
	
	public double[] getSpeeds(double forward, double strafe, double rotate) {
		
		double r = Math.hypot(forward, strafe);
		double robotAngle = Math.PI/2 - Math.atan2(forward, strafe);
		final double v1 =  (r * Math.sin(robotAngle + Math.PI/4) + rotate);
		final double v2 = -(r * Math.cos(robotAngle + Math.PI/4) - rotate);
		final double v3 =  (r * Math.cos(robotAngle + Math.PI/4) + rotate);
		final double v4 = -(r * Math.sin(robotAngle + Math.PI/4) - rotate);
		
		double[] speeds = {v1, v2, v3, v4};
		return speeds;
		
		
	}
	
	public void setInput(Input input) {
		this.input = input;
	}
	
}