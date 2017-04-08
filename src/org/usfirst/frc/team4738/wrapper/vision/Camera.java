package org.usfirst.frc.team4738.wrapper.vision;
	
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera {

		UsbCamera[] cam;
		int camera = 0;
		
		CvSink[] inputs;
		CvSource output;
		
		ObjectDetector detector;
		Mat currentFrame;
		
		public Camera(int numCamera){
			cam = new UsbCamera[numCamera];
			inputs = new CvSink[numCamera];
			
			for(int i = 0; i < numCamera; i++){
				cam[i] = CameraServer.getInstance().startAutomaticCapture(i);
				cam[i].setFPS(30);
				cam[i].setResolution(320, 240);
				cam[i].setExposureManual(-5);
				
				inputs[i] = CameraServer.getInstance().getVideo(cam[i].getName());
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
			pushMat(CameraUtils.drawOnImage(updateCapture()));
		}

		public void pushMat(Mat frame){
			output.putFrame(frame);
			frame.release();
		}
		
		public Mat updateCapture(){
			Mat frame = new Mat();
			inputs[camera].grabFrame(frame);
			currentFrame.release();
			currentFrame = frame.clone();
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
			System.out.println(currentFrame.empty());
			return detector.detectObjects(currentFrame);
		}
}
