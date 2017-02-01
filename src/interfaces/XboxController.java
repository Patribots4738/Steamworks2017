package interfaces;

import enums.Directions;
import enums.XboxButtons;
import wrapper.Axes;

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
