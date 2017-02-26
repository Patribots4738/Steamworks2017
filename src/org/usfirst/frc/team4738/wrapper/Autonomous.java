package org.usfirst.frc.team4738.robot;

import org.usfirst.frc.team4738.wrapper.Encoder;
import org.usfirst.frc.team4738.wrapper.Gyro;
import org.usfirst.frc.team4738.wrapper.PIDMecanumDrive;
import org.usfirst.frc.team4738.wrapper.Timer;

public class Autonomous{
	
	Gyro gyro;
	Encoder encoder;
	PIDMecanumDrive drive;
	Timer timer;
	
	int posInOrder = 0;
	
	public Autonomous(PIDMecanumDrive drive, Gyro gyro) {
		this.drive = drive;
		this.gyro = gyro;
		encoder = drive.motors[0].encoder;
		Timer timer = new Timer();
	}
	
	public void move(double inches, double speed, int order) {
		if(order == posInOrder){
			drive.linearMecanum(0, speed, 0);
			System.out.println(Math.abs(encoder.getDistance()));
			if(Math.abs(encoder.getDistance()) > inches){
				posInOrder++;
				drive.linearMecanum(0, 0, 0);
				encoder.reset();
			}
		}
	}
	
	public void rotate(double degrees, double speed, int order) {		
		if(order == posInOrder){
			drive.linearMecanum(0, 0, speed);
			if(gyro.getAngle() > degrees){
				posInOrder++;
				drive.linearMecanum(0, 0, 0);
				gyro.reset();
			}
		}
	}
	
	public void stop(double waitTime, int order){
		if(order == posInOrder){
			drive.linearMecanum(0, 0, 0, 16);
		}
	}
	
	public void reset(){
		posInOrder = 0;
	}
}
