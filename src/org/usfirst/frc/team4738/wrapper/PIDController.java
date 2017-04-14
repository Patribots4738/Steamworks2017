package org.usfirst.frc.team4738.wrapper;

/**
 * @author Sean, Anthony, Alec
 */

public class PIDController {
	
	double errSum = 0;
	double lastErr = 0;
	double Kp, Ki, Kd;
	
	Timer timer;
	
	public double deltaTime;
	
	//Information about variables & stuff can be found on the PID controller Wiki
	public PIDController(double Kp, double Ki, double Kd){
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		timer = new Timer();
		timer.start();
	}
	
	/**
	 * @param SP The destination point.
	 * @param PV The current point.
	 * @return The proportional aspect of the PID
	 */
	public double calcP(double SP, double PV){
		double et = SP-PV;
		return Kp*et;
	
	}
	
	/**
	 * @param SP The destination point.
	 * @param PV The current point.
	 * @return The integral aspect of the PID
	 */
	public double calcI(double SP, double PV, double deltaTime){
		double et = SP-PV;
		errSum += (et*deltaTime);
		return Ki * errSum;
	}
	
	/**
	 * @param SP The destination point.
	 * @param PV The current point.
	 * @return The derivative aspect of the PID
	 */
	public double calcD(double SP, double PV, double deltaTime){
		double et = SP-PV;
		double dErr = (et - lastErr) / deltaTime;
		lastErr = et;
		return Kd * dErr;
	}
	
	/**
	 * @param SP The destination point.
	 * @param PV The current point
	 * @return The output PID value.
	 * Note: This updates the PID data.
	 */
	public double calcPID(double SP, double PV){
		deltaTime = timer.getDeltaTime() / 1000;
		return calcP(SP, PV) + calcI(SP, PV, deltaTime) + calcD(SP, PV, deltaTime);
	}
	
	/**
	 * @param Kp The proportional constant
	 * @param Ki The integral constant
	 * @param Kd The derivative constant
	 */
	public void setPIDConstants(double Kp, double Ki, double Kd){
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
	}
	
}