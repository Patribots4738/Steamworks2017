package org.usfirst.frc.team4738.robot;

import org.usfirst.frc.team4738.wrapper.Gamepad;
import org.usfirst.frc.team4738.wrapper.Gyro;
import org.usfirst.frc.team4738.wrapper.PIDMecanumDrive;
import org.usfirst.frc.team4738.wrapper.PIDVictorSP;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	PIDVictorSP[] motors;
	Gyro gyro;
	PIDMecanumDrive drive;
	
	Gamepad pad;
	
	//this is what we're using to test the code 
	public void robotInit() {
		
		gyro = new Gyro(1);
		drive = new PIDMecanumDrive(4, 0, 0, 0, 0, 1, 2, 3);
		pad = new Gamepad(0);
		//pad = new XboxController(0);
		//cam = new Camera(1);
		//cam.startCamera();
	}
	
	public void autonomousInit() {
	}

	public void autonomousPeriodic() {
	}

	public void teleopPeriodic() {
		drive.parabolicMecanum(pad.getAxis(0), pad.getAxis(1), 0, 18);
		
		SmartDashboard.putString("Gyro angle", "" + gyro.getAngle());
	}
		
	public void testPeriodic() {
	}
}