package test;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvGet2D;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2HSV;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_core.*;


public class Num_Recog {
	
	boolean one=false, two=false, three=false, four=false, five=false, 
			six=false, seven=false, eight=false, nine=false, ten=false;

	public void Number1(IplImage img) throws InterruptedException, IOException {
		
		//process the image
		Process(img);
		
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
	    Mat template = Highgui.imread("train_data/1_2.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
		
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		
		 MinMaxLocResult mmr = Core.minMaxLoc(result);
		 Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 7; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 one = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect, new Scalar(127, 0, 255), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	

	} //end method Number1
	
public void Number2(IplImage img) throws InterruptedException, IOException {
		
	//process the image
	Process(img);
			
			System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
			Mat src = Highgui.imread("binary.jpg",0);
		    Mat template = Highgui.imread("train_data/2_1.jpg",0);
			Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
			
			Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
			
			 MinMaxLocResult mmr = Core.minMaxLoc(result);
			 Point matchLoc = mmr.minLoc;
			 
			 //min value gives the best match for TM_SQDIFF method
			 double VAL = mmr.minVal/1000000;		 	 
			 double THRESHOLD = 4; //set a threshold to determine how good the match is
			 
			 if(VAL < THRESHOLD) {
				 System.out.println("Match is found -> " + VAL);
				 two = true;
			 }
			 else
				 System.out.println("Match not found -> " + VAL);
			 
			 src = Highgui.imread("brighten.jpg");
			 
			 //draw a rectangle around the matched area
			 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
			 Core.rectangle(src, matchLoc, rect, new Scalar(127, 0, 255), 2);
			 
			 Highgui.imwrite("match.jpg", src);	//save the image	

	} //end method Number2

public void Number3(IplImage img) throws InterruptedException, IOException {
	
	//process the image
		Process(img);
				
				System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
				Mat src = Highgui.imread("binary.jpg",0);
			    Mat template = Highgui.imread("train_data/3_2.jpg",0);
				Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
				Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
				
				 MinMaxLocResult mmr = Core.minMaxLoc(result);
				 Point matchLoc = mmr.minLoc;
				 
				 //min value gives the best match for TM_SQDIFF method
				 double VAL = mmr.minVal/1000000;		 	 
				 double THRESHOLD = 13.0; //set a threshold to determine how good the match is
				 
				 if(VAL < THRESHOLD) {
					 System.out.println("Match is found -> " + VAL);
					 three = true;
				 }
				 else
					 System.out.println("Match not found -> " + VAL);
				 
				 src = Highgui.imread("brighten.jpg");
				 
				 //draw a rectangle around the matched area
				 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
				 Core.rectangle(src, matchLoc, rect, new Scalar(127, 0, 255), 2);
				 
				 Highgui.imwrite("match.jpg", src);	//save the image

} //end method Number3

public void Number4(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
	
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
    Mat template = Highgui.imread("train_data/4_3.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
	
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	
	 MinMaxLocResult mmr = Core.minMaxLoc(result);
	 Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 0.6; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 four = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect, new Scalar(127, 0, 255), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image

} //end method Number4

public void Number5(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
	
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
    Mat template = Highgui.imread("train_data/5_2.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
	
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	
	 MinMaxLocResult mmr = Core.minMaxLoc(result);
	 Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 5; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 five = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect, new Scalar(127, 0, 255), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image

} //end method Number5

public void Number6(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
	
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	
	//compare using 2 templates
    Mat template = Highgui.imread("train_data/6_1.jpg",0);
    Mat template2 = Highgui.imread("train_data/6_2.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
	Mat result2 = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
	
	//match both templates
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	Imgproc.matchTemplate(src, template2, result2, Imgproc.TM_SQDIFF);
	
	 MinMaxLocResult mmr = Core.minMaxLoc(result);
	 Point matchLoc = mmr.minLoc;
	 MinMaxLocResult mmr2 = Core.minMaxLoc(result2);
	 Point matchLoc2 = mmr2.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;
	 double VAL2 = mmr2.minVal/1000000;
	 double THRESHOLD = 3; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD && VAL2 < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL + " -> " + VAL2);
		 six = true;
	 }
	 else {
		System.out.println("Match not found -> " + VAL + " -> " + VAL2);
	 }
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw 2 rectangles around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect, new Scalar(127, 0, 255), 2);
	 Point rect2 = new Point(matchLoc2.x + template2.cols(), matchLoc2.y + template2.rows());		 
	 Core.rectangle(src, matchLoc2, rect2, new Scalar(127, 0, 255), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image

} //end method Number6

public void Number7(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
	
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
    Mat template = Highgui.imread("train_data/7_2.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
	
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	
	 MinMaxLocResult mmr = Core.minMaxLoc(result);
	 Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 9; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 seven = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect, new Scalar(127, 0, 255), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image

} //end method Number7

public void Number8(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
	
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
    Mat template = Highgui.imread("train_data/8_1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
	
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	
	 MinMaxLocResult mmr = Core.minMaxLoc(result);
	 Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 2.6; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 eight = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect, new Scalar(127, 0, 255), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image

} //end method Number8

public void Number9(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
	
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
    Mat template = Highgui.imread("train_data/9_3.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
	
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	
	 MinMaxLocResult mmr = Core.minMaxLoc(result);
	 Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 9; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 nine = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect, new Scalar(127, 0, 255), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image

} //end method Number9

public void Number10(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
	
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	
	//read 2 training data for number 10
    Mat template = Highgui.imread("train_data/10_1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
    Mat template2 = Highgui.imread("train_data/10_12.jpg",0);
	Mat result2 = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
	
	//match both templates
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	Imgproc.matchTemplate(src, template2, result2, Imgproc.TM_SQDIFF);
	
	 MinMaxLocResult mmr = Core.minMaxLoc(result);
	 Point matchLoc = mmr.minLoc;
	 MinMaxLocResult mmr2 = Core.minMaxLoc(result2);
	 Point matchLoc2 = mmr2.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;
	 double VAL2 = mmr2.minVal/1000000;
	 double THRESHOLD = 2.5; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD && VAL2 < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL + " -> " + VAL2);
		 ten = true;
	 }
	 else {
		System.out.println("Match not found -> " + VAL + " -> " + VAL2);
	 }
	 
	 src = Highgui.imread("brighten.jpg");

	 //draw rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect, new Scalar(127, 0, 255), 2);
	 Point rect2 = new Point(matchLoc2.x + template2.cols(), matchLoc2.y + template2.rows());		 
	 Core.rectangle(src, matchLoc2, rect2, new Scalar(127, 0, 255), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image

} //end method Number10
	
	public void Process(IplImage img) throws InterruptedException, IOException {
		
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
		
		/*Read brighten image*/
		JPEGImage srcImg = new JPEGImage();
		srcImg.read("brighten.jpg");
		Thread.sleep(200);
		
		/* Convert image into binary */
		for (int x = 0; x < srcImg.getWidth(); x++) {
		    for (int y = 0; y < srcImg.getHeight(); y++) {
		    	
		/* Get the RGB values from imageOne */
		    	int red = srcImg.getRed(x,y);
				int green = srcImg.getGreen(x,y);
				int blue = srcImg.getBlue(x,y);
				
		/* determine the grey values */
		    	int grey = (red+green+blue)/3;
		    	
		/* Put binary scale into the grayscale image */
		    	if(grey>128)
		    		srcImg.setRGB(x, y, 255, 255, 255); //white
		    	else
		    		srcImg.setRGB(x, y, 0, 0, 0); //black
			}
		}//end for loop
		
		//save the image
		srcImg.write("binary.jpg");
		Thread.sleep(200);	
		
	} //end method Process
	
	public boolean is1() {
		return one;
	}
	public boolean is2() {
		return two;
	}
	public boolean is3() {
		return three;
	}
	public boolean is4() {
		return four;
	}
	public boolean is5() {
		return five;
	}
	public boolean is6() {
		return six;
	}
	public boolean is7() {
		return seven;
	}
	public boolean is8() {
		return eight;
	}
	public boolean is9() {
		return nine;
	}
	public boolean is10() {
		return ten;
	}

} //end class Num_Recog
