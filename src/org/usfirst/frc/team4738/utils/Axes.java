package org.usfirst.frc.team4738.utils;

/**
 * @author Unknown
 * Simple data encapsulation class.
 */
public class Axes {

	double X, Y, Z;
	
	public Axes(double X){
		this.X = X;
	}
	
	/**
	 * @param X Value of x-axis.
	 * @param Y Value of y-axis.
	 */
	public Axes(double X, double Y){
		this.X = X;
		this.Y = Y;
	}
	
	/**
	 * @param X Value of x-axis.
	 * @param Y Value of y-axis.
	 * @param Z Value of z-axis.
	 */
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