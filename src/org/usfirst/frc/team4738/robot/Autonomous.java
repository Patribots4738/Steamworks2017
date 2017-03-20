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
	MultiServo arms;
	MultiServo kicker;
	
	public int posInOrder = 0;
	
	public Autonomous(PIDMecanumDrive drive, Gyro gyro, MultiServo arms, MultiServo kicker) {
		this.drive = drive;
		this.gyro = gyro;
		this.arms = arms;
		this.kicker = kicker;
		encoder = drive.motors[1].encoder;
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
			//We have to normalize the inches - encoder.getDistance so that the robot's speed doesnt end up being multiplied
			//by some crazy value like 4
			//drive.linearMecanum(0, (speed * (inches - encoder.getDistance())), 0);
			drive.linearMecanum(0, speed, 0);
			
			SmartDashboard.putString("move Speed", "" + speed * (inches - encoder.getDistance()));
			System.out.println(Math.abs(encoder.getDistance()));
			if(Math.abs(encoder.getDistance()) > inches){
				posInOrder++;
				//drive.linearMecanum(1, 0, 0);
				encoder.reset();
			}
		}
	}
	
	public void rotate(double degrees, double speed, int order) {		
		if(order == posInOrder){			
			drive.linearMecanum(0, 0, speed);
			gyro.reset();
			if (gyro.getAngle() >= degrees){
				drive.linearMecanum(0,  0,  0);
			}
		/*	if(gyro.getAngle() > degrees && gyro.getAngle() < degrees + 5){
				posInOrder++;
				drive.linearMecanum(0, 0, 0);
				gyro.reset();
			}*/
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
			arms.servoState(state);
			posInOrder++;
		}
	}
	
	public void setKicker(boolean state, int order){
		if(order == posInOrder){
			kicker.servoState(state);
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
<<<<<<< HEAD
		//SET SPEED VALUES: movement: .75, rotation: .5
		//When robot is in middle of field
		case 0:
			
			/*move(93, .5, 0);
			move( 1, .2, 1); //This is manual softening code
			stop(2);
			timedWait(3000, 3);
			*/
			setArms(true, 0);
			timedWait(300, 1);
			setKicker(true, 2);
			timedWait(3000, 3);
			setArms(false, 4);
			setKicker(false, 5);
			
			/*
			move(93, -.5, 5);
			stop(6);
			//degrees may need to be 10 less than the actual value due to drift
			rotate(60, .5, 7);
			move(45, .5, 8);
			stop(9);
			*/
=======
		
		//SET SPEED VALUES: movement: .75, rotation: .5
		//When robot is in middle of field
		case 0:
			move(93, .5, 0);
			move( 1, .2, 1); //This is manual softening code
			stop(2);
			timedWait(3000, 3);
			// setArms(true, 3);
			move(93, -.5, 4);
			stop(5);
			//degrees may need to be 10 less than the actual value due to drift
			rotate(60, .5, 6);
			move(45, .5, 7);
			stop(8);
>>>>>>> 2180ce9854ca5b36b8445aadbf40ac7df2603b2e
		break;
		
		//When the robot is on the right side of the field
		case 1:
			move(105, .5, 0);
			stop(1);
			rotate(60, .5, 2);
			stop(3);
			move(54, .5, 4);
			stop(5);
			timedWait(1.5, 6);
<<<<<<< HEAD
			setArms(true, 7);
			timedWait(.5, 8);
			setKicker(true, 9);
			move(-54, .5, 10);
			stop(11);
			//degrees may need to be 10 less than the actual value due to drift
			rotate(-60, .5, 12);
			move(86, .5, 13);
			stop(14);
=======
			//setArms(true, 7);
			timedWait(.5, 8);
			//setKicker(true, 9);
			move(-54, .5, 10);
			stop(9);
			//degrees may need to be 10 less than the actual value due to drift
			rotate(-60, .5, 11);
			move(86, .5, 12);
			stop(13);
>>>>>>> 2180ce9854ca5b36b8445aadbf40ac7df2603b2e
			
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
<<<<<<< HEAD
			setArms(true, 7);
			timedWait(.5, 8);
			setKicker(true, 9);
=======
			//setArms(true, 7);
			timedWait(.5, 8);
			//setKicker(true, 9);
>>>>>>> 2180ce9854ca5b36b8445aadbf40ac7df2603b2e
			timedWait(1, 10);
			move(-54, .75, 11);
			stop(12);
			//degrees may need to be 10 less than the actual value due to drift
			rotate(60, .5, 13);
			move(86, .75, 14);
<<<<<<< HEAD
			stop(15);
=======
			stop(14);
>>>>>>> 2180ce9854ca5b36b8445aadbf40ac7df2603b2e
			
		break;
		
		//middle gear autonomous
		//27 wasn't long enough, 29.5 may be right
		case 3:
			move(29.5, -0.25, 0);
			stop(1);
			setArms(true, 2);
			timedWait(800, 3);
			setKicker(true, 4);
			timedWait(1000, 5);
			move(13, 0.25, 6);
			stop(7);
			setKicker(false, 8);
			setArms(false, 9);
			
		case 4:
			//29.5 - 103in
			//38 crosses base line, 76 for halfway across the field
			move(65, -.25, 0);
			stop(1);

		}
	}
}