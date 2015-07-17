package test;

import static org.bytedeco.javacpp.helper.opencv_imgproc.cvFindContours;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.CvPoint3D32f;
import static org.bytedeco.javacpp.opencv_core.cvPointFrom32f;
import static org.bytedeco.javacpp.opencv_core.cvPoint2D32f;
import static org.bytedeco.javacpp.opencv_core.CV_AA;
import static org.bytedeco.javacpp.opencv_core.CV_WHOLE_SEQ;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGet2D;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_core.cvRectangle;
import static org.bytedeco.javacpp.opencv_core.cvDrawContours;
import static org.bytedeco.javacpp.opencv_core.cvSize;

import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;

import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2HSV;
import static org.bytedeco.javacpp.opencv_imgproc.CV_CHAIN_APPROX_SIMPLE;
import static org.bytedeco.javacpp.opencv_imgproc.CV_POLY_APPROX_DP;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RETR_LIST;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvApproxPoly;
import static org.bytedeco.javacpp.opencv_imgproc.cvCanny;
import static org.bytedeco.javacpp.opencv_imgproc.cvContourArea;
import static org.bytedeco.javacpp.opencv_imgproc.cvContourPerimeter;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvPointSeqFromMat;
import static org.bytedeco.javacpp.opencv_imgproc.cvHoughCircles;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core.CvContour;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.CvPoint2D32f;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class ShapeDetection {

boolean rect = false, triangle = false, circ = false, star = false;
	
public void Rectangle(IplImage img) throws InterruptedException, IOException {
	
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	
	//Brighten the image using imgScalr
	final RescaleOp OP_BRIGHTER = new RescaleOp(1.7f, 0, null);
	final RescaleOp OP_BRIGHTER_EXTRA = new RescaleOp(3.3f, 0, null);
	
	//Convert image to HSV 
	IplImage hsvImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 3);
	cvCvtColor(img, hsvImg, CV_BGR2HSV);
	Thread.sleep(200); //wait a moment to convert the image fully before processing it
	
	int counter = 0; 
	double luminance = 0;
	
	for(int x=1; x<hsvImg.width()-1; x++) {
		for(int y=1; y<hsvImg.height()-1; y++) {
			
			CvScalar l = cvGet2D(hsvImg,y,x);
			luminance += l.val(2); //total the values of V in HSV in every pixel
			counter++; //counting the total no of pixel
		}
	}
	int lumAvg = (int)luminance/counter; //calculate the average V values to estimate
										//the level of image brightness
	
	//Load the image as a BufferedImage
	BufferedImage image = ImageIO.read(new File("capture.jpg"));
	
	if(image!=null) {
	
		/* Enhance image based on the luminance */
		if(lumAvg<=105) { //lower brightness 
			OP_BRIGHTER_EXTRA.filter(image, image);
			System.out.println("luminance: " + lumAvg);
		}
		else if(lumAvg>105) { //higher brightness
			OP_BRIGHTER.filter(image, image);
			System.out.println("luminance: " + lumAvg);
		}
		
		OutputStream ostream = new FileOutputStream("brighten.jpg");
	    ImageIO.write(image, "jpg", ostream); //save the enhanced image
	    Thread.sleep(200); //wait
	}// end if image!=null
	
    //Load enhanced image in Mat format
    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat savedImg = Highgui.imread("brighten.jpg"); 	
	
	Mat dest = new Mat(savedImg.rows(),savedImg.cols(),savedImg.type()); //create destination image in Mat format
	Imgproc.GaussianBlur(savedImg,dest,new Size(9,9),0.0); //apply gaussian smoothing
	Highgui.imwrite("smoothed.jpg", dest); //then save the image
	Thread.sleep(200); //wait
	
	IplImage smoothed = cvLoadImage("smoothed.jpg");
	IplImage gray = IplImage.create(smoothed.width(),smoothed.height(),IPL_DEPTH_8U,1);
	cvCvtColor(smoothed, gray, CV_RGB2GRAY); //convert to grayscale

	IplImage edge = cvCreateImage(cvSize(gray.width(),gray.height()),IPL_DEPTH_8U,1);
	cvCanny(gray, edge, 0, 255); //canny edge detector
	cvSaveImage("canny.jpg",edge); 
	
	/*Find contours*/
	CvMemStorage mem = CvMemStorage.create();
	CvSeq contours = new CvSeq();									
	cvFindContours(edge.clone(), mem, contours, Loader.sizeof(CvContour.class),
				   CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE ); //should try cv_retr_external!!!!
	
	CvSeq ptr = new CvSeq();
	
	for(int i=0; i<contours.sizeof(); i++) {
		//approximate the contour with accuracy proportional to the contour perimeter	
		ptr = cvApproxPoly(contours, Loader.sizeof(CvContour.class), mem, 
				     CV_POLY_APPROX_DP, cvContourPerimeter(contours)*0.02, 0);
		
		//if there are 4 vertices and area of rectangle is more than 50 pixels
		if(ptr.total()==4 && Math.abs(cvContourArea(ptr,CV_WHOLE_SEQ,0))>50) {
			
			cvDrawContours(img, ptr, CvScalar.YELLOW, CvScalar.YELLOW, -1, 3 /*CV_FILLED*/, CV_AA );
			cvSaveImage("rect.jpg", img); //save the image
			
			rect = true;
		} //end if
	} //end for
		
} //end method Rectangle

