package org.usfirst.frc.team4738.wrapper;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * @author Owen
 */
public class Encoder{
	
	private final double ClicksPerRotation = 250;
	public edu.wpi.first.wpilibj.Encoder encoder;
	private double circumference, conversionFactor;

	/**
	 * @param port1 Channel A
	 * @param port2 Channel B
	 * @param radius Radius of the wheel.
	 */
	public static int PULSES_PER_ROTATION = 2048;
	
	/**
	 * @param port1 The first port of the encoder
	 * @param port2 The second port
	 * @param radius The radius of the wheel it is reading
	 * 
	 * This uses Constants.GEAR_RATIO as a multiplier for DPP
	 */
	public Encoder(int port1, int port2, double radius){
		encoder = new edu.wpi.first.wpilibj.Encoder(port1, port2, false, EncodingType.k4X); // Initializes the actual encoder
		encoder.setDistancePerPulse((radius * Math.PI * 2 * Constants.GEAR_RATIO) / PULSES_PER_ROTATION); // Calculates distance per pulse
		// FIXME: Figure out if this is better. I don't think it is, but it might be.
//		circumference = radius * 2 * Math.PI; 
//		conversionFactor = ClicksPerRotation / 360;
		
		encoder.reset();
	}
	
	/**
	 * @return The distance the encoder has spun, based on DPP
	 */
	//switched the getDistance and ClicksPerRotation
	public double getDistance(){
//		return circumference * (encoder.getDistance() / ClicksPerRotation);
		return encoder.getDistance();
	}
	
	/**
	 * @author Ghjf544912
	 * @return The speed the encoder is at based on the units of the radius per second.
	 */
	public double getSpeed(){
		return circumference * (encoder.getRate() / ClicksPerRotation);
	}
	
	/**
	 * @return Angle of rotation based on initial angle.
	 */
	public double getAngle(){
		double clicks = encoder.getDistance();
		clicks = (Math.abs(clicks / ClicksPerRotation) - (int)(Math.abs(clicks / ClicksPerRotation)));
		return (clicks * ClicksPerRotation) / conversionFactor;
	}
	
	public void reset(){
		encoder.reset();
	}
	
}
