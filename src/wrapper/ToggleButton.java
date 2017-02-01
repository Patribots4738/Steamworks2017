package wrapper;

/**
 * @author Garett & Owen
 */
public class ToggleButton {
	
	boolean lastPress = false, lastPressToggle = false;
	private boolean toggle = false;
	
	public ToggleButton(){
		//that's it, you're done
		//yay...
		//goddamn it
		//ikr (-_-)
	}
	
	/**
	 * @param state Input button state.
	 * @return The toggle's state.
	 */
	public boolean getDownToggle(boolean state){
		if(state && !lastPressToggle){
			toggle = !toggle;
			lastPressToggle = true;
		} else if(!state){
			lastPressToggle = false;
		}
		return toggle;
	}
	
	/**
	 * @param state State of the button being released.
	 * @return Returns true on the button's release.
	 */
	public boolean getUp(boolean state){
		if(lastPress == true && state == false){ 
			lastPress = false;
			return true;
		}
		lastPress = state;
		return false;
	}
	
	/**
	 * @param state State of the button being pressed down.
	 * @return Returns true on the button press.
	 */
	public boolean getDown(boolean state){
		if(lastPress == false && state == true){
			lastPress = true;
			return true;
		}
		lastPress = state;
		return false;
	}
	
	/**
	 * @return The state of the toggled button.
	 */
	public boolean getState(){
		return toggle;
	}
	
}
