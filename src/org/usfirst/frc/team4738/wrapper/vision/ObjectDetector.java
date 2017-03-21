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
		this.actualHeight = actualHeight; //This is the height of the object you are trying to detect
		this.FOV = FOV;
		erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*erode_size + 1, 2*erode_size+1));
		dialateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*dialate_size + 1, 2*dialate_size+1));
	}
	
	//This finds distance using triangle similarity, you can find a useful doc in vision equations 
	public VisionObject[] findObjects(Mat mat){
		
		Core.flip(mat, mat, 1); //Flips the image horizontally so it isn't mirrored
		
		Mat src = mat.clone(), dst = mat.clone();
		Mat hierarchy = new Mat(); 
		Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2HSV); //Converts the image from RGB to HSV color space
		Imgproc.GaussianBlur(dst, dst, new Size(5, 5),  2, 2); //Surprise! This blur blurs the image
		
		//This return a binary image(black and white) of the pixels that have a HSV value between the upper and lower bound
		Core.inRange(dst, lowerBound, upperBound, dst); 
		
		Imgproc.erode(dst, dst, erodeElement); //This removes any small noise by encroachin in on all of the pixels
		Imgproc.dilate(dst, dst, dialateElement); //We then expand all of the pixels to fix the eroding we did, but now the noise is gone
		
		ArrayList<MatOfPoint> contours = new ArrayList<>();
		
		//Here we are finding enclosed objects
		//Warning this is pretty expensive
		Imgproc.findContours(dst.clone(), contours, hierarchy,  Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
		ArrayList<VisionObject> objects = new ArrayList<>(contours.size());
		
		for(int i = 0; i < contours.size(); i++){
			VisionObject obj = new VisionObject();
			RotatedRect rect = new RotatedRect();
			rect = Imgproc.minAreaRect(new MatOfPoint2f(contours.get(i).toArray()));
			double distanceForwards = (actualHeight * focalLength) / (rect.size.height); //Calculates the distance using triangle similarity
			double distanceHorizontal = rect.center.x * Math.sin(Math.toRadians(FOV/2)); //Incomplete calculation to determine the horizontal position of the object
			obj.angleFromCamera = Math.atan2(distanceForwards, distanceHorizontal); //Angle from camera because I though I needed it when I wrote it (WTF?)
			double[] distance = {distanceForwards, distanceHorizontal}; //I'm sure you can figure out what this is doing...
			obj.distance = distance;
			obj.distanceMagnitude = Math.sqrt((distanceHorizontal * distanceHorizontal) + (distanceForwards * distanceForwards)); //Our lord pythagoras with his great theorem
			
			objects.add(obj);
		}
		
		//In case you are wondering, this is how you convert an arraylist to an array
		//It's => ArrayList.toArray(new Type[ArrayList.size()]);
		
		return objects.toArray(new VisionObject[objects.size()]);
	}
}
