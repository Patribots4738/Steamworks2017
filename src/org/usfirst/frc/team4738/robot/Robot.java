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

	PIDVictorSP[] motors;
	VictorSP wench;
	Gyro gyro;
	PIDMecanumDrive drive;
	// Camera cam;

	XboxController xbox;
	Gamepad pad;
	Arms arm;
	Kicker kicker;

	// this is what we're using to test the code
	public void robotInit() {
		arm = new Arms(0, 1);
		kicker = new Kicker(2, 3);

		gyro = new Gyro(1);

		motors = new PIDVictorSP[4];
		motors[0] = new PIDVictorSP(7, 2, 3, 3, 0, 0, 0); // front left
		motors[1] = new PIDVictorSP(8, 6, 7, 3, 0, 0, 0);// back left
		motors[2] = new PIDVictorSP(6, 0, 1, 3, 0, 0, 0);// front right
		motors[3] = new PIDVictorSP(9, 4, 5, 3, 0, 0, 0);// back right
		drive = new PIDMecanumDrive(3, 0, 0, 0, motors);
		// drive = new PIDMecanumDrive(4, 0, 0, 0, 0, 1, 2, 3);
		xbox = new XboxController(0);
		pad = new Gamepad(1);
		wench = new VictorSP(5);
		// pad = new XboxController(0);
		Camera cam = new Camera();
		//cam.startCamera();

		System.out.println("PRINTING STUFFS");
		try {
			File dir = new File("/dev/");
			File[] filesList = dir.listFiles();
			for (File file : filesList) {
				if (file.isFile()) {
					System.out.println(file.getName());
				}
			}
		} catch (Exception e) {
			System.out.println("Yeah, the file listing crashed.");
		}
	}

	public void autonomousInit() {
	}

	public void autonomousPeriodic() {

	}

	public void teleopPeriodic() {
		drive.parabolicMecanum((xbox.getAxis(0)), -xbox.getAxis(1), -xbox.getAxis(4), 12);

		wench.set(pad.getAxis(1));

		arm.openArms(pad.getButton(1));
		kicker.openKicker(pad.getButton(0));

		SmartDashboard.putString("Gyro angle", "" + gyro.getAngle());
		// cam.camUpdate();
	}

	public void testPeriodic() {
	}
}