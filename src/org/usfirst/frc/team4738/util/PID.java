package org.usfirst.frc.team4738.util;

public class PID {

	/*
	 * Create a class the uses calculates and outputs the P and I for the controller
	 * 
	 * The class needs:
	 * a constant for Kp, Ki, Kd DONE
	 * These are used to tune the controller, go look it up DONE
	 * 
	 * Create a constructor that sets the constants Kp, Ki, Kd.
	 * 
	 * double CalculateP(double Input) The main horsepower of the controller make this first then test it
	 * double CalculateI(double Input) After your P works figure out how to write P
	 * CalculateD() <- Doesn't do anything RN
	 * 
	 * void Update()
	 * 
	 * That's all for now
	 */
	 double Kp, Ki, Kd;
	 public PID(double Kp, double Ki, double Kd){
		 this.Kp = Kp;
		 this.Ki = Ki;
		 this.Kd = Kd;
	 }
 
	//This function is DONE!!!!!!!!!!
	public double CalculateP(double SP, double PV, double p0){
		double et = SP - PV;
		return Kp*et+p0;
	}
	
	public double CalculateI(){
		double Iout = 0;
		return Iout;
	}
	
	public double CalculateD(){
		double Dout = 0;
		return Dout;
	}
	
	public double getPID(double SP, double PV, double p0){
		return CalculateP(SP, PV, p0) + CalculateI() + CalculateD();
	}
}
