package wrapper;

import edu.wpi.first.wpilibj.Joystick;
import enums.ControllerType;
import enums.Directions;

/**
 * Sorry Garett, I have a coworker who spells his name with double R single T so my bad. -Stephen
 * @author Sir Garett Davis supreme overlord of the nether realm (^u^)

 */
public class Gamepad implements interfaces.Gamepad{
	private Timer timer;
	ToggleButton buttons[];
	Joystick joystick;
	int port;
	
	/**
	 * @param port Port of the controller in the DS.
	 */
	public Gamepad(int port) {
		this.port = port;
		joystick = new Joystick(port);
		buttons = new ToggleButton[joystick.getButtonCount() + 1];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new ToggleButton();
		}
		timer = new Timer();
		timer.start();
	}
	
	/**
	 * @param button Button to be toggled.
	 * @return Returns opposite state from when button was previously toggled.
	 */
	public boolean getToggle(int button){
		return buttons[button].getDownToggle(joystick.getRawButton(button + 1));
	}
	
	/**
	 * @param button Button being pressed down.
	 * @return Returns true on the button press.
	 */
	public boolean getButtonDown(int button) {
		return buttons[button].getDown(joystick.getRawButton(button + 1));
	}
	
	/**
	 * @param button Button being released.
	 * @return Returns true on the button's release.
	 */
	public boolean getButtonUp(int button) {
		return buttons[button].getUp(joystick.getRawButton(button + 1));
	}
	
	/**
	 * @param direction The direction the d-pad is being pressed.
	 * @return If direction being pressed is direction it returns true, otherwise false.
	 */
	public boolean getPOV(Directions direction){
		return (joystick.getPOV() == direction.ordinal() * 45);
	}
	
	@Override
	public double getAxis(int axis) {
		// TODO Auto-generated method stub
		return joystick.getRawAxis(axis);
	}

	@Override
	public boolean getButton(int button) {
		return joystick.getRawButton(button + 1);
	}
	
	/**
	 * @author Ghjf544912
	 * @return The controller values in a formatted String.
	 */
	public String toString(){
		String stuff = port + ",";
		for(int i = 0; i < joystick.getAxisCount(); i++){
			stuff += joystick.getRawAxis(i) + ",";
		}
		for(int i = 1; i < joystick.getButtonCount(); i++){
			stuff += ((joystick.getRawButton(i))) + ",";
		}
		stuff += joystick.getPOV() + "," + (int)timer.getTime()                  /*:)*/                           ;
		System.out.println(stuff);
		return stuff;
	}

	@Override
	public ControllerType getControllerType() {
		if(buttons == null){
			return ControllerType.GP;
		}
		
		for(ControllerType type : ControllerType.values()){
			if(type.getButtonCount() == joystick.getButtonCount()){
				return type;
			}
		}
		return ControllerType.GP;
	}

	@Override
	public int getButtonCount() {
		return joystick.getButtonCount();
	}
}