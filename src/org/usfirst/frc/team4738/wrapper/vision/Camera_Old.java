package org.usfirst.frc.team4738.wrapper.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera_Old{
	
	UsbCamera[] cam;
	int camera = 0;
	
	CvSink[] inputs;
	CvSource output;
	
	ObjectDetector detector;
	Mat currentFrame;
	
	public Camera_Old(int numCamera){
		cam = new UsbCamera[numCamera];
		inputs = new CvSink[numCamera];
		
		for(int i = 0; i < numCamera; i++){
			cam[i] = new UsbCamera("USB Camera " + i, i);
			cam[i].setFPS(30);
			cam[i].setResolution(320, 240);
			
			//seems to be most recent
			inputs[i] = new CvSink(i + "");
			inputs[i].setSource(cam[i]);
			
			System.out.println(inputs[i]);
			System.out.println(cam[i]);
			
			//idk if recent or not
			//CameraServer.getInstance().addCamera(cam[i]);
			inputs[i] = CameraServer.getInstance().getVideo(cam[i]);
			inputs[i].setSource(cam[i]);
			//CameraServer.getInstance().addServer(inputs[i]);
		}
		
		output = CameraServer.getInstance().putVideo("Video", 320, 240);
	}
	
	public void enableObjectDetection(double focalLength, double actualHeight, double FOV, int erode_size, int dialate_size, Scalar upper, Scalar lower){
		detector = new ObjectDetector(focalLength, actualHeight, FOV, erode_size, dialate_size, upper, lower);
	}
	
	public ObjectDetector getObjectDetector(){
		return detector;
	}
	
	//FIXME: UNSAFE!!! DO NOT REPLICATE
	public void startCamera(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(!Thread.interrupted()){
					camUpdate();
				}
			}
		}).start();
		
	}
	
	public void camUpdate() {
		pushMat(drawOnImage(updateCapture()));
	}

	public void pushMat(Mat frame){
		output.putFrame(frame);
		frame.release();
	}
	
	public Mat updateCapture(){
		Mat frame = new Mat();
		inputs[camera].grabFrame(frame);
		currentFrame = frame;
		return frame;
	}
	
	public int setCamera(int camNum){
		
		if(camNum > cam.length - 1 || camNum < 0){
			return camera;
		}
		
		for(int i = 0; i < cam.length; i++){
			inputs[i].setEnabled(false);
		}
		
		inputs[camera].setEnabled(true);
		
		camera = camNum;
		return camNum;
	}
	
	public void cycleCamera(){
		if(camera > cam.length - 1){
			setCamera(0);
			return;
		}
		setCamera(++camera);
	}
	
	public VisionObject[] detectObjects(Mat frame){
		return detector.findObjects(frame);
	}
	
	public VisionObject[] detectObjects(){
		return detector.findObjects(currentFrame);
	}
	
	public static Mat drawOnImage(Mat frame){
		Mat dst = frame; //This is your destination mat set it as the input and output of the draw functions
		Core.flip(frame, dst, -1);
		dst = drawCrosshair(dst);
		//dst = drawDepthLine(dst);
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
}