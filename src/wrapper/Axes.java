package wrapper;

/**
 * @author Garret
 */
public class Axes {

	double X, Y, Z;
	
	/**
	 * @param X Value of x-axis.
	 * @param Y Value of y-axis.
	 */
	public Axes(double X, double Y){
		this.X = X;
		this.Y = Y;
	}
	
	public Axes(double X, double Y, double Z){
		this.X = X;
		this.Y = Y;
		this.Z = Z;
	}
	
	/**
	 * @return Value of x-axis.
	 */
	public double getX(){
		return X;
	}
	
	/**
	 * @return Value of y-axis.
	 */
	public double getY(){
		return Y;
	}
	
	/**
	 * 
	 * @return Value of z-axis.
	 */
	public double getZ(){
		return Z;
	}
	
	/**
	 * @param X sets the x-axis
	 */
	public void setX(double X){
		this.X = X;
	}
	
	/**
	 * @param Y sets the y-axis
	 */
	public void setY(double Y){
		this.Y = Y; 
	}
	
	/**
	 * 
	 * @param Z sets the Z-axis
	 */
	public void setZ(double Z){
		this.Z = Z;
	}
	
}