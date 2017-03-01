package org.usfirst.frc.team4738.wrapper;

import org.usfirst.frc.team4738.utils.MovingAverage;

import edu.wpi.first.wpilibj.AnalogGyro;

public class Gyro {	
	 public AnalogGyro gyro;
	 MovingAverage average;
	 
	public Gyro(int port){
		gyro = new AnalogGyro(port);
		average = new MovingAverage(5);
	}
	
	public double getAngle(){
		double angle = gyro.getAngle();
		angle = (Math.abs(angle / 360) - (int)(Math.abs(angle / 360)));
		return  average.average(angle * 360);
	}
	
	public void reset(){
		gyro.reset();
	}
}
