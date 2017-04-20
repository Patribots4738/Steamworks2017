package org.usfirst.frc.team4738.robot;

import org.usfirst.frc.team4738.autonomousIO.DummyGamepad;
import org.usfirst.frc.team4738.autonomousIO.DummyXbox;
import org.usfirst.frc.team4738.autonomousIO.FileManager;
import org.usfirst.frc.team4738.autonomousIO.FileManager.FileType;
import org.usfirst.frc.team4738.wrapper.Constants;
import org.usfirst.frc.team4738.wrapper.Gamepad;
import org.usfirst.frc.team4738.wrapper.Gyro;
import org.usfirst.frc.team4738.wrapper.PIDMecanumDrive;
import org.usfirst.frc.team4738.wrapper.PIDVictorSP;
import org.usfirst.frc.team4738.wrapper.Timer;
import org.usfirst.frc.team4738.wrapper.ToggleButton;
import org.usfirst.frc.team4738.wrapper.XboxController;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	// Cheeki Breeki
	
	// Declaring all the various variables for the possible variably various variants of the various variants of variables.
	// All of the variously varying variables can vary.
	// WARNING: VARIOUS VARIOUSLY VARYING VARIABLES THAT VARY
	PIDVictorSP[] motors;
	VictorSP winch;
	PIDMecanumDrive drive;

	Gyro gyro;
	Autonomous autoDrive;
	Timer timer;
	FileManager manager;

	XboxController xbox;
	Gamepad pad;
	DummyXbox dummyXbox; // Man, what a low IQ.
	DummyGamepad dummyPad; // Geez, they're everywhere!

	ToggleButton toggle = new ToggleButton();

	MultiServo arms, kicker;
	// Camera cam;

	public void robotInit() {
		double[][] armBounds = { { 0, 90 }, { 90, 0 } }; // Sets the bounds for the arm servos
		double[][] kickBounds = { { 105, 60 }, { 60, 105 } }; // Same as ^, but for kicker

		arms = new MultiServo(0, 1); // Init arms
		kicker = new MultiServo(2, 3); // Init kicker
		arms.setBounds(armBounds); // Sets bounds as defined above
		kicker.setBounds(kickBounds); // Ditto ^
		
		gyro = new Gyro(0);
		timer = new Timer();
		
		/*	Motor Listing:		
		 * Port 9: Back Right
		 * Port 8: Back Left
		 * Port 7: Front Left
		 * Port 6: Front Right
		 * Port 5: Winch
		 */
		motors = new PIDVictorSP[4]; // Initializes the array

		motors[0] = new PIDVictorSP(7, 2, 3, Constants.WHEEL_RADIUS, 0, 0, 0); // front left
		motors[1] = new PIDVictorSP(8, 6, 7, Constants.WHEEL_RADIUS, 0, 0, 0);// back left
		motors[2] = new PIDVictorSP(6, 0, 1, Constants.WHEEL_RADIUS, 0, 0, 0);// front right
		motors[3] = new PIDVictorSP(9, 4, 5, Constants.WHEEL_RADIUS, 0, 0, 0);// back right
		
		drive = new PIDMecanumDrive(motors); // Initializes the drive functions
		xbox = new XboxController(0);
		pad = new Gamepad(1);
		winch = new VictorSP(5); // Initializes the winch motor

		autoDrive = new Autonomous(drive, gyro, arms, kicker);
		manager = new FileManager();
		CameraServer.getInstance().startAutomaticCapture(1);
	}

	int autoMode = 0;
	
	public void autonomousInit() {
		autoMode = (int) SmartDashboard.getNumber("AutoMode", 0); // Retrieves the Auto Mode from dashboard
		autoDrive.reset();
		drive.reset();  	// Resets all the persistant variables to zero.
		
		if (autoMode == -2) {

			manager.setFile("Autonomous", FileType.GP, false); // Opens the file called "Autonomous.GP" for reading controller instructions

			dummyXbox = new DummyXbox(0, manager, 2);		// Creates the dummy controllers 
			dummyPad = new DummyGamepad(1, manager, 2);
		}	
	}

	public void autonomousPeriodic() {
		if (autoMode == -2) {
			SmartDashboard.putNumber("Gyro Angle", gyro.gyro.getAngle());	// Pushes the current core gyroscope angle to the dashboard	
			dummyXbox.updateData();		// Updates the data from the data file currently opened in the file manager for reading.
			dummyPad.updateData();			// Ditto ^
	
			drive.parabolicMecanum(-dummyXbox.getLeftStick().getX(), dummyXbox.getLeftStick().getY(),
					-dummyXbox.getRightStick().getX(), 6); // Tells the drive to drive to drive based on the dummy's input
	
			winch.set(dummyPad.getAxis(1));				// Sets the motor based on the dummy's input
			arms.servoState(dummyPad.getButton(1));		// Ditto ^
			kicker.servoState(dummyPad.getButton(0));	// Ditto ^
		} else {
			autoDrive.autonomousChooser(autoMode);
		}
	}

	public void teleopInit() {
		xbox.resetTimer();	// Resets the timers in the controller classes for recording.
		pad.resetTimer();	// Ditto ^
		
		drive.reset();
		drive.setPID(0, 0, 0);	// Sets the PID constants, currently disabled
		drive.motors[0].victor.stopMotor();		// Stops the motors at the start.
		drive.linearMecanum(0, 0, 0, 16);		// Another attempt to stop the motors
		gyro.reset();	// Resets the gyro to zero

		if (SmartDashboard.getBoolean("IsRecording",false)) { // If the recording button in dashboard is ticked then
			manager.createNewFile("Autonomous", FileType.GP, false, true); // Create a new autonomous file
		}
		
	}

	public void teleopPeriodic() {
		SmartDashboard.putNumber("Gyro Angle", gyro.getOffsetAngle()); // Puts some data to the dashboard
		// TODO: Remove the following when done debugging
		SmartDashboard.putNumber("XboxTimer", xbox.getCurrentTime());		// Ditto ^
		SmartDashboard.putNumber("JoystickTimer", pad.getCurrentTime());	// Ditto ^
		
		boolean recording = SmartDashboard.getBoolean("IsRecording", false); // Gets if we are recording

		if (recording) {
			manager.writeToFile(xbox.toString() + "\n" + pad.toString()); // If we are, then write the current data to the file
		}

		drive.parabolicMecanum(-xbox.getLeftStick().getX(), xbox.getLeftStick().getY(), -xbox.getRightStick().getX() * .75, 6); // Drive based on controller

		winch.set(pad.getAxis(1));
		arms.servoState(pad.getButton(1));
		kicker.servoState(pad.getButton(0));

		SmartDashboard.putNumber("Gyro", gyro.getAngle());
	}

	@Override
	public void testInit() {
		timer.reset();
	}
	
	@Override
	public void testPeriodic() {
		if (timer.wait(5000D)) {
			System.out.println(timer.getTime());
			System.out.println((int)timer.getTime());
			timer.reset();
		}
	}
}
