package org.usfirst.frc.team4738.drive;

import org.usfirst.frc.team4738.controllers.XboxController;
import org.usfirst.frc.team4738.controllers.interfaces.Controller;
import org.usfirst.frc.team4738.controllers.interfaces.Input;
import org.usfirst.frc.team4738.controllers.interfaces.JoystickWrapper;
import org.usfirst.frc.team4738.util.Mathd;

public enum DriveTypes {
	MECANUM_PARABOLIC {
		
		private double[] getSpeeds(double forward, double strafe, double rotate) {

			double forwardC = Mathd.curve(forward);
			double strafeC = Mathd.curve(strafe);
			double rotateC = Mathd.curve(rotate);
			
			double r = Math.hypot(forwardC, strafeC);
			double robotAngle = Math.PI/2 - Math.atan2(forwardC, strafeC);
			final double v1 =  (r * Math.sin(robotAngle + Math.PI/4) + rotateC);
			final double v2 = -(r * Math.cos(robotAngle + Math.PI/4) - rotateC);
			final double v3 =  (r * Math.cos(robotAngle + Math.PI/4) + rotateC);
			final double v4 = -(r * Math.sin(robotAngle + Math.PI/4) - rotateC);
			
			double[] speeds = {v1, v2, v3, v4};
			return speeds;
			
			
		}

		@Override
		public double[] getSpeeds(Input input) {
			if (input instanceof JoystickWrapper) {
				JoystickWrapper joystick = (JoystickWrapper) input;
				double forward = joystick.getSlider() * joystick.getY() * -1;
				double strafe = joystick.getSlider() * joystick.getX();
				double rotate = joystick.getZ() /2;
				return getSpeeds(forward, strafe, rotate);
			} else if (input instanceof Controller) {
				XboxController xboxController = (XboxController) input;
				double forward = xboxController.getLeftStick().getY();
				double strafe = xboxController.getLeftStick().getX();
				double rotate = xboxController.getRightStick().getX();
				return getSpeeds(forward, strafe, rotate);
			}
			
			return null;
		}
	},
	MECANUM_LINEAR {
		@Override
		public double[] getSpeeds(Input input) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	public abstract double[] getSpeeds(Input input);
}