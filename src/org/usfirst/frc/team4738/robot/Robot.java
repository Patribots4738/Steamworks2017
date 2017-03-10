package org.usfirst.frc.team4738.robot;

import org.usfirst.frc.team4738.enums.XboxButtons;
import org.usfirst.frc.team4738.wrapper.Gamepad;
import org.usfirst.frc.team4738.wrapper.Gyro;
import org.usfirst.frc.team4738.wrapper.PIDMecanumDrive;
import org.usfirst.frc.team4738.wrapper.PIDVictorSP;
import org.usfirst.frc.team4738.wrapper.Timer;
import org.usfirst.frc.team4738.wrapper.XboxController;
//import org.usfirst.frc.team4738.wrapper.vision.Camera;
import org.usfirst.frc.team4738.wrapper.vision.Camera;

import edu.wpi.first.wpilibj.CameraServer;
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
	Timer timer;

	XboxController xbox;
	Gamepad pad;
	MultiServo arms, kicker;
	//Kicker kicker;

	Camera cam;
	
	//pwm 5, mcm - winch
	//
	public void robotInit() {
		//arms = new Arms(0, 1);
		//kicker = new Kicker(2, 3);
		//used to be Arms(4)
		arms = new MultiServo(0, 1);
		
		double[][] bounds = {{0, 90}, {90, 0}};
		arms.setBounds(bounds);
		double[][] kickBounds = {{105, 70}, {70, 105}};
		//double[][] kickBounds = {{90, 90}, {90, 90}};
		kicker = new MultiServo(2, 3);
		kicker.setBounds(kickBounds);
		gyro = new Gyro(0);
		timer = new Timer();
		//9 - back right motor, 8 - back l, 7 - fl, 6 - fr, 5 - winch
		
		motors = new PIDVictorSP[4];
		motors[0] = new PIDVictorSP(7, 2, 3, 3, 0, 0, 0); // front left
		motors[1] = new PIDVictorSP(8, 6, 7, 3, 0, 0, 0);// back left
		motors[2] = new PIDVictorSP(6, 0, 1, 3, 0, 0, 0);// front right
		motors[3] = new PIDVictorSP(9, 4, 5, 3, 0, 0, 0);// back right
		
		// !!!!!1 to 6.6!!!, or 6.6 to 1 either way 
		drive = new PIDMecanumDrive(3 / 6.6, 0, 0, 0, motors);
		/*encoder1 = new Encoder(0, 1, 4);
		encoder2 = new Encoder(2, 3, 4);
		encoder3 = new Encoder(4, 5, 4);
		encoder4 = new Encoder(6, 7, 4);*/
		
		//9 , 8 , 7, 6
		//last 4 - 0, 1, 2, 3
//		drive = new PIDMecanumDrive(4, 0, 0, 0, new int[] {6, 7, 8, 9});
//		drive = new PIDMecanumDrive(4, 0, 0, 0, 7, 6, 8, 9);
		xbox = new XboxController(0);
		pad = new Gamepad(1);
		wench = new VictorSP(5);
		// pad = new XboxController(0);
		cam = new Camera(1);
		cam.startCamera();
		//______autoDrive = new Autonomous(drive, gyro, arms, kicker);
	}
	
	int autoMode = 0;
	
	public void autonomousInit() {
	/*	drive.setPID(1, 0, 0);
		drive.motors[0].victor.stopMotor();
		autoDrive.reset();
		
		try {
			autoMode = Integer.parseInt(SmartDashboard.getString("DB/String 0", "0"));
		} catch (Exception e) {
			autoMode = 0;
		}*/
	}

	public void autonomousPeriodic() {
	/*	timer.start();
		
		autoDrive.autonomousChooser(autoMode); // Changed from static value of 0 to the fist string in the DB/String
		
		SmartDashboard.putNumber("PosInOrder", autoDrive.posInOrder);
		SmartDashboard.putString("Gyro", "" + gyro.getAngle());
		*/
	}
	
	public void telopInit(){
		//drive.setPID(0, 0, 0);
		//drive.motors[0].victor.stopMotor();
		//drive.linearMecanum(0, 0, 0);
	}

	public void teleopPeriodic() {
		timer.start();
		 
		//Dampening was formerly at 8, changes to lerp may require re=adjusting it
		// ______drive.parabolicMecanum(xbox.getAxis(0), -xbox.getAxis(1), -xbox.getAxis(4), 3);
		drive.parabolicMecanum(-xbox.getLeftStick().getX(), xbox.getLeftStick().getY(), -xbox.getRightStick().getX());
		
		if(xbox.getButtonDown(XboxButtons.A)){
//			cam.cycleCamera();
		}
		
		wench.set(pad.getAxis(1));
//		arms.servos[0].set(0);
//		arms.servos[1].set(90);
		arms.servoState(pad.getButton(1));
		kicker.servoState(pad.getButton(0));
				
		SmartDashboard.putString("Gyro", "" + gyro.getAngle());
		cam.camUpdate();
		
//		System.out.println(cam.inputs);
//		System.out.println(cam.inputs.toString());
	}
		
	public void testPeriodic() {
	}
}