public void Triangle(IplImage img) throws InterruptedException, IOException {
	
//System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	
	//Brighten the image using imgScalr
	final RescaleOp OP_BRIGHTER = new RescaleOp(1.7f, 0, null);
	final RescaleOp OP_BRIGHTER_EXTRA = new RescaleOp(3.3f, 0, null);
	
	//Convert image to HSV 
	IplImage hsvImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 3);
	cvCvtColor(img, hsvImg, CV_BGR2HSV);
	Thread.sleep(200); //wait a moment to convert the image fully before processing it
	
	int counter = 0; 
	double luminance = 0;
	
	for(int x=1; x<hsvImg.width()-1; x++) {
		for(int y=1; y<hsvImg.height()-1; y++) {
			
			CvScalar l = cvGet2D(hsvImg,y,x);
			luminance += l.val(2); //total the values of V in HSV in every pixel
			counter++; //counting the total no of pixel
		}
	}
	int lumAvg = (int)luminance/counter; //calculate the average V values to estimate
										//the level of image brightness
	
	//Load the image as a BufferedImage
	BufferedImage image = ImageIO.read(new File("capture.jpg"));
	
	if(image!=null) {
	
		/* Enhance image based on the luminance */
		if(lumAvg<=105) { //lower brightness 
			OP_BRIGHTER_EXTRA.filter(image, image);
			System.out.println("luminance: " + lumAvg);
		}
		else if(lumAvg>105) { //higher brightness
			OP_BRIGHTER.filter(image, image);
			System.out.println("luminance: " + lumAvg);
		}
		
		OutputStream ostream = new FileOutputStream("brighten.jpg");
	    ImageIO.write(image, "jpg", ostream); //save the enhanced image
	    Thread.sleep(200); //wait
	}// end if image!=null
	
	//Load enhanced image in Mat format
    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat savedImg = Highgui.imread("brighten.jpg"); 	
	
	Mat dest = new Mat(savedImg.rows(),savedImg.cols(),savedImg.type()); //create destination image in Mat format
	Imgproc.GaussianBlur(savedImg,dest,new Size(9,9),0.0); //apply gaussian smoothing
	Highgui.imwrite("smoothed.jpg", dest); //then save the image
	Thread.sleep(200); //wait
	
	IplImage smoothed = cvLoadImage("smoothed.jpg");
	IplImage gray = IplImage.create(smoothed.width(),smoothed.height(),IPL_DEPTH_8U,1);
	cvCvtColor(smoothed, gray, CV_RGB2GRAY); //convert to grayscale

	IplImage edge = cvCreateImage(cvSize(gray.width(),gray.height()),IPL_DEPTH_8U,1);
	cvCanny(gray, edge, 0, 255); //canny edge detector
	cvSaveImage("canny.jpg",edge); 
	
	/*Find contours*/
	CvMemStorage mem = CvMemStorage.create();
	CvSeq contours = new CvSeq();									
	cvFindContours(edge.clone(), mem, contours, Loader.sizeof(CvContour.class),
			CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE );
	
	CvSeq ptr = new CvSeq();
	
	for(int i=0; i<contours.sizeof(); i++) {
		//approximate the contour with accuracy proportional to the contour perimeter	
		ptr = cvApproxPoly(contours, Loader.sizeof(CvContour.class), mem, 
				     CV_POLY_APPROX_DP, cvContourPerimeter(contours)*0.02, 0);
		
		//if there are 3 vertices and area of triangle is more than 50 pixels
		if(ptr.total()==3 && Math.abs(cvContourArea(ptr,CV_WHOLE_SEQ,0))>50) {
			
			cvDrawContours(img, ptr, CvScalar.YELLOW, CvScalar.YELLOW, -1, 3 /*CV_FILLED*/, CV_AA );
			cvSaveImage("triangle.jpg", img); //save the image
			
			triangle = true;
		} //end if
	} //end for	
	
}//end method Triangle

