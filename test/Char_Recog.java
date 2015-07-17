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

import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_32F;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvGet2D;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2HSV;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_core.*;

public class Char_Recog {
	
	boolean A = false, B = false, C = false, D = false, E = false;
	boolean F = false, G = false, H = false, I = false, J = false;
	boolean K = false, L = false, M = false, N = false, O = false;
	boolean P = false, Q = false, R = false, S = false, T = false;
	boolean U = false, V = false, W = false, X = false, Y = false, Z = false;

	
	// For Testing purposes
	/*boolean A = false, B = true, C = true, D = true, E = true;
	boolean F = true, G = true, H = true, I = true, J = true;
	boolean K = true, L = true, M = true, N = true, O = true;
	boolean P = true, Q = true, R = true, S = true, T = true;
	boolean U = true, V = true, W = true, X = true, Y = true, Z = true;*/
	
	
public void A(IplImage img) throws InterruptedException, IOException {
		
		//process the image
		Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/a_1.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 8; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 A = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
		
	} //end method A
	
public void B(IplImage img) throws InterruptedException, IOException {
		
		//process the image
		Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/b_1.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 10; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 B = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
		
	} //end method B	

public void C(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
			
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	Mat template = Highgui.imread("train_data2/c_1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
			
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	MinMaxLocResult mmr = Core.minMaxLoc(result);
	Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 9; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 C = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method C

public void D(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
			
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	Mat template = Highgui.imread("train_data2/d_1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
			
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	MinMaxLocResult mmr = Core.minMaxLoc(result);
	Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 11; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 D = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method D

public void E(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
			
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	
	//read two templates
	Mat template = Highgui.imread("train_data2/e_2.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
	Mat template2 = Highgui.imread("train_data2/e_3.jpg",0);
	Mat result2 = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);	
	
	//match templates
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	Imgproc.matchTemplate(src, template2, result2, Imgproc.TM_SQDIFF);
	
	MinMaxLocResult mmr = Core.minMaxLoc(result);
	Point matchLoc = mmr.minLoc;
	MinMaxLocResult mmr2 = Core.minMaxLoc(result2);
	Point matchLoc2 = mmr2.minLoc;
	
	double rangeDistanceX = Math.abs(matchLoc.x - matchLoc2.x); //match distance between 2 templates
	double rangeDistanceY = Math.abs(matchLoc.y - matchLoc2.y); //2 parts of a letter should be closed to each other
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;	
	 double VAL2 = mmr2.minVal/1000000;
	 double THRESHOLD = 4.5; //set a threshold to determine how good the match is
	 
	 System.out.println(rangeDistanceX + " & Y " + rangeDistanceY);
	 
	 if(VAL < THRESHOLD && VAL2 < THRESHOLD+2 && rangeDistanceX < 15 && rangeDistanceY < 70) {
		 System.out.println("Match is found -> " + VAL + " -> " + VAL2);
		 E = true;
	 }
	 else {
			 System.out.println("Match not found -> " + VAL + " -> " + VAL2);
	 }
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw 2 rectangles around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
	 Point rect2 = new Point(matchLoc2.x + template2.cols(), matchLoc2.y + template2.rows());		 
	 Core.rectangle(src, matchLoc2, rect2 ,new Scalar(204, 0, 102), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method E

public void F(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
			
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	Mat template = Highgui.imread("train_data2/f_1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
			
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	MinMaxLocResult mmr = Core.minMaxLoc(result);
	Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 7.5; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		F = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method F

public void G(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
			
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	Mat template = Highgui.imread("train_data2/g_1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
			
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	MinMaxLocResult mmr = Core.minMaxLoc(result);
	Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 8.5; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 G = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method G

public void H(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
			
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	Mat template = Highgui.imread("train_data2/h_1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
			
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	MinMaxLocResult mmr = Core.minMaxLoc(result);
	Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 7.5; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 H = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method H

public void I(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
			
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	Mat template = Highgui.imread("train_data2/i_1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
			
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	MinMaxLocResult mmr = Core.minMaxLoc(result);
	Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 9; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 I = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method I

public void J(IplImage img) throws InterruptedException, IOException {
	
	//process the image
		Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/j_3.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 7; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 J = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method J

public void K(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
			
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	Mat template = Highgui.imread("train_data2/k_1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
			
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	MinMaxLocResult mmr = Core.minMaxLoc(result);
	Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 7; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 K = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method K

public void L(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
			
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	Mat template = Highgui.imread("train_data2/l_1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
			
	Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
	MinMaxLocResult mmr = Core.minMaxLoc(result);
	Point matchLoc = mmr.minLoc;
	 
	 //min value gives the best match for TM_SQDIFF method
	 double VAL = mmr.minVal/1000000;		 	 
	 double THRESHOLD = 5; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD) {
		 System.out.println("Match is found -> " + VAL);
		 L = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw a rectangle around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method L

public void M(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/m_1.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 8.5; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 M = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method M

public void N(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
			
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	
	//read two templates
	Mat template = Highgui.imread("train_data2/n_1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
	Mat template2 = Highgui.imread("train_data2/n_2.jpg",0);
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
	 double THRESHOLD = 8; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD && VAL2 < 5) {
		 
		 System.out.println("Match is found -> " + VAL + " -> " + VAL2);
		 N = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL + " -> " + VAL2);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw 2 rectangleS around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
	 Point rect2 = new Point(matchLoc2.x + template2.cols(), matchLoc2.y + template2.rows());		 
	 Core.rectangle(src, matchLoc2, rect2 ,new Scalar(204, 0, 102), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method N

public void O(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
			
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	Mat src = Highgui.imread("binary.jpg",0);
	
	//read two templates
	Mat template = Highgui.imread("train_data2/o_u1.jpg",0);
	Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
	Mat template2 = Highgui.imread("train_data2/o_2.jpg",0);
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
	 double THRESHOLD = 8.5; //set a threshold to determine how good the match is
	 
	 if(VAL < THRESHOLD && VAL2 < THRESHOLD) {
		 
		 System.out.println("Match is found -> " + VAL + " -> " + VAL2);
		 O = true;
	 }
	 else
		 System.out.println("Match not found -> " + VAL + " -> " + VAL2);
	 
	 src = Highgui.imread("brighten.jpg");
	 
	 //draw 2 rectangleS around the matched area
	 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
	 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
	 Point rect2 = new Point(matchLoc2.x + template2.cols(), matchLoc2.y + template2.rows());		 
	 Core.rectangle(src, matchLoc2, rect2 ,new Scalar(204, 0, 102), 2);
	 
	 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method O
	
public void P(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/p_1.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 8; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 P = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method P

public void Q(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/q_1.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 8; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 Q = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method Q

public void R(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/r_1.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 30; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 R = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method R

public void S(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/s_2.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 7; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 S = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method S

public void T(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/t_1.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 7; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 T = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method T

public void U(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/u_1.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 7.5; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 U = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method U

public void V(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/v_w.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 6; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 V = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method V

public void W(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		
		//read two templates
		Mat template = Highgui.imread("train_data2/v_w.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
		Mat template2 = Highgui.imread("train_data2/w_1.jpg",0);
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
		 double THRESHOLD = 6.5; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD && VAL2 < THRESHOLD) {
			 
			 System.out.println("Match is found -> " + VAL + " -> " + VAL2);
			 W = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL + " -> " + VAL2);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw 2 rectangleS around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 Point rect2 = new Point(matchLoc2.x + template2.cols(), matchLoc2.y + template2.rows());		 
		 Core.rectangle(src, matchLoc2, rect2 ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method W

public void X(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/x_1.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 6; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 X = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect ,new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method X

public void Y(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/y_1.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 6; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 Y = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect, new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method Y

public void Z(IplImage img) throws InterruptedException, IOException {
	
	//process the image
	Process(img);
				
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat src = Highgui.imread("binary.jpg",0);
		Mat template = Highgui.imread("train_data2/z_1.jpg",0);
		Mat result = new Mat(src.rows(), src.cols(), IPL_DEPTH_32F);
				
		Imgproc.matchTemplate(src, template, result, Imgproc.TM_SQDIFF);
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		 
		 //min value gives the best match for TM_SQDIFF method
		 double VAL = mmr.minVal/1000000;		 	 
		 double THRESHOLD = 6; //set a threshold to determine how good the match is
		 
		 if(VAL < THRESHOLD) {
			 System.out.println("Match is found -> " + VAL);
			 Z = true;
		 }
		 else
			 System.out.println("Match not found -> " + VAL);
		 
		 src = Highgui.imread("brighten.jpg");
		 
		 //draw a rectangle around the matched area
		 Point rect = new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows());		 
		 Core.rectangle(src, matchLoc, rect, new Scalar(204, 0, 102), 2);
		 
		 Highgui.imwrite("match.jpg", src);	//save the image	
	
} //end method Z

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

public boolean isA() {
	return A;
}
public boolean isB() {
	return B;
}
public boolean isC() {
	return C;
}
public boolean isD() {
	return D;
}
public boolean isE() {
	return E;
}
public boolean isF() {
	return F;
}
public boolean isG() {
	return G;
}
public boolean isH() {
	return H;
}
public boolean isI() {
	return I;
}
public boolean isJ() {
	return J;
}
public boolean isK() {
	return K;
}
public boolean isL() {
	return L;
}
public boolean isM() {
	return M;
}
public boolean isN() {
	return N;
}
public boolean isO() {
	return O;
}
public boolean isP() {
	return P;
}
public boolean isQ() {
	return Q;
}
public boolean isR() {
	return R;
}
public boolean isS() {
	return S;
}
public boolean isT() {
	return T;
}
public boolean isU() {
	return U;
}
public boolean isV() {
	return V;
}
public boolean isW() {
	return W;
}
public boolean isX() {
	return X;
}
public boolean isY() {
	return Y;
}
public boolean isZ() {
	return Z;
}

public void restart() { //set everything back to false
	
	A = false; B = false; C = false; D = false; E = false;
	F = false; G = false; H = false; I = false; J = false;
	K = false; L = false; M = false; N = false; O = false;
	P = false; Q = false; R = false; S = false; T = false;
	U = false; V = false; W = false; X = false; Y = false;
	Z = false;	
}

} //end class
