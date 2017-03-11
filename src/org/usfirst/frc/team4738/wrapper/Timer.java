package org.usfirst.frc.team4738.wrapper;

/**
 * @author Owen maybe
 */
public class Timer {
	
	double deltaTime = 0, lastTime = 0, startTime;
	
	boolean started = false;
	
	public void start() {
		if(!started){
			startTime = System.currentTimeMillis();
			started = true;
		}
	}
	
	public void stop(){
		started = false;
		reset();
	}
	
	/**
	 * @return The current time on the timer.
	 */
	public double getTime(){
		return System.currentTimeMillis() - startTime;
	}
	
	/**
	 * @return The change in time.
	 */
	public double getDeltaTime(){
		updateDeltaTime();
		return deltaTime;
	}
	
	/**
	 * @author Ghjf544912
	 */
	public void updateDeltaTime(){
		deltaTime = getTime() - lastTime;
		lastTime = getTime();
	}
	
	public void reset(){
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * @param waitTime Time to wait.
	 * @return Returns true if it's still waiting, false if the wait period is over.
	 */
	public boolean wait(double waitTime){
		if (waitTime <= getTime())
			return true;
		return false;
	}
	
}