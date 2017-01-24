package org.usfirst.frc.team4738.drive;

import org.usfirst.frc.team4738.controllers.XboxController;
import org.usfirst.frc.team4738.controllers.interfaces.Controller;
import org.usfirst.frc.team4738.controllers.interfaces.Input;
import org.usfirst.frc.team4738.controllers.interfaces.JoystickWrapper;
import org.usfirst.frc.team4738.robot.Constants;
import org.usfirst.frc.team4738.util.Mathd;

import edu.wpi.first.wpilibj.VictorSP;

public class MotorContainer {
	
	private VictorSP[] motors;
	
	public MotorContainer(int[] ports) {
		motors = new VictorSP[ports.length];
		
		for (int i=0; i<ports.length; i++) {
			motors[i] = new VictorSP(ports[i]);
		}
	}
	
	public void resetWheelSpeeds() {
		for (int i=0; i<motors.length; i++) {
			motors[i].set(0);
		}
	}
	
	public void updateWheelSpeeds(Input input) {
		double[] speeds = getSpeeds(input);
		for (int i=0; i<motors.length;i++) {
			motors[i].set(speeds[i]);
		}
	}
	
	public void updateWheelSpeeds(double forward, double strafe, double rotate) {
		double[] speeds = getSpeeds(forward, strafe, rotate);
		for (int i=0; i<motors.length;i++) {
			motors[i].set(speeds[i]);
		}
	}
	
	private double[] getSpeeds(double forward, double strafe, double rotate) {
		
		double r = Math.hypot(forward, strafe);
		double robotAngle = Math.PI/2 - Math.atan2(forward, strafe);
		final double v1 =  (r * Math.sin(robotAngle + Math.PI/4) + rotate);
		final double v2 = -(r * Math.cos(robotAngle + Math.PI/4) - rotate);
		final double v3 =  (r * Math.cos(robotAngle + Math.PI/4) + rotate);
		final double v4 = -(r * Math.sin(robotAngle + Math.PI/4) - rotate);
		
		double[] speeds = {v1, v2, v3, v4};
		return speeds;
		
		
	}

	public double[] getSpeeds(Input input) {
		if (input instanceof JoystickWrapper) {
			JoystickWrapper joystick = (JoystickWrapper) input;
			double forward = Constants.DIR_SPEED_MOD[0] * joystick.getSlider() * Mathd.curve(joystick.getY(), Constants.DIR_SPEED_EXP[0]) * -1;
			double strafe = Constants.DIR_SPEED_MOD[1] * joystick.getSlider() * Mathd.curve(joystick.getX(), Constants.DIR_SPEED_EXP[1]);
			double rotate = Constants.DIR_SPEED_MOD[2] * Mathd.curve(joystick.getZ(), Constants.DIR_SPEED_EXP[2]);
			
			return getSpeeds(forward, strafe, rotate);
		} else if (input instanceof Controller) {
			XboxController xboxController = (XboxController) input;
			double forward = Constants.DIR_SPEED_MOD[0] *  Mathd.curve(xboxController.getLeftStick().getY(), Constants.DIR_SPEED_EXP[0]) * -1;
			double strafe = Constants.DIR_SPEED_MOD[1] * Mathd.curve(xboxController.getLeftStick().getX(), Constants.DIR_SPEED_EXP[1]);
			double rotate = Constants.DIR_SPEED_MOD[2] * Mathd.curve(xboxController.getRightStick().getX(), Constants.DIR_SPEED_EXP[2]);
			
			return getSpeeds(forward, strafe, rotate);
		}
		
		return null;
	}
	
}