public void Circle(IplImage img) throws InterruptedException, IOException {
	
	//Brighten the image using imgScalr
		final RescaleOp OP_BRIGHTER = new RescaleOp(1.7f, 0, null);
		final RescaleOp OP_BRIGHTER_EXTRA = new RescaleOp(3.3f, 0, null);
		
		//Convert image to HSV 
		IplImage hsvImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 3);
		cvCvtColor(img, hsvImg, CV_BGR2HSV);
		Thread.sleep(200); //wait a moment to convert the image fully before processing it
		
		int counter = 0; 
		double luminance = 0;
		
		for(int x=1; x<hsvImg.width()-1; x++) {
			for(int y=1; y<hsvImg.height()-1; y++) {
				
				CvScalar l = cvGet2D(hsvImg,y,x);
				luminance += l.val(2); //total the values of V in HSV in every pixel
				counter++; //counting the total no of pixel
			}
		}
		int lumAvg = (int)luminance/counter; //calculate the average V values to estimate
											//the level of image brightness
		
		//Load the image as a BufferedImage
		BufferedImage image = ImageIO.read(new File("capture.jpg"));
		
		if(image!=null) {
		
			/* Enhance image based on the luminance */
			if(lumAvg<=105) { //lower brightness 
				OP_BRIGHTER_EXTRA.filter(image, image);
				System.out.println("luminance: " + lumAvg);
			}
			else if(lumAvg>105) { //higher brightness
				OP_BRIGHTER.filter(image, image);
				System.out.println("luminance: " + lumAvg);
			}
			
			OutputStream ostream = new FileOutputStream("brighten.jpg");
		    ImageIO.write(image, "jpg", ostream); //save the enhanced image
		    Thread.sleep(200); //wait
		}// end if image!=null
	
		//Convert to grayscale
		IplImage gray = IplImage.create(img.width(),img.height(),IPL_DEPTH_8U,1);
		cvCvtColor(img, gray, CV_RGB2GRAY);
		cvSaveImage("grayscale.jpg", gray); //save the image
		Thread.sleep(200); //wait
		
		//Load enhanced image in Mat format
	    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat savedImg = Highgui.imread("grayscale.jpg"); 
		Mat dest = new Mat(savedImg.rows(),savedImg.cols(),savedImg.type()); //create destination image in Mat format
		Imgproc.GaussianBlur(savedImg,dest,new Size(9,9),0.0); //apply gaussian smoothing
		Highgui.imwrite("smoothed.jpg", dest); //then save the image
		Thread.sleep(200); //wait
		
		//Load smoothed image in IplImage format
		IplImage smoothed = cvLoadImage("smoothed.jpg");
		
		//detecting circle
		CvMemStorage mem = CvMemStorage.create();
		
		IplImage edge = cvCreateImage(cvSize(smoothed.width(),smoothed.height()),IPL_DEPTH_8U,1);
		cvCanny(gray, edge, 0, 255); //canny edge detector
		cvSaveImage("canny.jpg",edge); 
		
		/*Find contours*/
		CvSeq contours = new CvSeq();									
		cvFindContours(edge.clone(), mem, contours, Loader.sizeof(CvContour.class),
				CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE );
		
		CvSeq ptr = new CvSeq();
		
		for(int i=0; i<contours.sizeof(); i++) {
			//approximate the contour with accuracy proportional to the contour perimeter	
			ptr = cvApproxPoly(contours, Loader.sizeof(CvContour.class), mem, 
					     CV_POLY_APPROX_DP, cvContourPerimeter(contours)*0.02, 0);
			
			//if there are more than 6 vertices and area of circle is more than 50 pixels
			if((ptr.total()>6 && ptr.total()!=10) && Math.abs(cvContourArea(ptr,CV_WHOLE_SEQ,0))>50) {
				
				cvDrawContours(img, ptr, CvScalar.YELLOW, CvScalar.YELLOW, -1, 3 /*CV_FILLED*/, CV_AA );
				cvSaveImage("circle.jpg", img); //save the image
				
				circ = true;
			} //end if
		} //end for			
}//end method Circle

