package interfaces;
import enums.Directions;

public interface Gamepad{
	
	public int getButtonCount();
	
	public double getAxis(int axis);
	public boolean getButton(int button);
	public boolean getToggle(int button);
	public boolean getButtonUp(int button);
	public boolean getButtonDown(int button);
	public boolean getPOV(Directions direction);
	public String toString();
}