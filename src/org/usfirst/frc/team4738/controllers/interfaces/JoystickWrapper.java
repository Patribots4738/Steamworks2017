package org.usfirst.frc.team4738.controllers.interfaces;

public interface JoystickWrapper extends Input {
	public double getX();
	public double getY();
	public double getZ();
	
	public boolean getTrigger();
	public boolean getButton(int button);
	public double getSlider();
}
