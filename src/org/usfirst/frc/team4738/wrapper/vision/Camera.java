package org.usfirst.frc.team4738.wrapper.vision;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera {
	
	UsbCamera cam;
	int camera = 0;
	
	CvSink inputs;
	CvSource output;
	
	
	public Camera(){
		System.out.println("0");
		cam = CameraServer.getInstance().startAutomaticCapture();
		cam.setFPS(30);
		cam.setResolution(640, 480);
		System.out.println(cam.isConnected());
		inputs = CameraServer.getInstance().getVideo();
		System.out.println("2");
//		output = CameraServer.getInstance().putVideo("TEST", 120, 90);
		System.out.println("3");
		System.out.println(cam.isConnected());
//		startCamera();
	}
	
	
	//FIXME: UNSAFE!!! DO NOT REPLICATE
	public void startCamera(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!Thread.interrupted()){
					pushMat(drawOnImage(updateCapture()));
				}
			}
		}).start();
		
	}
	
	public void camUpdate() {
		pushMat(drawOnImage(updateCapture()));
	}

	public void pushMat(Mat frame){
		output.putFrame(frame);
	}
	
	public Mat updateCapture(){
		Mat frame = new Mat();
		inputs.grabFrame(frame);		
		return frame;
	}
	
	/*public void changeCamera(int camera){
		if(camera > cam.length - 1){
			System.err.println("Woah!! THis camera doesn't exist you dimwit!");
			return;
		}
		
		camera = this.camera;
	}*/
	
	
		
	public static Mat drawOnImage(Mat frame){
		Mat dst = frame; //This is your destination mat set it as the input and output of the draw functions
		
		//Example on how to use
		dst = drawCrosshair(dst);
		dst = drawDepthLine(dst);
		//For your own code please create a new function that returns a Mat and gets a Mat input
		//Then call this function here in order to add it to the camera's feed
		
		return dst;
	}
	
	public static Mat drawDepthLine(Mat frame){
		Imgproc.line(frame, new Point(frame.width() * .75, frame.height() / 1.6), new Point(frame.width() , frame.height()), new Scalar(0, 0, 0, 127), 6);
		Imgproc.line(frame, new Point(frame.width() * .25, frame.height() / 1.6), new Point(0 , frame.height()), new Scalar(0, 0, 0, 127), 6);
		return frame;
	} 
	
	public static Mat drawCrosshair(Mat frame){
		Imgproc.circle(frame, new Point(frame.width() / 2, frame.height() / 2), 3, new Scalar(0, 128, 0));
		return frame;
	}

	/*public void cycleCamera(){
		if((camera + 1) > cam.length - 1){
			camera = 0;
		}
		changeCamera(camera++);
	}*/
}
