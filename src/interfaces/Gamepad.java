package interfaces;

import enums.ControllerType;
import enums.Directions;

public interface Gamepad extends Joystick{
	
	public int getButtonCount();
	
	public double getAxis(int axis);
	public boolean getButton(int button);
	public boolean getToggle(int button);
	public boolean getButtonUp(int button);
	public boolean getButtonDown(int button);
	public boolean getPOV(Directions direction);
	public String toString();
}
