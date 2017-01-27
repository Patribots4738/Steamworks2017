package org.usfirst.frc.team4738.robot;

import java.util.Timer;

import org.usfirst.frc.team4738.controllers.Joystick3D;
import org.usfirst.frc.team4738.controllers.XboxController;
import org.usfirst.frc.team4738.controllers.interfaces.Controller;
import org.usfirst.frc.team4738.controllers.interfaces.Input;
import org.usfirst.frc.team4738.controllers.interfaces.JoystickWrapper;
import org.usfirst.frc.team4738.drive.MotorContainer;
import org.usfirst.frc.team4738.drive.PIDUpdate;
import org.usfirst.frc.team4738.util.Mathd;
import org.usfirst.frc.team4738.util.PID;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Servo;

public class Robot extends IterativeRobot {
	
	MotorContainer motors; 
	XboxController xboxController;
	Joystick3D joystick;
	Servo arm;
	
	public double forward = 0D;
	public double strafe = 0D;
	public double rotate = 0D;
	
	Input inputMethod;
	
	PID wheel1, wheel2, wheel3, wheel4;
	Timer timer = new Timer();
	
	public Robot() {
		motors = new MotorContainer(new int[] {Constants.PWM_PORT[0], Constants.PWM_PORT[1], Constants.PWM_PORT[2], Constants.PWM_PORT[3]}, inputMethod);
		xboxController = new XboxController(1);
		joystick = new Joystick3D(0);
		
		timer.schedule(new PIDUpdate(motors, this), Constants.TICK_RATE);
	}

	public void teleopPeriodic() {
		if (inputMethod instanceof Input) {
			if (inputMethod instanceof Controller) {
				Controller controller = (Controller) inputMethod;
				forward = -1 * Constants.DIR_SPEED_MOD[0] * Mathd.curve(controller.getLeftStick().getY(), Constants.DIR_SPEED_EXP[0]);
				strafe = Constants.DIR_SPEED_MOD[1] * Mathd.curve(controller.getLeftStick().getX(), Constants.DIR_SPEED_EXP[1]);
				rotate = Constants.DIR_SPEED_MOD[2] * Mathd.curve(controller.getRightStick().getX(), Constants.DIR_SPEED_EXP[2]);
			} else if (inputMethod instanceof Joystick3D) {
				JoystickWrapper joystick = (JoystickWrapper) inputMethod;
				forward = -1 * Constants.DIR_SPEED_MOD[0] * Mathd.curve(joystick.getY(), Constants.DIR_SPEED_EXP[0]);
				strafe = Constants.DIR_SPEED_MOD[1] * Mathd.curve(joystick.getX(), Constants.DIR_SPEED_EXP[1]);
				rotate = Constants.DIR_SPEED_MOD[2] * Mathd.curve(joystick.getZ(), Constants.DIR_SPEED_EXP[2]);
			}
			
			motors.updateWheelSpeeds(forward, strafe, rotate);
			System.out.println(String.valueOf(forward) + " " + String.valueOf(strafe) + " " + String.valueOf(rotate));
		}
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
	
	public void autonomousInit() {
	}
	
	public void autonomousPeriodic() {
		
	}
	
}
