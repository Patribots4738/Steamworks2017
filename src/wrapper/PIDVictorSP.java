package wrapper;

import edu.wpi.first.wpilibj.VictorSP;

public class PIDVictorSP {
	
	VictorSP victor;
	PID pid;
	Encoder encoder;
	
	public PIDVictorSP(int victorPort, int encoderA, int encoderB, double radius, double Kp, double Ki, double Kd){
		victor = new VictorSP(victorPort);
		encoder = new Encoder(encoderA, encoderB, radius);
		pid = new PID(Kp, Ki, Kd);
	}
	
	public void set(double speed){
		victor.set(speed+pid.calcPID(speed, encoder.getSpeed() / Constants.TOP_SPEED));
		
	}

}
