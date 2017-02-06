package org.usfirst.frc.team4738.util.pid_util;

import java.util.Timer;

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
	 double lastDiff;
	 double currentSlope;
	 double totalArea;
	 long tickrate;
	 
	 private static final Timer timer = new Timer();
	 
	 public PID(double Kp, double Ki, double Kd, long tickrate, ErrorRetriever err){
		 this.Kp = Kp;
		 this.Ki = Ki;
		 this.Kd = Kd;
		 this.lastDiff = 0;
		 this.totalArea = 0;
		 this.tickrate = tickrate;
		 
		 timer.schedule(new PIDUpdate(this, err), 0L, tickrate);
	 }
 
	//This function is DONE!!!!!!!!!!
	public double CalculateP(double SP, double PV){
		double et = SP - PV;
		return Kp*et;
	}
	
	public double CalculateI(){
		return Ki * totalArea;
	}
	
	public double CalculateD(){
		return currentSlope * Kp;
	}
	
	public double getPID(double SP, double PV){

		return CalculateP(SP, PV) + 
				CalculateI() + 
				CalculateD();
	}

	public void update(double e) {
		currentSlope = (e - lastDiff) / (tickrate * 1000);
		totalArea += ((lastDiff+e)/2) * (tickrate / 1000);
		
		lastDiff = e;
	}
	
}
