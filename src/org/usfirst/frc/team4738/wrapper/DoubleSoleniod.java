package org.usfirst.frc.team4738.wrapper;

/**
 * Senpai says hi! -Stephen
 */
public class DoubleSoleniod extends edu.wpi.first.wpilibj.DoubleSolenoid{
	
	ToggleButton toggledState = new ToggleButton();
	ToggleButton holdButton = new ToggleButton();
	
	/**
	 * @param moduleNumber
	 * @param forwardChannel
	 * @param reverseChannel
	 */
	public DoubleSoleniod(int moduleNumber, int forwardChannel, int reverseChannel) {
		super(moduleNumber, forwardChannel, reverseChannel);
	}
	
	/**
	 * @param forwardChannel
	 * @param reverseChannel
	 */
	public DoubleSoleniod(int forwardChannel, int reverseChannel) {
		super(forwardChannel, reverseChannel);
	}

	public void onHold(boolean state){
		 if(holdButton.getDown(state)){
			 this.set(Value.kForward);
		 }
		 else if(holdButton.getUp(state)){
			 this.set(Value.kReverse);
		 }
	}
	
	public void toggleDirection(boolean state){
		if(toggledState.getDown(state)){
			if(toggledState.getDownToggle(state)){
				this.set(Value.kForward);
			} 
			else{
				this.set(Value.kReverse);
			}
		}
	}
	
}