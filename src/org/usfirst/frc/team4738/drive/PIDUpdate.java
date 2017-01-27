package org.usfirst.frc.team4738.drive;

import java.util.TimerTask;

import org.usfirst.frc.team4738.robot.Constants;
import org.usfirst.frc.team4738.robot.Robot;
import org.usfirst.frc.team4738.util.PID;

public class PIDUpdate extends TimerTask {
	
	MotorContainer container;
	Robot robot;
	
	public PIDUpdate(MotorContainer container, Robot robot) {
		this.container = container;
		this.robot = robot;
	}
	
	@Override
	public void run() {
		PID[] controllers = container.motorControllers;
		double[] speeds = container.getSpeeds(robot.forward, robot.strafe, robot.rotate);
		double[] motorErrors = new double[] {
			speeds[0] - container.motors[0].getSpeed(),
			speeds[1] - container.motors[1].getSpeed(),
			speeds[2] - container.motors[2].getSpeed(),
			speeds[3] - container.motors[3].getSpeed()
		};
		for (int i = 0; i<controllers.length; i++) {
			controllers[i].update(Constants.TICK_RATE, motorErrors[i]);
		}
	}

}
