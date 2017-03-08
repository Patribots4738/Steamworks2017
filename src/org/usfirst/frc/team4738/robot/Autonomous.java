package org.usfirst.frc.team4738.robot;

import org.usfirst.frc.team4738.wrapper.Encoder;
import org.usfirst.frc.team4738.wrapper.Gyro;
import org.usfirst.frc.team4738.wrapper.PIDMecanumDrive;
import org.usfirst.frc.team4738.wrapper.Timer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous{
	
	Gyro gyro;
	Encoder encoder;
	PIDMecanumDrive drive;
	Timer timer;
	Arms arms;
	Kicker kicker;
	
	public int posInOrder = 0;
	
	public Autonomous(PIDMecanumDrive drive, Gyro gyro, Arms arms, Kicker kicker) {
		this.drive = drive;
		this.gyro = gyro;
		encoder = drive.motors[0].encoder;
		timer = new Timer();
	}
	
	public void stupidMove(double inches, double speed, int order) {
		if(order == posInOrder){
			//There must be an error here as the robot goes at full speed regardless
			drive.linearMecanum(0, speed, 0);
			System.out.println(Math.abs(encoder.getDistance()));
			if(Math.abs(encoder.getDistance()) > inches){
				posInOrder++;
				drive.linearMecanum(0, 0, 0);
				encoder.reset();
			}
		}
	}
	
	public void move(double inches, double speed, int order) {
		if(order == posInOrder){
			//There must be an error here as the robot goes at full speed regardless
			drive.linearMecanum(0, (speed * (inches - encoder.getDistance())), 0);
			
			SmartDashboard.putString("move Speed", "" + speed * (inches - encoder.getDistance()));
			System.out.println(Math.abs(encoder.getDistance()));
			if(Math.abs(encoder.getDistance()) > inches){
				posInOrder++;
				drive.linearMecanum(1, 0, 0);
				encoder.reset();
			}
		}
	}
	
	public void rotate(double degrees, double speed, int order) {		
		if(order == posInOrder){			
			drive.linearMecanum(0, 0, speed);
			if(gyro.getAngle() > degrees && gyro.getAngle() < degrees + 5){
				posInOrder++;
				drive.linearMecanum(0, 0, 0);
				gyro.reset();
			}
		}
	}
	
	public void stop(int order){
		if(order == posInOrder){
			boolean isStopped = 
					drive.motors[0].encoder.getSpeed() == 0 &&
					drive.motors[1].encoder.getSpeed() == 0 &&
					drive.motors[2].encoder.getSpeed() == 0 &&
					drive.motors[3].encoder.getSpeed() == 0;
			
			
			drive.linearMecanum(0, 0, 0);
			if(isStopped){
				encoder.reset();
				posInOrder++;
			}
		}
	}
	
	public void reset(){
		posInOrder = 0;
		gyro.reset();
	}
	
	public void setArms(boolean state, int order){
		if(order == posInOrder){
			arms.openArms(state);
			posInOrder++;
		}
	}
	
	public void setKicker(boolean state, int order){
		if(order == posInOrder){
			kicker.openKicker(state);
			posInOrder++;
		}
	}
	
	public void timedWait(double seconds, int order){
		if(order == posInOrder){
			timer.start();
			if (timer.wait(seconds)){
				posInOrder++;
				timer.stop();
			}
		}
	}
	
	public void autonomousChooser(int autoNum){
		switch (autoNum) {
		
		//SET SPEED VALUES: movement: .75, rotation: .5
		//When robot is in middle of field
		case 0:
			move(93, .25, 0);
			stop(1);
			timedWait(3000, 2);
			// setArms(true, 3);
			move(93, -.25, 4);
			stop(5);
			//degrees may need to be 10 less than the actual value due to drift
			rotate(60, .25, 6);
			move(45, .25, 7);
			stop(8);
		break;
		
		//When the robot is on the right side of the field
		case 1:
			move(105, .75, 0);
			stop(1);
			rotate(60, .5, 2);
			stop(3);
			move(54, .75, 4);
			stop(5);
			timedWait(1.5, 6);
			//setArms(true, 7);
			timedWait(.5, 8);
			//setKicker(true, 9);
			move(-54, .75, 10);
			stop(9);
			//degrees may need to be 10 less than the actual value due to drift
			rotate(-60, .5, 11);
			move(86, .75, 12);
			stop(13);
			
		break;
		
		//When the robot is on the left side of the field
		case 2:
			move(105, .75, 0);
			stop(1);
			rotate(-60, .5, 2);
			stop(3);
			move(54, .75, 4);
			stop(5);
			timedWait(1.5, 6);
			//setArms(true, 7);
			timedWait(.5, 8);
			//setKicker(true, 9);
			timedWait(1, 10);
			move(-54, .75, 11);
			stop(12);
			//degrees may need to be 10 less than the actual value due to drift
			rotate(60, .5, 13);
			move(86, .75, 14);
			stop(14);
			
		break;
		}
	}
}