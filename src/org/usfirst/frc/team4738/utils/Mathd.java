package org.usfirst.frc.team4738.utils;

/**
 * @author Various peeplz
 * Random useful math functions
 */
public class Mathd{

	public static double clamp(double value, double max, double min){
		return Math.max(min, Math.min(max, value));
	}
	
	
	public static double normalize(double value, double max){
		return value / max;
	}
	
	//variables a, b, and f are to be replaced with current position, destination, and dampening
	float lerp(float a, float b, float f)
	{
	    return a + f * (b - a);
	}
	
	//This was basically cerp and then changed with a "c"
	public static double lerp(double value, double destination, double dampening){
		//return value + (destination - value) * dampening;
		//THIS ONE HAS PARANTHESIS AND THE OTHER CERP DOESNT
		return value * (1-dampening) + (destination*dampening);
	}
	
	public static double cerp(double value, double destination, double dampening){
		dampening = (1-Math.cos(dampening * Math.PI)) / 2;
		return value *(1-dampening) + destination * dampening;
	}
	
	public static double findAngle(double x, double y, double deadband){
		if (deadband > Math.abs(x) || deadband > Math.abs(y)){
			return 0;
		}
		
		return Math.atan(y/x);
	}
}
