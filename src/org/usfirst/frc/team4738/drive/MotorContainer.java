package org.usfirst.frc.team4738.drive;

import org.usfirst.frc.team4738.controllers.interfaces.Input;
import org.usfirst.frc.team4738.robot.Robot;
import org.usfirst.frc.team4738.util.pid_util.ErrorRetriever;
import org.usfirst.frc.team4738.util.pid_util.PID;

import edu.wpi.first.wpilibj.VictorSP;

public class MotorContainer {
	
	VictorSP[] motors;
	PID[] motorControllers;
	Input input;
	
	public MotorContainer(int[] ports, Input input) {
		motors = new VictorSP[ports.length];
		this.input = input;
		
		for (int i=0; i<ports.length; i++) {
			motors[i] = new VictorSP(ports[i]);
		}
		
		motorControllers = new PID[] {
				new PID(1, 1, 1, 5, new ErrorRetriever() {
					@Override
					public double getError() {
						return getSpeeds(Robot.forward, Robot.strafe, Robot.rotate)[0] - motors[0].getSpeed();
					}
				}),
				new PID(1, 1, 1, 5, new ErrorRetriever() {
					@Override
					public double getError() {
						return getSpeeds(Robot.forward, Robot.strafe, Robot.rotate)[1] - motors[1].getSpeed();
					}
				}),
				new PID(1, 1, 1, 5, new ErrorRetriever() {
					@Override
					public double getError() {
						return getSpeeds(Robot.forward, Robot.strafe, Robot.rotate)[2] - motors[2].getSpeed();
					}
				}),
				new PID(1, 1, 1, 5, new ErrorRetriever() {
					@Override
					public double getError() {
						return getSpeeds(Robot.forward, Robot.strafe, Robot.rotate)[3] - motors[3].getSpeed();
					}
				})
		};
	}
	
	public void resetWheelSpeeds() {
		for (int i=0; i<motors.length; i++) {
			motors[i].set(0);
		}
	}
	
	public void updateWheelSpeeds(double forward, double strafe, double rotate) {
		double[] speeds = getSpeeds(forward, strafe, rotate);
		for (int i=0; i<motors.length;i++) {
			double voltage = motorControllers[i].getPID(speeds[i], motors[i].getSpeed());
			motors[i].set(voltage);
		}
	}
	
	public double[] getSpeeds(double forward, double strafe, double rotate) {
		
		double r = Math.hypot(forward, strafe);
		double robotAngle = Math.PI/2 - Math.atan2(forward, strafe);
		final double v1 =  (r * Math.sin(robotAngle + Math.PI/4) + rotate);
		final double v2 = -(r * Math.cos(robotAngle + Math.PI/4) - rotate);
		final double v3 =  (r * Math.cos(robotAngle + Math.PI/4) + rotate);
		final double v4 = -(r * Math.sin(robotAngle + Math.PI/4) - rotate);
		
		double[] speeds = {v1, v2, v3, v4};
		return speeds;
		
		
	}
	
	public void setInput(Input input) {
		this.input = input;
	}
	
}