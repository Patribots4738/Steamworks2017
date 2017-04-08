package org.usfirst.frc.team4738.robot;

import org.usfirst.frc.team4738.wrapper.Constants;
import org.usfirst.frc.team4738.wrapper.Gamepad;
import org.usfirst.frc.team4738.wrapper.Gyro;
import org.usfirst.frc.team4738.wrapper.PIDMecanumDrive;
import org.usfirst.frc.team4738.wrapper.PIDVictorSP;
import org.usfirst.frc.team4738.wrapper.Timer;
import org.usfirst.frc.team4738.wrapper.XboxController;
import org.usfirst.frc.team4738.wrapper.vision.Camera;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	//Cheeki Breeki
	PIDVictorSP[] motors;
	VictorSP winch;
	Gyro gyro;
	PIDMecanumDrive drive;
	Autonomous autoDrive;
	Timer timer;
	XboxController xbox;
	Gamepad pad;
	MultiServo arms, kicker;
	Camera cam;
	
	public void robotInit() {
		//arms = new Arms(0, 1);
		//kicker = new Kicker(2, 3);
		//used to be Arms(4)
		arms = new MultiServo(0, 1);
		
		double[][] bounds = {{0, 90}, {90, 0}};
		arms.setBounds(bounds);
		double[][] kickBounds = {{105, 60}, {60, 105}};
		kicker = new MultiServo(2, 3);
		kicker.setBounds(kickBounds);
		gyro = new Gyro(0);
		timer = new Timer();

		//9 - br, 8 - bl, 7 - fl, 6 - fr, 5 - winch
		
		motors = new PIDVictorSP[4];
		
		motors[0] = new PIDVictorSP(7, 2, 3, 3 * Constants.GEAR_RATIO, 0, 0, 0); // front left
		motors[1] = new PIDVictorSP(8, 6, 7, 3 * Constants.GEAR_RATIO, 0, 0, 0);// back left
		motors[2] = new PIDVictorSP(6, 0, 1, 3 * Constants.GEAR_RATIO, 0, 0, 0);// front right
		motors[3] = new PIDVictorSP(9, 4, 5, 3 * Constants.GEAR_RATIO, 0, 0, 0);// back right
		
		// !!!!!1 to GEAR_RATIO!!!, or GEAR_RATIO to 1 either way 
		drive = new PIDMecanumDrive(3 * Constants.GEAR_RATIO, 0, 0, 0, motors);
		/*encoder1 = new Encoder(0, 1, 4);
		encoder2 = new Encoder(2, 3, 4);
		encoder3 = new Encoder(4, 5, 4);
		encoder4 = new Encoder(6, 7, 4);*/
		
//		drive = new PIDMecanumDrive(4, 0, 0, 0, new int[] {6, 7, 8, 9});
//		drive = new PIDMecanumDrive(4, 0, 0, 0, 7, 6, 8, 9);
		xbox = new XboxController(0);
		pad = new Gamepad(1);
		winch = new VictorSP(5);
		try{
			cam = new Camera(1);
			cam.startCamera();
		} catch(Exception e){
			System.err.println(e.toString());
		}
		//cam.enableObjectDetection(448, 5, 80, 5, 5, new Scalar(130, 100, 100), new Scalar(110, 80, 75));
		
		autoDrive = new Autonomous(drive, gyro, arms, kicker);
//		drive = new PIDMecanumDrive(4, 0, 0, 0, 0, 1, 2, 3);
		xbox = new XboxController(0);
		pad = new Gamepad(1);
		//wench = new VictorSP(5);
		// pad = new XboxController(0);
//		cam = new Camera(1);
//		cam.startCamera();
		autoDrive = new Autonomous(drive, gyro, arms, kicker);
	}
	
	int autoMode = 3;
	
	public void autonomousInit(){ 
		autoDrive.reset();
		
		//reset encoder for top left wheel, which has the encoder we use for the autonomous
		drive.motors[0].encoder.reset();
		
//		try {
//			autoMode = Integer.parseInt(SmartDashboard.getString("DB/String 0", "0"));
//		} catch (Exception e) {
//			autoMode = 0;
//		}
	}

	public void autonomousPeriodic(){
		
		autoDrive.autonomousChooser(4);
		
		for(int i = 0; i < 4; i++){
			SmartDashboard.putNumber("Encoder " + i, drive.motors[i].encoder.getDistance());
		}
		SmartDashboard.putString("Gyro", "" + gyro.getAngle());
		
	}
	
	public void telopInit(){
		drive.setPID(0, 0, 0);
		drive.motors[0].victor.stopMotor();
		drive.linearMecanum(0, 0, 0, 16);
		gyro.reset();
	}

	public void teleopPeriodic() {
		timer.start();
		
		drive.parabolicMecanum(xbox.getLeftStick().getX(), -xbox.getLeftStick().getY(), xbox.getRightStick().getX(), 6);
	
		winch.set(pad.getAxis(1));
//		arms.servos[0].set(0);
//		arms.servos[1].set(90);
		arms.servoState(pad.getButton(1));
		kicker.servoState(pad.getButton(0));
		//drive.parabolicMecanum(xbox.getAxis(0), -xbox.getAxis(1), -xbox.getAxis(4));
		//drive.parabolicMecanum(xbox.getAxis(0), -xbox.getAxis(1), -xbox.getAxis(4));
		
		SmartDashboard.putString("Gyro angle", "" + gyro.getAngle());
		
		/*try{
			VisionObject[] detectedObjects = cam.detectObjects();
			for(int i = 0; i < detectedObjects.length; i++){
				System.out.println(i + " " + detectedObjects[i].distance[1]);
			}
		} catch(IllegalThreadStateException e){e.printStackTrace();}*/
		
	}
	
		public void testPeriodic() {
	}
}
