package org.usfirst.frc.team4738.controllers;

import org.usfirst.frc.team4738.controllers.enums.XboxInputs;
import org.usfirst.frc.team4738.controllers.interfaces.Controller;
import org.usfirst.frc.team4738.util.Axes;

import edu.wpi.first.wpilibj.Joystick;

public class XboxController implements Controller {

	Joystick joystick;
	
	public XboxController(int port) {
		this.joystick = new Joystick(port);
	}
	
	@Override
	public Axes getLeftStick() {
		return new Axes(getAxis(XboxInputs.LEFT_STICK_X), getAxis(XboxInputs.LEFT_STICK_Y));
	}

	@Override
	public Axes getRightStick() {
		return new Axes(getAxis(XboxInputs.RIGHT_STICK_X), getAxis(XboxInputs.RIGHT_STICK_Y));
	}

	@Override
	public double getTriggerL() {
		return getAxis(XboxInputs.LEFT_TRIGGER);
	}

	@Override
	public double getTriggerR() {
		return getAxis(XboxInputs.RIGHT_TRIGGER);
	}

	@Override
	public boolean getA() {
		return getButton(XboxInputs.A);
	}

	@Override
	public boolean getB() {
		return getButton(XboxInputs.B);
	}

	@Override
	public boolean getX() {
		return getButton(XboxInputs.X);
	}

	@Override
	public boolean getY() {
		return getButton(XboxInputs.Y);
	}

	@Override
	public boolean getStart() {
		return getButton(XboxInputs.START);
	}

	@Override
	public boolean getBack() {
		return getButton(XboxInputs.BACK);
	}

	@Override
	public boolean getLeftStickButton() {
		return getButton(XboxInputs.LEFT_STICK_BUTTON);
	}

	@Override
	public boolean getRightStickButton() {
		return getButton(XboxInputs.RIGHT_STICK_BUTTON);
	}
	
	private double getAxis(XboxInputs xbox) {
		return joystick.getRawAxis(xbox.num);
	}
	
	private boolean getButton(XboxInputs xbox) {
		return joystick.getRawButton(xbox.num);
	}
	
}
