package org.usfirst.frc.team4738.robot;

import org.usfirst.frc.team4738.wrapper.Encoder;
import org.usfirst.frc.team4738.wrapper.Gamepad;
import org.usfirst.frc.team4738.wrapper.Gyro;
import org.usfirst.frc.team4738.wrapper.PIDMecanumDrive;
import org.usfirst.frc.team4738.wrapper.PIDVictorSP;
import org.usfirst.frc.team4738.wrapper.Timer;
import org.usfirst.frc.team4738.wrapper.XboxController;
//import org.usfirst.frc.team4738.wrapper.vision.Camera;

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
	Encoder encoder1, encoder2, encoder3, encoder4;
	Timer timer;
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
		
		/*
		motors = new PIDVictorSP[4];
		motors[0] = new PIDVictorSP(7, 2, 3, 3, 0, 0, 0); // front left
		motors[1] = new PIDVictorSP(8, 6, 7, 3, 0, 0, 0);// back left
		motors[2] = new PIDVictorSP(6, 0, 1, 3, 0, 0, 0);// front right
		motors[3] = new PIDVictorSP(9, 4, 5, 3, 0, 0, 0);// back right
		*/
		//drive = new PIDMecanumDrive(3, 0, 0, 0, motors);
		/*encoder1 = new Encoder(0, 1, 4);
		encoder2 = new Encoder(2, 3, 4);
		encoder3 = new Encoder(4, 5, 4);
		encoder4 = new Encoder(6, 7, 4);*/

		drive = new PIDMecanumDrive(4, 1, .04, 0, 0, 1, 2, 3);
		xbox = new XboxController(0);
		pad = new Gamepad(1);
		//wench = new VictorSP(5);
		// pad = new XboxController(0);
		//Camera cam = new Camera();      <------- All camera code was commented out. Giving error for visionObject
		//cam.startCamera();
		autoDrive = new Autonomous(drive, gyro, arms, kicker);
		
		SmartDashboard.putNumber("Gyro", gyro.getAngle());
	}

	public void autonomousInit() {
		autoDrive.reset(); 
	}

	public void autonomousPeriodic() {
		timer.start();
		//autoDrive.autonomousChooser(0);
		autoDrive.autonomousChooser(0);
		while (this.isAutonomous()){
		if (timer.getDeltaTime() > 15000){
			break;
			}
		}
		/*
		autoDrive.move(70, .25, 0);
		autoDrive.stop(1);
		autoDrive.rotate(170, -.2, 2);
		autoDrive.stop(3);
		autoDrive.move(70, .25, 4);
		autoDrive.stop(5);
		autoDrive.rotate(170, -.2, 6);
		autoDrive.stop(7);*/
		
		//autoDrive.rotate(15, 0.25, 0);
		//autoDrive.rotate(60, -0, 0);
		
		SmartDashboard.putNumber("PosInOrder", autoDrive.posInOrder);
		SmartDashboard.putNumber("Gyro", gyro.getAngle());
	}

	public void teleopPeriodic() {
		
		//drive.parabolicMecanum((xbox.getAxis(0)), -xbox.getAxis(1), -xbox.getAxis(4), 5);
		drive.parabolicMecanum(xbox.getAxis(0), -xbox.getAxis(1), -xbox.getAxis(4));
		
		SmartDashboard.putString("Speed 0", "" + drive.motors[0].encoder.getSpeed());
		SmartDashboard.putString("Speed 1", "" + drive.motors[1].encoder.getSpeed());
		SmartDashboard.putString("Speed 2", "" + drive.motors[2].encoder.getSpeed());
		SmartDashboard.putString("Speed 3", "" + drive.motors[3].encoder.getSpeed());
		//wench.set(pad.getAxis(1));
		
		//arms.openArms(pad.getButton(1));
		//kicker.openKicker(pad.getButton(0));
		
		SmartDashboard.putString("Gyro", "" + gyro.getAngle());
		// cam.camUpdate();
	}

	public void testPeriodic() {
	}
}