package test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2HSV;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;

public class ColorDetection {
	 
boolean red = false, green = false, blue = false, yellow = false, purple = false; //indicator
BufferedImage image = null, image2 = null;

	public void DetectColorRed(BufferedImage img) throws IOException, InterruptedException {
		
		int pixelCount = 0; //counter for detected pixel color in the image
		
		//Brighten the image using imgScalr
		final RescaleOp OP_BRIGHTER = new RescaleOp(1.7f, 0, null);
		final RescaleOp OP_BRIGHTER_EXTRA = new RescaleOp(3.3f, 0, null);
		final RescaleOp OP_LESS_BRIGHT = new RescaleOp(0.3f, 0, null);
		
		/* Load the image & convert it to HSV */
		IplImage captured = cvLoadImage("capture.jpg");
		IplImage hsvImg = null;
		
		if(captured!=null) {
			hsvImg = IplImage.create(captured.width(), captured.height(), IPL_DEPTH_8U, 3);
			cvCvtColor(captured, hsvImg, CV_BGR2HSV);
			Thread.sleep(300); //wait a moment to convert the image fully before processing it

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
			
			/* Enhance image based on the luminance */
			if(lumAvg<=105) { //lower brightness 
				OP_BRIGHTER_EXTRA.filter(img, img);
				System.out.println("luminance: " + lumAvg);
			}
			else if(lumAvg>105) { //higher brightness
				OP_BRIGHTER.filter(img, img);
				System.out.println("luminance: " + lumAvg);
			}
			else if(lumAvg>115) { //very higher brightness
				OP_LESS_BRIGHT.filter(img, img);
				System.out.println("luminance: " + lumAvg);
			}
				
			OutputStream ostream = new FileOutputStream("brighten.jpg");
		    ImageIO.write(img, "jpg", ostream); //save the enhanced image
		    Thread.sleep(300); //wait
		}
	
		//perform color detection on captured image
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat savedImg = Highgui.imread("brighten.jpg"); //load enhanced image in Mat format
		
		if(savedImg != null) {
			
			Mat dest = new Mat(savedImg.rows(),savedImg.cols(),savedImg.type()); //create destination image in Mat format
			Imgproc.GaussianBlur(savedImg,dest,new Size(15,15),0.0); //apply gaussian smoothing
			Highgui.imwrite("smoothed.jpg", dest); //then save the image
		
		IplImage smoothed = cvLoadImage("smoothed.jpg"); //load the smoothed image in IplImage format
			
		if(smoothed!=null) {
			
			IplImage imgThreshold = cvCreateImage(cvGetSize(smoothed), 8, 1); 
			CvScalar max = cvScalar(102, 102, 255, 0);//max range of red
			CvScalar min = cvScalar(0, 0, 102, 0);//min range of red
			
			
			cvInRangeS(smoothed, min, max, imgThreshold);// apply thresholding          
	        cvSaveImage("threshold.jpg", imgThreshold); //save the image
		}
		
		//then read the thresholded image
		try{
			img = ImageIO.read(new File("threshold.jpg"));
			int col; 
			image = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB); //create a new image to show only the red pixels
			image2 = ImageIO.read(new File("brighten.jpg")); //reference image
		
			for(int i=0; i<img.getWidth(); i++){
		        for(int j=0; j<img.getHeight(); j++) {
		        	
		             Color c = new Color(img.getRGB(i,j));
		                int red = c.getRed();
		                int green = c.getGreen();
		                int blue = c.getBlue();
		                
		             Color c2 = new Color(image2.getRGB(i, j)); //second color extracted from the original img 
		            	int r = c2.getRed(); //image's red pixel
		            	int g = c2.getGreen();
		            	int b = c2.getBlue();
		                col = (r << 16) | (g << 8) | b;
		                
		                if(red==255 && blue==255 && green==255) {
		                	pixelCount ++; //count the white pixel
		                	image.setRGB(i, j, col);	                
		                }   
		          }
		       }//end for
			
			File outputfile = new File("ColouredImg.jpg");
			ImageIO.write(image, "jpg", outputfile); //save the coloured image
		
		}	catch (Exception e) {
	         System.out.println("");
			}		
	   }//end if image captured != null
		