public void Star(IplImage img) throws InterruptedException, IOException {
	
	//Brighten the image using imgScalr
		final RescaleOp OP_BRIGHTER = new RescaleOp(1.7f, 0, null);
		final RescaleOp OP_BRIGHTER_EXTRA = new RescaleOp(3.3f, 0, null);
		
		//Convert image to HSV 
		IplImage hsvImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 3);
		cvCvtColor(img, hsvImg, CV_BGR2HSV);
		Thread.sleep(200); //wait a moment to convert the image fully before processing it
		
		int counter = 0; 
		double luminance = 0;
		
		for(int x=1; x<hsvImg.width()-1; x++) {
			for(int y=1; y<hsvImg.height()-1; y++) {
				
				CvScalar l = cvGet2D(hsvImg,y,x);
				luminance += l.val(2); //total the values of V in HSV in every pixel
				counter++; //counting the total no of pixel
			}
		}
		int lumAvg = (int)luminance/counter; //calculate the average V values to estimate
											//the level of image brightness
		
		//Load the image as a BufferedImage
		BufferedImage image = ImageIO.read(new File("capture.jpg"));
		
		if(image!=null) {
		
			/* Enhance image based on the luminance */
			if(lumAvg<=105) { //lower brightness 
				OP_BRIGHTER_EXTRA.filter(image, image);
				System.out.println("luminance: " + lumAvg);
			}
			else if(lumAvg>105) { //higher brightness
				OP_BRIGHTER.filter(image, image);
				System.out.println("luminance: " + lumAvg);
			}
			
			OutputStream ostream = new FileOutputStream("brighten.jpg");
		    ImageIO.write(image, "jpg", ostream); //save the enhanced image
		    Thread.sleep(200); //wait
		}// end if image!=null
	
		//Convert to grayscale
		IplImage gray = IplImage.create(img.width(),img.height(),IPL_DEPTH_8U,1);
		cvCvtColor(img, gray, CV_RGB2GRAY);
		cvSaveImage("grayscale.jpg", gray); //save the image
		Thread.sleep(200); //wait
		
		//Load enhanced image in Mat format
	    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat savedImg = Highgui.imread("grayscale.jpg"); 
		Mat dest = new Mat(savedImg.rows(),savedImg.cols(),savedImg.type()); //create destination image in Mat format
		Imgproc.GaussianBlur(savedImg,dest,new Size(9,9),0.0); //apply gaussian smoothing
		Highgui.imwrite("smoothed.jpg", dest); //then save the image
		Thread.sleep(200); //wait
		
		//Load smoothed image in IplImage format
		IplImage smoothed = cvLoadImage("smoothed.jpg");
		
		//detecting star shape
		CvMemStorage mem = CvMemStorage.create();
		
		IplImage edge = cvCreateImage(cvSize(smoothed.width(),smoothed.height()),IPL_DEPTH_8U,1);
		cvCanny(gray, edge, 0, 255); //canny edge detector
		cvSaveImage("canny.jpg",edge); 
		
		/*Find contours*/
		//CvMemStorage mem = CvMemStorage.create();
		CvSeq contours = new CvSeq();									
		cvFindContours(edge.clone(), mem, contours, Loader.sizeof(CvContour.class),
				CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE );
		
		CvSeq ptr = new CvSeq();
		
		for(int i=0; i<contours.sizeof(); i++) {
			//approximate the contour with accuracy proportional to the contour perimeter	
			ptr = cvApproxPoly(contours, Loader.sizeof(CvContour.class), mem, 
					     CV_POLY_APPROX_DP, cvContourPerimeter(contours)*0.03, 0);
			
			//if there are 10 vertices and area of star is more than 50 pixels
			if(ptr.total()==10 && Math.abs(cvContourArea(ptr,CV_WHOLE_SEQ,0))>50) {
				
				cvDrawContours(img, ptr, CvScalar.YELLOW, CvScalar.YELLOW, -1, 3 /*CV_FILLED*/, CV_AA );
				cvSaveImage("star.jpg", img); //save the image
				
				star = true;
			} //end if
		} //end for			
}//end method Circle

public boolean isRect() {
	return rect;
}
public boolean isTri() {
	return triangle;
}
public boolean isCirc() {
	return circ;
}
public boolean isStar() {
	return star;
}

}//end class
