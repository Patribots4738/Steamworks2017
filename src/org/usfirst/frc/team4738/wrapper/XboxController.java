package org.usfirst.frc.team4738.wrapper;

import org.usfirst.frc.team4738.enums.Directions;
import org.usfirst.frc.team4738.enums.XboxButtons;

/**
 * @author Owen
 */
public class XboxController extends Gamepad implements org.usfirst.frc.team4738.interfaces.XboxController {

	public XboxController(int port) {
		super(port);
	}

	@Override
	public Axes getLeftStick() {		
		return new Axes(this.getAxis(0), this.getAxis(1));
	}

	@Override
	public Axes getRightStick() {
		return new Axes(this.getAxis(4), this.getAxis(5));
	}

	@Override
	public double getRightTrigger() {
		return this.getAxis(2);
	}

	@Override
	public double getLeftTrigger() {
		return this.getAxis(3);
	}

	@Override
	public boolean getButton(XboxButtons button) {
		return this.getButton(button.ordinal());
	}
	
	public boolean getToggle(XboxButtons button){
		return this.getToggle(button.ordinal());
	}

	@Override
	public boolean getButtonDown(XboxButtons button) {
		return this.getButtonDown(button.ordinal());
	}

	@Override
	public boolean getButtonUp(XboxButtons button) {
		return this.getButtonUp( button.ordinal());
	}
	
	

	@Override
	public boolean getDPad(Directions direction) {
		return this.getPOV(direction);
	}
}