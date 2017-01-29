package wrapper;

/**
* @authors Garett and Sami
*/
public class Encoder {
	
	private double circumference;
	public edu.wpi.first.wpilibj.Encoder encoder;
	private final double clicksPerRotation = 2048;
	private final double conversionFactor = clicksPerRotation/360;
	
	// Setting the encoder to its port values
	// Setting values radius and circumference   
	 
	public Encoder(int portA, int portB, double radius){
		encoder = new edu.wpi.first.wpilibj.Encoder(portA, portB);
		encoder.setDistancePerPulse(0.001);
		circumference = radius*2*Math.PI;
	}
	
	// This converts click rate to units per second
	public double getSpeed() {
		return encoder.getRate() / clicksPerRotation * circumference;
	}
	
	// clicks to units
	public double getDistance(){
		return encoder.getDistance() / clicksPerRotation * circumference;
	}
	
	// returns wheel angle between 0 and 359 degrees
	public double getAngle(){
		return (encoder.getDistance() / conversionFactor) % 360; 		
	}
	
	// resets distance and angle
	public void reset(){
		encoder.reset();
	}
}
