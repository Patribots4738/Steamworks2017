package org.usfirst.frc.team4738.controllers;

import org.usfirst.frc.team4738.controllers.interfaces.JoystickWrapper;

import edu.wpi.first.wpilibj.Joystick;

public class FancyJoystick implements JoystickWrapper {

	Joystick joystick;
	
	public FancyJoystick(int port) {
		this.joystick = new Joystick(port);
	}
	
	@Override
	public double getX() {
		return joystick.getRawAxis(0);
	}

	@Override
	public double getY() {
		return joystick.getRawAxis(1);
	}

	@Override
	public double getZ() {
		return joystick.getRawAxis(4);
	}

	@Override
	public boolean getTrigger() {
		return joystick.getRawButton(1);
	}

	@Override
	public boolean getButton(int button) {
		return joystick.getRawButton(button);
	}

	@Override
	public double getSlider() {
		return 1;
	}

}
