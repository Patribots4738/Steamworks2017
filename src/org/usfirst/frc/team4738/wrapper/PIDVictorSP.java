package org.usfirst.frc.team4738.wrapper;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDVictorSP {
	
	VictorSP victor;
	PID pid;
	public Encoder encoder;
	int port;
	
	public PIDVictorSP(int victorPort, int encoderA, int encoderB, double radius, double Kp, double Ki, double Kd){
		port = victorPort;
		victor = new VictorSP(victorPort);
		encoder = new Encoder(encoderA, encoderB, radius);
		pid = new PID(Kp, Ki, Kd);
	}
	
	public void set(double speed){
		double pidVal = pid.calcPID(speed, encoder.getSpeed() / Constants.TOP_SPEED);
		victor.set(speed+pidVal);
		SmartDashboard.putNumber("pidval " + port, pidVal);
		//SmartDashboard.putString("sped", "Set Speed " + speed + " Encoder Speed " + encoder.getSpeed() / Constants.TOP_SPEED);
	}
	
	public void setPID(double Kp, double Ki, double Kd){
		pid.setPIDConstants(Kp, Ki, Kd);
	}
	
	public void soften(double currentSpeed, double deltaX, double deltaY, double acceptAccel){
		
	}
	//SOFTENING CODE -- PRIORITIZE
	//slow down when turning, don' go from 100% speed forward to 100% speed backwards
	
	/*public void soften(double currentSpeed, double deltaX, double deltaY){
		
	currentSpeed = encoder.getSpeed();
	
	if(Math.abs(currentSpeed))
		
	}*/
}
