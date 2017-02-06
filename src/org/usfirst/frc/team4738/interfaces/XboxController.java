package org.usfirst.frc.team4738.interfaces;

import org.usfirst.frc.team4738.enums.Directions;
import org.usfirst.frc.team4738.enums.XboxButtons;
import org.usfirst.frc.team4738.wrapper.Axes;

public interface XboxController extends Joystick{

	Axes getLeftStick();
	Axes getRightStick();
	double getRightTrigger();
	double getLeftTrigger();
	boolean getButton(XboxButtons button);
	boolean getToggle(XboxButtons button);
	boolean getButtonDown(XboxButtons button);
	boolean getButtonUp(XboxButtons button);
	boolean getDPad(Directions direction);
	
}
