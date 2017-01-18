package org.usfirst.frc.team4738.util;

public class Axes {
	private double X;
	private double Y;
	private double Z;
	
	public Axes(double X, double Y) {
		this.X = X;
		this.Y = Y;
		this.Z = 0D;
	}
	
	public Axes(double X, double Y, double Z) {
		this.X = X;
		this.Y = Y;
		this.Z = Z;
	}

	public double getX() {
		return X;
	}

	public void setX(double x) {
		X = x;
	}

	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}

	public double getZ() {
		return Z;
	}

	public void setZ(double z) {
		Z = z;
	}
}
