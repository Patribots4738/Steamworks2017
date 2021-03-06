package org.usfirst.frc.team4738.robot;

import org.usfirst.frc.team4738.wrapper.Encoder;
import org.usfirst.frc.team4738.wrapper.Gyro;
import org.usfirst.frc.team4738.wrapper.PIDMecanumDrive;
import org.usfirst.frc.team4738.wrapper.Timer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {

	public Gyro gyro;
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
		if (order == posInOrder) {
			// There must be an error here as the robot goes at full speed
			// regardless
			drive.linearMecanum(0, speed, 0);
			System.out.println(Math.abs(encoder.getDistance()));
			if (Math.abs(encoder.getDistance()) > inches) {
				posInOrder++;
				drive.linearMecanum(0, 0, 0);
				encoder.reset();
			}
		}
	}

	public void move(double inches, double speed, int order) {
		if (order == posInOrder) {
			if (!hasStarted) {
				drive.reset();
				hasStarted = true;
			}
			// There must be an error here as the robot goes at full speed
			// regardless
			// We have to normalize the inches - encoder.getDistance so that the
			// robot's speed doesnt end up being multiplied
			// by some crazy value like 4
			// drive.linearMecanum(0, (speed * (inches -
			// encoder.getDistance())), 0);
			speed = (speed * (inches-encoder.getDistance()) / inches) + .15;
			drive.linearMecanum(0, speed, 0);

			SmartDashboard.putNumber("move Speed", (speed * (inches-encoder.getDistance()) / inches) + .15);
			System.out.println(Math.abs(encoder.getDistance()));
			if (Math.abs(encoder.getDistance()) > inches) {
				posInOrder++;
				// drive.linearMecanum(1, 0, 0);
				encoder.reset();
				hasStarted = false;
			}
		}
	}

	boolean hasStarted = false;
	public void rotate(double degrees, double speed, int order) {
		if (order == posInOrder) {
			if(!hasStarted){
				gyro.reset();
				System.out.println("Reset Gyros");
				hasStarted = true;
			}
			drive.linearMecanum(0, 0, speed);
			if (Math.abs(gyro.gyro.getAngle()) >= (degrees)) {
				drive.linearMecanum(0, 0, 0);
				hasStarted = false;
				posInOrder++;
			}
			/*
			 * if(gyro.getAngle() > degrees && gyro.getAngle() < degrees + 5){
			 * posInOrder++; drive.linearMecanum(0, 0, 0); gyro.reset(); }
			 */
		}
	}

	public void stop(int order) {
		if (order == posInOrder) {
			boolean isStopped = drive.motors[0].encoder.getSpeed() == 0 && drive.motors[1].encoder.getSpeed() == 0
					&& drive.motors[2].encoder.getSpeed() == 0 && drive.motors[3].encoder.getSpeed() == 0;

			drive.linearMecanum(0, 0, 0);
			if (isStopped) {
				encoder.reset();
				posInOrder++;
			}
		}
	}

	public void reset() {
		posInOrder = 0;
		drive.reset();
		gyro.reset();
	}

	public void setArms(boolean state, int order) {
		if (order == posInOrder) {
			arms.servoState(state);
			posInOrder++;
		}
	}

	public void setKicker(boolean state, int order) {
		if (order == posInOrder) {
			kicker.servoState(state);
			posInOrder++;
		}
	}

	public void timedWait(double seconds, int order) {
		if (order == posInOrder) {
			timer.start();
			if (timer.wait(seconds)) {
				posInOrder++;
				timer.stop();
			}
		}
	}

	public void autonomousChooser(int autoNum) {
		switch (autoNum) {

		case -1:
			double dist = SmartDashboard.getNumber("TestDist", 100);
			move(dist, 0.25, 0);
			break;
		
		// Middle of field
		case 0:

			/*
			 * move(93, .5, 0); move( 1, .2, 1); //This is manual softening code
			 * stop(2); timedWait(3000, 3);
			 */
			setArms(true, 0);
			timedWait(300, 1);
			setKicker(true, 2);
			timedWait(3000, 3);
			setArms(false, 4);
			setKicker(false, 5);

			/*
			 * move(93, -.5, 5); stop(6); //degrees may need to be 10 less than
			 * the actual value due to drift rotate(60, .5, 7); move(45, .5, 8);
			 * stop(9);
			 */
			break;

		// When the robot is on the right side of the field
		case 1:
			move(105, .5, 0);
			stop(1);
			rotate(60, .5, 2);
			stop(3);
			move(54, .5, 4);
			stop(5);
			timedWait(1.5, 6);
			setArms(true, 7);
			timedWait(.5, 8);
			setKicker(true, 9);
			move(-54, .5, 10);
			stop(11);
			// degrees may need to be 10 less than the actual value due to drift
			rotate(-60, .5, 12);
			move(86, .5, 13);
			stop(14);
			break;

		// When the robot is on the left side of the field
		case 2:
			move(105, .75, 0);
			stop(1);
			rotate(-60, .5, 2);
			stop(3);
			move(54, .75, 4);
			stop(5);
			timedWait(1.5, 6);
			setArms(true, 7);
			timedWait(.5, 8);
			setKicker(true, 9);
			timedWait(1, 10);
			move(-54, .75, 11);
			stop(12);
			// degrees may need to be 10 less than the actual value due to drift
			rotate(60, .5, 13);
			move(86, .75, 14);
			stop(15);
			break;

		// middle gear autonomous
		// 1 unit of distance == 5.5 actual distance
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
			break;

		case 4:
			// 29.5 - 103in
			// 38 crosses base line, 76 for halfway across the field
			move(74, .25, 0);
			stop(1);
			break;

		case 5:
			move(65, -0.25, 0);
			stop(1);
			rotate(1, 0.25, 2);
			move(-57, 0.25, 3);
			stop(4);
			break;

		}
	}
}