package org.usfirst.frc.team4738;

import org.usfirst.frc.team4738.wrapper.Encoder;
import org.usfirst.frc.team4738.wrapper.Timer;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	VictorSP motor1, motor2, motor3, motor4;
	Encoder encoder1, encoder2, encoder3, encoder4;
	Timer timer;
	
	//this is what we're using to test the code 
	public void robotInit() {
		
		motor1 = new VictorSP(0);
		motor2 = new VictorSP(1);
		motor3 = new VictorSP(2);
		motor4 = new VictorSP(3);
		
		//Change the values for port 2, and maybe soon port 1 if they rewire it
		encoder1 = new Encoder(6, 7, 8);
		encoder2 = new Encoder(0, 1, 8);
		encoder3 = new Encoder(2, 3, 8);
		encoder4 = new Encoder(4, 5, 8);
		
		timer.start();
		timer.reset();
		
	}

	public void autonomousInit() {
		
	}

	
	public void autonomousPeriodic() {
		
	}

	//arrays are stupid
	//Init the maximum motor speed, is changed when code below is run
	double motor1Max = 0, motor2Max = 0, motor3Max = 0, motor4Max = 0;
	
	public void teleopPeriodic() {
		
		if (timer.getTime() < 15000) {
			motor1.setSpeed(1);
			motor2.setSpeed(1);
			motor3.setSpeed(1);
			motor4.setSpeed(1);
		} else {
			motor1.setSpeed(-1);
			motor2.setSpeed(-1);
			motor3.setSpeed(-1);
			motor4.setSpeed(-1);
		}
		
		//check encoder value, if || > motorMax, set motorMax = encoder value
		if (Math.abs(encoder1.getSpeed()) > motor1Max){
			motor1Max = Math.abs(encoder1.getSpeed());
			SmartDashboard.putNumber("Motor1", motor1Max);
		}
		
		if (Math.abs(encoder1.getSpeed()) > motor2Max){
			motor2Max = Math.abs(encoder2.getSpeed());
			SmartDashboard.putNumber("Motor2", motor2Max);
		}
	
		if (Math.abs(encoder1.getSpeed()) > motor3Max){
			motor3Max = Math.abs(encoder3.getSpeed());
			SmartDashboard.putNumber("Motor3", motor3Max);
		}
		
		if (Math.abs(encoder1.getSpeed()) > motor4Max){
			motor4Max = Math.abs(encoder4.getSpeed());
			SmartDashboard.putNumber("Motor4", motor4Max);
		}

		}

	public void testPeriodic() {
	}
}