		if(pixelCount>=15000) {
	    	System.out.println("red detected = " + pixelCount);
	    	red = true;
		}
		else
			System.out.println("no red detected = " + pixelCount);
		
	}//end DetectColorRed method

	public void DetectColorGreen(BufferedImage img) throws IOException, InterruptedException {
		
		int pixelCount = 0; //counter for detected pixel color in the image
		
		//Brighten the image using imgScalr
		final RescaleOp OP_BRIGHTER = new RescaleOp(1.7f, 0, null);
		final RescaleOp OP_BRIGHTER_EXTRA = new RescaleOp(3.3f, 0, null);
		final RescaleOp OP_LESS_BRIGHT = new RescaleOp(0.3f, 0, null);
		
		/* Load the image & convert it to HSV */
		IplImage captured = cvLoadImage("capture.jpg");
		IplImage hsvImg = null;
		
		if(captured!=null) {
			hsvImg = IplImage.create(captured.width(), captured.height(), IPL_DEPTH_8U, 3);
			cvCvtColor(captured, hsvImg, CV_BGR2HSV);
			Thread.sleep(300);
			
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
		
		/* Enhance image based on the luminance */
		if(lumAvg<=105) { //lower brightness 
			OP_BRIGHTER_EXTRA.filter(img, img);
			System.out.println("luminance: " + lumAvg);
		}
		else if(lumAvg>105) { //higher brightness
			OP_BRIGHTER.filter(img, img);
			System.out.println("luminance: " + lumAvg);
		}
		else if(lumAvg>115) { //very higher brightness
			OP_LESS_BRIGHT.filter(img, img);
			System.out.println("luminance: " + lumAvg);
		}

		OutputStream ostream = new FileOutputStream("brighten.jpg");
	    ImageIO.write(img, "jpg", ostream); //save the enhanced image
	    Thread.sleep(300);
	}
		
		//perform color detection on captured image
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat savedImg = Highgui.imread("brighten.jpg"); //load enhanced image in Mat format
		
		if(savedImg != null) {
			
			Mat dest = new Mat(savedImg.rows(),savedImg.cols(),savedImg.type()); //create destination image in Mat format
			Imgproc.GaussianBlur(savedImg,dest,new Size(15,15),0.0); //apply gaussian smoothing
			Highgui.imwrite("smoothed.jpg", dest); //then save the image
		
		IplImage smoothed = cvLoadImage("smoothed.jpg"); //load the smoothed image in IplImage format
			
		if(smoothed!=null) {
			
			IplImage imgThreshold = cvCreateImage(cvGetSize(smoothed), 8, 1); 
			CvScalar max = cvScalar(102, 255, 178, 0);//max range of green 
			CvScalar min = cvScalar(0, 102, 51, 0);//min range of green
			
			cvInRangeS(smoothed, min, max, imgThreshold);// apply thresholding          
	        cvSaveImage("threshold.jpg", imgThreshold); //save the image
		}
		//then read the thresholded image
		try{
			img = ImageIO.read(new File("threshold.jpg"));
			int col; 
			image = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB); //create a new image to show only the red pixels
			image2 = ImageIO.read(new File("brighten.jpg")); //reference image
		
			for(int i=0; i<img.getWidth(); i++){
		        for(int j=0; j<img.getHeight(); j++) {
		        	
		             Color c = new Color(img.getRGB(i,j));
		                int red = c.getRed();
		                int green = c.getGreen();
		                int blue = c.getBlue();
		                
		                Color c2 = new Color(image2.getRGB(i, j)); //second color extracted from the original img 
		            	int r = c2.getRed(); //image's red pixel
		            	int g = c2.getGreen();
		            	int b = c2.getBlue();
		                col = (r << 16) | (g << 8) | b;
		                
		                if(red==255 && blue==255 && green==255) {
		                	pixelCount ++; //count the white pixel
		                	image.setRGB(i, j, col);
		                }   
		          }
		       }//end for
			
	    	File outputfile = new File("ColouredImg.jpg");
			ImageIO.write(image, "jpg", outputfile); //save the coloured image
			
			if(pixelCount>=15000) {
		    	System.out.println("green detected = " + pixelCount);
		    	green = true;
			}
			else { //second range of green to be detected
				pixelCount = 0;
				IplImage imgThreshold = cvCreateImage(cvGetSize(smoothed), 8, 1); 
				CvScalar max = cvScalar(102, 255, 102, 0);//max range of green
				CvScalar min = cvScalar(0, 102, 0, 0);//min range of green
				
				cvInRangeS(smoothed, min, max, imgThreshold);// apply thresholding          
		        cvSaveImage("threshold.jpg", imgThreshold); //save the image
		        
		        img = ImageIO.read(new File("threshold.jpg"));
		        image = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB); //create a new image to show only the red pixels
				
				for(int i=0; i<img.getWidth(); i++){
			        for(int j=0; j<img.getHeight(); j++) {
			        	
			             Color c = new Color(img.getRGB(i,j));
			                int red = c.getRed();
			                int green = c.getGreen();
			                int blue = c.getBlue();
			       
			             Color c2 = new Color(image2.getRGB(i, j)); //second color extracted from the original img
			            	int r = c2.getRed(); //image's red pixel
			            	int g = c2.getGreen();
			            	int b = c2.getBlue();
			                col = (r << 16) | (g << 8) | b;
			                
			                if(red==255 && blue==255 && green==255) {
			                	pixelCount ++; //count the white pixel
			                	image.setRGB(i, j, col);
			                }   
			          }
			       }//end for
				
		    	outputfile = new File("ColouredImg.jpg");
				ImageIO.write(image, "jpg", outputfile); //save the coloured image
		        
		        if(pixelCount>=15000) {
		        	System.out.println("green detected = " + pixelCount);
			    	green = true;
		        }
		        else
		        	System.out.println("no green detected = " + pixelCount);
			}
		
		}	catch (Exception e) {
	         System.out.println("");
			}		
	   }//end if image captured != null

	}//end DetectColorGreen method	
	
	public void DetectColorBlue(BufferedImage img) throws IOException, InterruptedException {
	
	int pixelCount = 0; //counter for detected pixel color in the image
	
	//Brighten the image using imgScalr
	final RescaleOp OP_BRIGHTER = new RescaleOp(1.7f, 0, null);
	final RescaleOp OP_BRIGHTER_EXTRA = new RescaleOp(3.3f, 0, null);
	final RescaleOp OP_LESS_BRIGHT = new RescaleOp(0.3f, 0, null);
	
	/* Load the image & convert it to HSV */
	IplImage captured = cvLoadImage("capture.jpg");
	IplImage hsvImg = null;
	
	if(captured!=null) {
		hsvImg = IplImage.create(captured.width(), captured.height(), IPL_DEPTH_8U, 3);
		cvCvtColor(captured, hsvImg, CV_BGR2HSV);
		Thread.sleep(300);

		int counter = 0; 
		double luminance = 0;
		
		for(int x=1; x<hsvImg.width()-1; x++) {
			for(int y=1; y<hsvImg.height()-1; y++) {
				
				CvScalar l = cvGet2D(hsvImg,y,x);
				luminance += l.val(2); //total the values of V in HSV in every pixel
				counter++; //counting the total no of pixel
			}
		}//end for
		int lumAvg = (int)luminance/counter; //calculate the average V values to estimate
											//the level of image brightness
		
		/* Enhance image based on the luminance */
		if(lumAvg<=105) { //lower brightness 
			OP_BRIGHTER_EXTRA.filter(img, img);
			System.out.println("luminance: " + lumAvg);
		}
		else if(lumAvg>105) { //higher brightness
			OP_BRIGHTER.filter(img, img);
			System.out.println("luminance: " + lumAvg);
		}
		else if(lumAvg>115) { //very higher brightness
			OP_LESS_BRIGHT.filter(img, img);
			System.out.println("luminance: " + lumAvg);
		}
		
		OutputStream ostream = new FileOutputStream("brighten.jpg");
	    ImageIO.write(img, "jpg", ostream); //save the enhanced image
	    Thread.sleep(300);
	}
	
	//perform color detection on captured image
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat savedImg = Highgui.imread("brighten.jpg"); //load enhanced image in Mat format
	
	if(savedImg != null) {
		
		Mat dest = new Mat(savedImg.rows(),savedImg.cols(),savedImg.type()); //create destination image in Mat format
		Imgproc.GaussianBlur(savedImg,dest,new Size(15,15),0.0); //apply gaussian smoothing
		Highgui.imwrite("smoothed.jpg", dest); //then save the image
	
	IplImage smoothed = cvLoadImage("smoothed.jpg"); //load the smoothed image in IplImage format
		
	if(smoothed!=null) {
		
		IplImage imgThreshold = cvCreateImage(cvGetSize(smoothed), 8, 1); 
		CvScalar max = cvScalar(255, 178, 102, 0);//max range of blue
		CvScalar min = cvScalar(102, 51, 0, 0);//min range of blue
		
		cvInRangeS(smoothed, min, max, imgThreshold);// apply thresholding          
        cvSaveImage("threshold.jpg", imgThreshold); //save the image
	}
	//then read the thresholded image
	try{
		img = ImageIO.read(new File("threshold.jpg"));
		int col; 
		image = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB); //create a new image to show only the red pixels
		image2 = ImageIO.read(new File("brighten.jpg")); //reference image
	
		for(int i=0; i<img.getWidth(); i++) {
	        for(int j=0; j<img.getHeight(); j++) {
	        	
	             Color c = new Color(img.getRGB(i,j));
	                int red = c.getRed();
	                int green = c.getGreen();
	                int blue = c.getBlue();
	                
	                Color c2 = new Color(image2.getRGB(i, j)); //second color extracted from the original img
	            	int r = c2.getRed(); //image's red pixel
	            	int g = c2.getGreen();
	            	int b = c2.getBlue();
	                col = (r << 16) | (g << 8) | b;
	                
	                if(red==255 && blue==255 && green==255) {
	                	pixelCount ++; //count the white pixel
	                	image.setRGB(i, j, col);
	                }   
	          }
	       }//end for
		
    	File outputfile = new File("ColouredImg.jpg");
		ImageIO.write(image, "jpg", outputfile); //save the coloured image
		
		if(pixelCount>=15000) {
	    	System.out.println("blue detected = " + pixelCount);
	    	blue = true;
		}
		else { //second range of blue
			pixelCount = 0;
			IplImage imgThreshold = cvCreateImage(cvGetSize(smoothed), 8, 1); 
			CvScalar max = cvScalar(255, 102, 102, 0);//max range of blue
			CvScalar min = cvScalar(102, 0, 0, 0);//min range of blue
			
			cvInRangeS(smoothed, min, max, imgThreshold);// apply thresholding          
	        cvSaveImage("threshold.jpg", imgThreshold); //save the image
	        
	        Thread.sleep(300);
	        img = ImageIO.read(new File("threshold.jpg"));
	        image = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB); //create a new image to show only the red pixels
			image2 = ImageIO.read(new File("brighten.jpg")); //reference image
		
			for(int i=0; i<img.getWidth(); i++){
		        for(int j=0; j<img.getHeight(); j++) {
		        	
		             Color c = new Color(img.getRGB(i,j));
		                int red = c.getRed();
		                int green = c.getGreen();
		                int blue = c.getBlue();
		                
		                Color c2 = new Color(image2.getRGB(i, j)); //second color extracted from the original img
		            	int r = c2.getRed(); //image's red pixel
		            	int g = c2.getGreen();
		            	int b = c2.getBlue();
		                col = (r << 16) | (g << 8) | b;
		                
		                if(red==255 && blue==255 && green==255) {
		                	pixelCount ++; //count the white pixel
		                	image.setRGB(i, j, col);
		                }   
		          }
		       }//end for
			
	    	outputfile = new File("ColouredImg.jpg");
			ImageIO.write(image, "jpg", outputfile); //save the coloured image
			
	        if(pixelCount>=15000) {
		    	System.out.println("blue detected = " + pixelCount);
		    	blue = true;
			}
	        else {
	        	System.out.println("no blue detected = " + pixelCount);
	        }
		}
	
	}	catch (Exception e) {
         System.out.println("");
		}		
   }//end if image captured != null
	
}//end DetectColorBlue method
	
	public void DetectColorYellow(BufferedImage img) throws IOException, InterruptedException {
		
		int pixelCount = 0; //counter for detected pixel color in the image
		
		//Brighten the image using imgScalr
		final RescaleOp OP_BRIGHTER = new RescaleOp(1.7f, 0, null);
		final RescaleOp OP_BRIGHTER_EXTRA = new RescaleOp(3.3f, 0, null);
		final RescaleOp OP_LESS_BRIGHT = new RescaleOp(0.3f, 0, null);
		
		/* Load the image & convert it to HSV */
		IplImage captured = cvLoadImage("capture.jpg");
		IplImage hsvImg = null;
		
		if(captured!=null) {
			hsvImg = IplImage.create(captured.width(), captured.height(), IPL_DEPTH_8U, 3);
			cvCvtColor(captured, hsvImg, CV_BGR2HSV);
			Thread.sleep(300); //wait a moment to convert the image fully before processing it

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
			
			/* Enhance image based on the luminance */
			if(lumAvg<=105) { //lower brightness 
				OP_BRIGHTER_EXTRA.filter(img, img);
				System.out.println("luminance: " + lumAvg);
			}
			else if(lumAvg>105) { //higher brightness
				OP_BRIGHTER.filter(img, img);
				System.out.println("luminance: " + lumAvg);
			}
			else if(lumAvg>115) { //very higher brightness
				OP_LESS_BRIGHT.filter(img, img);
				System.out.println("luminance: " + lumAvg);
			}
				
			OutputStream ostream = new FileOutputStream("brighten.jpg");
		    ImageIO.write(img, "jpg", ostream); //save the enhanced image
		    Thread.sleep(300); //wait
		}
	
		//perform color detection on captured image
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat savedImg = Highgui.imread("brighten.jpg"); //load enhanced image in Mat format
		
		if(savedImg != null) {
			
			Mat dest = new Mat(savedImg.rows(),savedImg.cols(),savedImg.type()); //create destination image in Mat format
			Imgproc.GaussianBlur(savedImg,dest,new Size(15,15),0.0); //apply gaussian smoothing
			Highgui.imwrite("smoothed.jpg", dest); //then save the image
		
		IplImage smoothed = cvLoadImage("smoothed.jpg"); //load the smoothed image in IplImage format
			
		if(smoothed!=null) {
			
			IplImage imgThreshold = cvCreateImage(cvGetSize(smoothed), 8, 1); 
			CvScalar max = cvScalar(153, 255, 255, 0);//max range of yellow
			CvScalar min = cvScalar(0, 153, 153, 0);//min range of yellow
			
			cvInRangeS(smoothed, min, max, imgThreshold);// apply thresholding          
	        cvSaveImage("threshold.jpg", imgThreshold); //save the image
		}
		//then read the thresholded image
		try{
			img = ImageIO.read(new File("threshold.jpg"));
			int col;
			image = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB); //create a new image to show only the red pixels
			image2 = ImageIO.read(new File("brighten.jpg")); //reference image
		
			for(int i=0; i<img.getWidth(); i++){
		        for(int j=0; j<img.getHeight(); j++) {
		        	
		             Color c = new Color(img.getRGB(i,j));
		                int red = c.getRed();
		                int green = c.getGreen();
		                int blue = c.getBlue();
		                
		                Color c2 = new Color(image2.getRGB(i, j)); //second color extracted from the original img
		            	int r = c2.getRed(); //image's red pixel
		            	int g = c2.getGreen();
		            	int b = c2.getBlue();
		                col = (r << 16) | (g << 8) | b;
		                
		                if(red==255 && blue==255 && green==255) {
		                	pixelCount ++; //count the white pixel
		                	image.setRGB(i, j, col);
		                }   
		          }
		       }//end for
			
	    	File outputfile = new File("ColouredImg.jpg");
			ImageIO.write(image, "jpg", outputfile); //save the coloured image
		
		}	catch (Exception e) {
	         System.out.println("");
			}		
	   }//end if image captured != null
		
		if(pixelCount>=15000) {
	    	System.out.println("yellow detected = " + pixelCount);
	    	yellow = true;
		}
		else
			System.out.println("no yellow detected = " + pixelCount);
		
	}//end DetectColorYellow method
	
	public void DetectColorPurple(BufferedImage img) throws IOException, InterruptedException {
		
		int pixelCount = 0; //counter for detected pixel color in the image
		
		//Brighten the image using imgScalr
		final RescaleOp OP_BRIGHTER = new RescaleOp(1.7f, 0, null);
		final RescaleOp OP_BRIGHTER_EXTRA = new RescaleOp(3.3f, 0, null);
		final RescaleOp OP_LESS_BRIGHT = new RescaleOp(0.3f, 0, null);
		
		/* Load the image & convert it to HSV */
		IplImage captured = cvLoadImage("capture.jpg");
		IplImage hsvImg = null;
		
		if(captured!=null) {
			hsvImg = IplImage.create(captured.width(), captured.height(), IPL_DEPTH_8U, 3);
			cvCvtColor(captured, hsvImg, CV_BGR2HSV);
			Thread.sleep(300);

			int counter = 0; 
			double luminance = 0;
			
			for(int x=1; x<hsvImg.width()-1; x++) {
				for(int y=1; y<hsvImg.height()-1; y++) {
					
					CvScalar l = cvGet2D(hsvImg,y,x);
					luminance += l.val(2); //total the values of V in HSV in every pixel
					counter++; //counting the total no of pixel
				}
			}//end for
			int lumAvg = (int)luminance/counter; //calculate the average V values to estimate
												//the level of image brightness
			
			/* Enhance image based on the luminance */
			if(lumAvg<=105) { //lower brightness 
				OP_BRIGHTER_EXTRA.filter(img, img);
				System.out.println("luminance: " + lumAvg);
			}
			else if(lumAvg>105) { //higher brightness
				OP_BRIGHTER.filter(img, img);
				System.out.println("luminance: " + lumAvg);
			}
			else if(lumAvg>115) { //very higher brightness
				OP_LESS_BRIGHT.filter(img, img);
				System.out.println("luminance: " + lumAvg);
			}
		
			OutputStream ostream = new FileOutputStream("brighten.jpg");
		    ImageIO.write(img, "jpg", ostream); //save the enhanced image
		    Thread.sleep(300);
		}
		
		//perform color detection on captured image
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat savedImg = Highgui.imread("brighten.jpg"); //load enhanced image in Mat format
		
		if(savedImg != null) {
			
			Mat dest = new Mat(savedImg.rows(),savedImg.cols(),savedImg.type()); //create destination image in Mat format
			Imgproc.GaussianBlur(savedImg,dest,new Size(15,15),0.0); //apply gaussian smoothing
			Highgui.imwrite("smoothed.jpg", dest); //then save the image
		
		IplImage smoothed = cvLoadImage("smoothed.jpg"); //load the smoothed image in IplImage format
			
		if(smoothed!=null) {
			
			IplImage imgThreshold = cvCreateImage(cvGetSize(smoothed), 8, 1); 
			CvScalar max = cvScalar(153, 51, 255, 0);//max range of purple
			CvScalar min = cvScalar(51, 0, 102, 0);//min range of purp;e
			
			cvInRangeS(smoothed, min, max, imgThreshold);// apply thresholding          
	        cvSaveImage("threshold.jpg", imgThreshold); //save the image
		}
		//then read the thresholded image
		try{
			img = ImageIO.read(new File("threshold.jpg"));
			int col;
			image = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB); //create a new image to show only the red pixels
			image2 = ImageIO.read(new File("brighten.jpg")); //reference image
		
			for(int i=0; i<img.getWidth(); i++){
		        for(int j=0; j<img.getHeight(); j++) {
		        	
		             Color c = new Color(img.getRGB(i,j));
		                int red = c.getRed();
		                int green = c.getGreen();
		                int blue = c.getBlue();
		                
		                Color c2 = new Color(image2.getRGB(i, j)); //second color extracted from the original img
		            	int r = c2.getRed(); //image's red pixel
		            	int g = c2.getGreen();
		            	int b = c2.getBlue();
		                col = (r << 16) | (g << 8) | b;
		                
		                if(red==255 && blue==255 && green==255) {
		                	pixelCount ++; //count the white pixel
		                	image.setRGB(i, j, col);
		                }   
		          }
		       }//end for
			
	    	File outputfile = new File("ColouredImg.jpg");
			ImageIO.write(image, "jpg", outputfile); //save the coloured image
			
			if(pixelCount>=15000) {
		    	System.out.println("purple1 detected = " + pixelCount);
		    	purple = true;
			}
			else { //second range of purple - violet
				pixelCount = 0;
				IplImage imgThreshold = cvCreateImage(cvGetSize(smoothed), 8, 1); 
				CvScalar max = cvScalar(255, 102, 178, 0);//max range of purple
				CvScalar min = cvScalar(102, 0, 51, 0);//min range of purple
				
				cvInRangeS(smoothed, min, max, imgThreshold);// apply thresholding          
		        cvSaveImage("threshold.jpg", imgThreshold); //save the image
		        
		        Thread.sleep(300);
		        img = ImageIO.read(new File("threshold.jpg"));
				image = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB); //create a new image to show only the red pixels
				image2 = ImageIO.read(new File("brighten.jpg")); //reference image
			
				for(int i=0; i<img.getWidth(); i++){
			        for(int j=0; j<img.getHeight(); j++) {
			        	
			             Color c = new Color(img.getRGB(i,j));
			                int red = c.getRed();
			                int green = c.getGreen();
			                int blue = c.getBlue();
			                
			                Color c2 = new Color(image2.getRGB(i, j)); //second color extracted from the original img
			            	int r = c2.getRed(); //image's red pixel
			            	int g = c2.getGreen();
			            	int b = c2.getBlue();
			                col = (r << 16) | (g << 8) | b;
			                
			                if(red==255 && blue==255 && green==255) {
			                	pixelCount ++; //count the white pixel
			                	image.setRGB(i, j, col);
			                }   
			          }
			       }//end for
				
		    	outputfile = new File("ColouredImg.jpg");
				ImageIO.write(image, "jpg", outputfile); //save the coloured image
				
		        if(pixelCount>=15000) {
			    	System.out.println("purple2 detected = " + pixelCount);
			    	purple = true;
				}
		        else { //third range of purple - magenta
		        	
		        	pixelCount = 0;
					IplImage imgThreshold2 = cvCreateImage(cvGetSize(smoothed), 8, 1); 
					CvScalar max2 = cvScalar(255, 51, 255, 0);//max range of purple
					CvScalar min2 = cvScalar(102, 0, 102, 0);//min range of purple
					
					cvInRangeS(smoothed, min2, max2, imgThreshold2);// apply thresholding          
			        cvSaveImage("threshold.jpg", imgThreshold2); //save the image
			        
			        Thread.sleep(300);
			        img = ImageIO.read(new File("threshold.jpg"));
					image = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB); //create a new image to show only the red pixels
					image2 = ImageIO.read(new File("brighten.jpg")); //reference image
				
					for(int i=0; i<img.getWidth(); i++){
				        for(int j=0; j<img.getHeight(); j++) {
				        	
				             Color c = new Color(img.getRGB(i,j));
				                int red = c.getRed();
				                int green = c.getGreen();
				                int blue = c.getBlue();
				                
				                Color c2 = new Color(image2.getRGB(i, j)); //second color extracted from the original img
				            	int r = c2.getRed(); //image's red pixel
				            	int g = c2.getGreen();
				            	int b = c2.getBlue();
				                col = (r << 16) | (g << 8) | b;
				                
				                if(red==255 && blue==255 && green==255) {
				                	pixelCount ++; //count the white pixel
				                	image.setRGB(i, j, col);
				                }   
				          }
				       }//end for
					
			    	outputfile = new File("ColouredImg.jpg");
					ImageIO.write(image, "jpg", outputfile); //save the coloured image
					
			        if(pixelCount>=15000) {
				    	System.out.println("purple3 detected = " + pixelCount);
				    	purple = true;
					}
			        else
			        	System.out.println("no purple detected = " + pixelCount);
		        }
			}//end main else
		
		}	catch (Exception e) {
	         System.out.println("");
			}		
	   }//end if image captured != null
		
	}//end DetectColorPurple method


public boolean redDetected() {
	return red;
}

public boolean greenDetected() {
	return green;
}

public boolean blueDetected() {
	return blue;
}

public boolean yellowDetected() {
	return yellow;
}

public boolean purpleDetected() {
	return purple;
}

}//end class