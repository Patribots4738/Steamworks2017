package org.usfirst.frc.team4738.wrapper.vision;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera {
	
	VideoSink server;
	UsbCamera cam[];
	CvSink[] sink;
	CvSource source;
	
	int currCamera = 0;
	
	public Camera(int numCamera){
		server = CameraServer.getInstance().getServer();
		
		cam = new UsbCamera[numCamera];
		sink = new CvSink[numCamera];
		
		for(int i = 0; i < numCamera; i++){
			cam[i] = CameraServer.getInstance().startAutomaticCapture(i);
			sink[i].setSource(cam[i]);
			sink[i].setEnabled(true);
		}
		
		server.setSource(cam[currCamera]);
	}
	
	public int setCamera(int camNum){
		if(camNum >= cam.length || camNum < 0){
			return -1;
		}
		
		currCamera = camNum;
		return currCamera;
	}
	
	public void cycleCamera(boolean cycle){
		if(cycle){
			if(setCamera(++currCamera) == -1){
				setCamera(0);
			}
		}
	}
}
