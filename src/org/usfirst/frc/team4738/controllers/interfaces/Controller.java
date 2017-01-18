package org.usfirst.frc.team4738.controllers.interfaces;

import org.usfirst.frc.team4738.util.Axes;

public interface Controller extends Input {
	public Axes getLeftStick();
	public Axes getRightStick();
	public double getTriggerL();
	public double getTriggerR();
	public boolean getA();
	public boolean getB();
	public boolean getX();
	public boolean getY();
	public boolean getStart();
	public boolean getBack();
	public boolean getLeftStickButton();
	public boolean getRightStickButton();
}
