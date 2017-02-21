package org.usfirst.frc.team4738.wrapper.vision;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ObjectDetector {
	
	public static Object[] findObjects(Mat mat){
		
		Mat src = mat.clone(), dst = mat.clone();
		
		Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2HSV);
		
		
		return null;
	}
}
