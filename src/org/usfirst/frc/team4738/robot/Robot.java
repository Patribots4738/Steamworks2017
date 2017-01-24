package org.usfirst.frc.team4738.robot;

import org.usfirst.frc.team4738.controllers.Joystick3D;

import org.usfirst.frc.team4738.controllers.XboxController;
import org.usfirst.frc.team4738.controllers.interfaces.Input;
import org.usfirst.frc.team4738.drive.MotorContainer;
import org.usfirst.frc.team4738.util.PID;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.*;

public class Robot extends IterativeRobot {
	
	MotorContainer motors; 
	XboxController xboxController;
	Joystick3D joystick;
	
	Input inputMethod;
	
	PID wheel1, wheel2, wheel3, wheel4;
	
	public Robot() {
		motors = new MotorContainer(new int[] {Constants.PWM_PORT[0], Constants.PWM_PORT[1], Constants.PWM_PORT[2], Constants.PWM_PORT[3]});
		xboxController = new XboxController(1);
		joystick = new Joystick3D(0);

		wheel1 = new PID(1, 1, 1);
		wheel2 = new PID(1, 1, 1);
		wheel3 = new PID(1, 1, 1);
		wheel4 = new PID(1, 1, 1);
	}

	public void teleopPeriodic() {
		if (inputMethod instanceof Input) {
			motors.updateWheelSpeeds(inputMethod);
		}
	}
	
	Servo arm;
	
	public void robotInit() {
		
	}
	
	public void teleopInit() {
		motors.resetWheelSpeeds();
		inputMethod = null;
		while (!(inputMethod instanceof Input)) {
			if (xboxController.getA()) {
				inputMethod = xboxController;
			} else if (joystick.getTrigger()) {
				inputMethod = joystick;
			} 
		}
	}
	
	
}
