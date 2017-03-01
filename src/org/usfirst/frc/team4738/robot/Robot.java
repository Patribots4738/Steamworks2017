package org.usfirst.frc.team4738.robot;

import java.io.File;

import org.usfirst.frc.team4738.wrapper.Gamepad;
import org.usfirst.frc.team4738.wrapper.Gyro;
import org.usfirst.frc.team4738.wrapper.PIDMecanumDrive;
import org.usfirst.frc.team4738.wrapper.PIDVictorSP;
import org.usfirst.frc.team4738.wrapper.XboxController;
import org.usfirst.frc.team4738.wrapper.vision.Camera;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	//Cheeki Breeki
	PIDVictorSP[] motors;
	VictorSP wench;
	Gyro gyro;
	PIDMecanumDrive drive;
	Autonomous autoDrive;
	// Camera cam;

	XboxController xbox;
	Gamepad pad;
	Arms arms;
	Kicker kicker;

	public void robotInit() {
		//arms = new Arms(0, 1);
		//kicker = new Kicker(2, 3);
		arms = new Arms(4);
		kicker = new Kicker(5);
		gyro = new Gyro(0);
		
		motors = new PIDVictorSP[4];
		/*motors[0] = new PIDVictorSP(7, 2, 3, 3, 0, 0, 0); // front left
		motors[1] = new PIDVictorSP(8, 6, 7, 3, 0, 0, 0);// back left
		motors[2] = new PIDVictorSP(6, 0, 1, 3, 0, 0, 0);// front right
		motors[3] = new PIDVictorSP(9, 4, 5, 3, 0, 0, 0);// back right
		drive = new PIDMecanumDrive(3, 0, 0, 0, motors);*/
		drive = new PIDMecanumDrive(4, 0, 0, 0, 0, 1, 2, 3);
		xbox = new XboxController(0);
		pad = new Gamepad(1);
		//wench = new VictorSP(5);
		// pad = new XboxController(0);
		Camera cam = new Camera();
		//cam.startCamera();
		autoDrive = new Autonomous(drive, gyro, arms, kicker);
		
		SmartDashboard.putNumber("Gyro", gyro.getAngle());
	}

	public void autonomousInit() {
		autoDrive.reset(); 
	}

	public void autonomousPeriodic() {
		//autoDrive.autonomousChooser(0);
		//autoDrive.move(93, .35, 0);
		autoDrive.move(70, .25, 0);
		autoDrive.stop(1);
		autoDrive.rotate(170, -.2, 2);
		autoDrive.stop(3);
		autoDrive.move(70, .25, 4);
		autoDrive.stop(5);
		autoDrive.rotate(170, -.2, 6);
		autoDrive.stop(7);
		
		//autoDrive.rotate(15, 0.25, 0);
		//autoDrive.rotate(60, -0, 0);
		
		SmartDashboard.putNumber("PosInOrder", autoDrive.posInOrder);
		SmartDashboard.putNumber("Gyro", gyro.getAngle());
	}

	public void teleopPeriodic() {
		drive.parabolicMecanum((xbox.getAxis(0)), -xbox.getAxis(1), -xbox.getAxis(4), 12);

		wench.set(pad.getAxis(1));

		arms.openArms(pad.getButton(1));
		kicker.openKicker(pad.getButton(0));

		SmartDashboard.putString("Gyro", "" + gyro.getAngle());
		// cam.camUpdate();
	}

	public void testPeriodic() {
	}
}