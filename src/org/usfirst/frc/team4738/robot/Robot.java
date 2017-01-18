package org.usfirst.frc.team4738.robot;

import org.usfirst.frc.team4738.controllers.FancyJoystick;
import org.usfirst.frc.team4738.controllers.Joystick3D;
import org.usfirst.frc.team4738.controllers.XboxController;
import org.usfirst.frc.team4738.controllers.interfaces.Input;
import org.usfirst.frc.team4738.drive.MotorContainer;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	
	MotorContainer motors; 
	XboxController xboxController;
	Joystick3D joystick;
	FancyJoystick collinsJoystick;
	
	Input inputMethod;
	
	public Robot() {
		motors = new MotorContainer(new int[] {Constants.PWM_PORT[0], Constants.PWM_PORT[1], Constants.PWM_PORT[2], Constants.PWM_PORT[3]});
		xboxController = new XboxController(1);
		joystick = new Joystick3D(0);
		collinsJoystick = new FancyJoystick(5);
	}
	
	@Override
	public void teleopPeriodic() {
		if (inputMethod instanceof Input) {
			motors.updateWheelSpeeds(inputMethod);
		}
	}
	
	public void robotInit() {
	}
	
	@Override
	public void teleopInit() {
		inputMethod = null;
		while (!(inputMethod instanceof Input)) {
			if (xboxController.getA()) {
				inputMethod = xboxController;
			} else if (joystick.getTrigger()) {
				inputMethod = joystick;
			} else if (collinsJoystick.getTrigger()) {
				inputMethod = collinsJoystick;
			}
		}
	}
	
	@Override
	public void autonomousInit() {
		
	}
	
	public void autonomousPeriodic() {
		
	}
	
}
