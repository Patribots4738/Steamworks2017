package org.usfirst.frc.team4738.wrapper.vision;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ObjectDetector {
	
	double actualHeight = 3, focalLength = 458, FOV = 80;
	
	Scalar upperBound, lowerBound;
	Mat erodeElement, dialateElement;
	
	public ObjectDetector(double focalLength, double actualHeight, double FOV, int erode_size, int dialate_size, Scalar upper, Scalar lower){
		this.focalLength = focalLength;
		this.actualHeight = actualHeight;
		this.FOV = FOV;
		erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*erode_size + 1, 2*erode_size+1));
		dialateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*dialate_size + 1, 2*dialate_size+1));
	}
	
	public VisionObject[] findObjects(Mat mat){
		
		Core.flip(mat, mat, 1);
		
		Mat src = mat.clone(), dst = mat.clone();
		Mat hierarchy = new Mat();
		Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2HSV);
		Imgproc.GaussianBlur(dst, dst, new Size(5, 5),  2, 2);
		
		Core.inRange(dst, lowerBound, upperBound, dst);
		
		Imgproc.erode(dst, dst, erodeElement);
		Imgproc.dilate(dst, dst, dialateElement);	
		
		ArrayList<MatOfPoint> contours = new ArrayList<>();
		
		Imgproc.findContours(dst.clone(), contours, hierarchy,  Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
		ArrayList<VisionObject> objects = new ArrayList<>(contours.size());
		
		for(int i = 0; i < contours.size(); i++){
			VisionObject obj = new VisionObject();
			RotatedRect rect = new RotatedRect();
			rect = Imgproc.minAreaRect(new MatOfPoint2f(contours.get(i).toArray()));
			double distanceForwards = (actualHeight * focalLength) / (rect.size.height);
			double distanceHorizontal = rect.center.x * Math.sin(Math.toRadians(FOV/2));
			obj.angleFromCamera = Math.atan2(distanceForwards, distanceHorizontal);
			double[] distance = {distanceForwards, distanceHorizontal};
			obj.distance = distance;
			obj.distanceMagnitude = Math.sqrt((distanceHorizontal * distanceHorizontal) + (distanceForwards * distanceForwards)); 
			
			objects.add(obj);
		}
		
		return objects.toArray(new VisionObject[objects.size()]);
	}
}
