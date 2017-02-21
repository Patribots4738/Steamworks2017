package org.usfirst.frc.team4738.utils;


public class Mathd{

	public static double clamp(double value, double max, double min){
		return Math.max(min, Math.min(max, value));
	}
	
	
	public static double normalize(double value, double max, double min){
		return 0;
	}
	
	public static double lerp(double value, double destination, double dampening){
		//return value + (destination - value) * dampening;
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
