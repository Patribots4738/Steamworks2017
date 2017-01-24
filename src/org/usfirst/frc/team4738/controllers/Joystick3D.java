package org.usfirst.frc.team4738.controllers;

import org.usfirst.frc.team4738.controllers.interfaces.JoystickWrapper;

import edu.wpi.first.wpilibj.Joystick;

public class Joystick3D implements JoystickWrapper {
	Joystick joystick;
	
	public Joystick3D(int port) {
		this.joystick = new Joystick(port);
	}

	public double getX() {
		return joystick.getRawAxis(0);
	}

	public double getY() {
		return joystick.getRawAxis(1);
	}

	public double getZ() {
		return joystick.getRawAxis(2);
	}

	public boolean getTrigger() {
		return joystick.getRawButton(1);
	}

	public boolean getButton(int button) {
		return joystick.getRawButton(button);
	}

	public double getSlider() {
		return (-1*joystick.getRawAxis(3)+1)/2;
	}
}
