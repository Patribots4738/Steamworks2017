package wrapper;

/**
 * 
 * @author Sean, Anthony, Alec
 *
 */

public class PID {
	
	double errSum = 0;
	double lastErr = 0;
	double Kp, Ki, Kd;
	
	Timer timer;
	
	//Information about variables & stuff can be found on the PID controller Wiki
	public PID(double Kp, double Ki, double Kd){
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		timer = new Timer();
		timer.start();
	}
	
	public double calcP(double SP, double PV){
		double et = SP-PV;
		return Kp*et;
	}
	
	public double calcI(double SP, double PV, double deltaTime){
		double et = SP-PV;
		errSum += (et*deltaTime);
		return Ki * errSum;
	}
	
	public double calcD(double SP, double PV, double deltaTime){
		double et = SP-PV;
		double dErr = (et - lastErr) / deltaTime;
		lastErr = et;
		return Kd * dErr;
	}
	
	//This updates the PID
	public double calcPID(double SP, double PV){
		double deltaTime = timer.getDeltaTime();
		return calcP(SP, PV) + calcI(SP, PV, deltaTime) + calcD(SP, PV, deltaTime);
	}
	
}