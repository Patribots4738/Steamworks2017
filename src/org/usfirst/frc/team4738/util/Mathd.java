package org.usfirst.frc.team4738.util;

public class Mathd{

	public static double clamp(double value, double max, double min){
		return Math.max(min, Math.min(max, value));
	}
	
	
	public static double normalize(double value, double max, double min){
		return 0;
	}
	
	/**
	 * @author Collin
	 * @param value The normalized value to curve with exponent of 2
	 * @return The curved normalized value
	 */
	
	public static double curve(double value) {
		return value * Math.abs(value);
	}
	
	public static Axes curveAxes(Axes axes) { 
		return new Axes(curve(axes.getX()), curve(axes.getY()));
	}
	
}
