package org.usfirst.frc.team4738;

import edu.wpi.first.wpilibj.CameraServer;

public class Camera {
	CameraServer server;

	public Camera() {
		server = CameraServer.getInstance();
		//server.setQuality(50); //7 percent
		//server.startAutomaticCapture("cam1");

	}
}
