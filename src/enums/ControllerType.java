package enums;

/**
 * @author Ghjf544912
 */
public enum ControllerType {
	GP(0), Xbox(10), Attack3(11), X3D(12);
	
	private int buttonCount = 0;
	
	private ControllerType(int buttonCount){
		this.buttonCount = buttonCount;
	}
	
	public int getButtonCount(){
		return buttonCount;
	}
}
