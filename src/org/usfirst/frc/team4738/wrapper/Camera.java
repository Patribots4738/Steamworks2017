package org.usfirst.frc.team4738.wrapper;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera {
	
	UsbCamera[] cam;
	int camera = 0;
	
	CvSink[] inputs;
	CvSource output;
	
	
	public Camera(int numCamera){
		cam = new UsbCamera[numCamera];
		inputs = new CvSink[numCamera];
		
		for (int i = 0; i < numCamera; i++) {
			cam[i] = CameraServer.getInstance().startAutomaticCapture("" + i, i);
			inputs[i] = CameraServer.getInstance().getVideo("" + i);
		}
		
		output = CameraServer.getInstance().putVideo("Video", 320, 240);
	}
	
	
	//FIXME: UNSAFE!!! DO NOT REPLICATE
	public void startCamera(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!Thread.interrupted()){
					pushMat(drawCrosshair(updateCapture()));
				}
			}
		}).start();
		
	}

	public void pushMat(Mat frame){
		output.putFrame(frame);
	}
	
	public Mat updateCapture(){
		Mat frame = new Mat();
		inputs[camera].grabFrame(frame);		
		return frame;
	}
	
	public void changeCamera(int camera){
		if(camera > cam.length - 1){
			System.err.println("Woah!! THis camera doesn't exist you dimwit!");
			return;
		}
		
		camera = this.camera;
	}
	
	public Mat drawCrosshair(Mat frame){
		Imgproc.circle(frame, new Point(frame.width() / 2, frame.height() / 2), 3, new Scalar(0, 128, 0));
		return frame;
	}

	public void cycleCamera(){
		if((camera + 1) > cam.length - 1){
			camera = 0;
		}
		changeCamera(camera++);
	}
}
