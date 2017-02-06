package org.usfirst.frc.team4738.utils;


public class Mathd{

	public static double clamp(double value, double max, double min){
		return Math.max(min, Math.min(max, value));
	}
	
	
	public static double normalize(double value, double max, double min){
		return 0;
	}
}
