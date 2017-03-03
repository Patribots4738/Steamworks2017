package org.usfirst.frc.team4738.wrapper;

import org.usfirst.frc.team4738.utils.Mathd;

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
		
		SmartDashboard.putNumber("speed + pidval " + port, speed+pidVal);
		SmartDashboard.putNumber("pidval " + port, pidVal);
		SmartDashboard.putNumber("speed " + port, speed);
		//SmartDashboard.putString("sped", "Set Speed " + speed + " Encoder Speed " + encoder.getSpeed() / Constants.TOP_SPEED);
	}
	
	public void cerpSet(double speed, double dampening){
		set(Mathd.cerp(encoder.getSpeed() / Constants.TOP_SPEED, speed, dampening * pid.deltaTime));
	}
	
	public void setPID(double Kp, double Ki, double Kd){
		pid.setPIDConstants(Kp, Ki, Kd);
	}
}
