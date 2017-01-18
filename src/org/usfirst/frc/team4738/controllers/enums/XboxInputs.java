package org.usfirst.frc.team4738.controllers.enums;

public enum XboxInputs {
	A(1), B(2), X(3), Y(4), LB(5), RB(6), BACK(7), START(8), LEFT_STICK_BUTTON(9), RIGHT_STICK_BUTTON(10),
	LEFT_STICK_X(0), LEFT_STICK_Y(1), LEFT_TRIGGER(2), RIGHT_TRIGGER(3), RIGHT_STICK_X(4), RIGHT_STICK_Y(5);
	
	public int num;
	XboxInputs(int num) {
		this.num = num;
	}
	
}
