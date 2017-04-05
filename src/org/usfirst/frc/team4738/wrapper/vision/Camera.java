package org.usfirst.frc.team4738.wrapper.vision;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera {
	
	UsbCamera cam[];
	CvSink[] sink;
	CvSource source;
	
	UsbCamera camera1;
	UsbCamera camera2;
	CvSink cvsink1;
	CvSink cvsink2;
	VideoSink server;
	boolean prevTrigger = false;
	
	int currCamera = 0;
	
	public Camera(int numCamera){
		/*server = CameraServer.getInstance().getServer();
		
		cam = new UsbCamera[numCamera];
		sink = new CvSink[numCamera];
		
		for(int i = 0; i < numCamera; i++){
			cam[i] = CameraServer.getInstance().startAutomaticCapture(i);
			sink[i].setSource(cam[i]);
			sink[i].setEnabled(true);
		}
		
		server.setSource(cam[currCamera]);*/
		
		camera1 = CameraServer.getInstance().startAutomaticCapture(0);
		camera2 = CameraServer.getInstance().startAutomaticCapture(1);
		server = CameraServer.getInstance().getServer();
		// dummy sinks to keep camera connections open
		cvsink1 = new CvSink("cam1cv");
		cvsink1.setSource(camera1);
		cvsink1.setEnabled(true);
		cvsink2 = new CvSink("cam2cv");
		cvsink2.setSource(camera2);
		cvsink2.setEnabled(true);
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
