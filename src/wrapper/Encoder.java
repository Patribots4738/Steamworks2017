package wrapper;

/**
 * @author Owen
 */
public class Encoder{
	
	private final double ClicksPerRotation = 2048;
	public static edu.wpi.first.wpilibj.Encoder encoder;
	@SuppressWarnings("unused")
	private double radius, circumference, conversionFactor;
	
	/**
	 * @param port1 Channel A
	 * @param port2 Channel B
	 */
	public Encoder(int port1, int port2){
		encoder = new edu.wpi.first.wpilibj.Encoder(port1, port2);
		encoder.setDistancePerPulse(0.001);
	}
	
	/**
	 * @param port1 Channel A
	 * @param port2 Channel B
	 * @param radius Radius of the wheel.
	 */
	public Encoder(int port1, int port2, double radius){
		encoder = new edu.wpi.first.wpilibj.Encoder(port1, port2);
		this.radius = radius;
		circumference = radius * 2 * Math.PI;
		conversionFactor = ClicksPerRotation / 360;
	}
	
	/**
	 * @return The distance the encoder has spun based on the units of the radius.
	 */
	public double getDistance(){
		 return circumference * (encoder.getDistance() / ClicksPerRotation);
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
	
	public static void reset(){
		encoder.reset();
	}
	
}