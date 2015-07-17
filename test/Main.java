package test;

/* 
 * This is the main class where the GUI is constructed and all core functionalities
 * of the game application is called through this class.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.*;
import org.imgscalr.Scalr;
import org.opencv.core.Core;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.imgscalr.Scalr.*;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

import sun.audio.*;

import java.awt.SystemColor;


public class Main {
	
IplImage image;	
BufferedImage img = null, thumbnail = null;
ImageIcon icon1 = null, icon2 = null, iconBG = null, iconBG2 = null, iconBG3 = null, iconBG4 = null, iconBG5 = null;
//image icon for background images

private JTextField Question;
private JTextField response;
private JButton next;
private JTextArea instruction;

boolean empty = true; //indicates whether an image has been captured or not
boolean backPressed1 = false, nextPressed1 = false; //mark when the back & next button is pressed
boolean backPressed2 = false, nextPressed2 = false;
boolean backPressed3 = false, nextPressed3 = false;
boolean backPressed4 = false, nextPressed4 = false;
boolean lesson1 = false, lesson2 = false, lesson3 = false, lesson4 = false; //mark which lesson is being played atm

FrameGrabber grabber = new VideoInputFrameGrabber(0);

private JLabel label1;
private JButton alphabets;
private JLabel bg2;

static String bgmusic; //sound label
static AudioInputStream audioStream; //for background music
static Clip clip;

//create and implement the color detection class
ColorDetection color = new ColorDetection();

//create and implement the shape detection class
ShapeDetection shape = new ShapeDetection(); 

//create and implement the number recognition class
Num_Recog num = new Num_Recog(); 

//create and implement the character recognition class
Char_Recog chara = new Char_Recog();

public Main() {
		
		JFrame guiFrame = new JFrame();
		guiFrame.setForeground(Color.WHITE);
		guiFrame.setBackground(Color.WHITE);
		guiFrame.getContentPane().setBackground(new Color(255, 255, 51));
		guiFrame.setResizable(false);
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		guiFrame.setTitle("Let's Play!"); 
		guiFrame.setSize(700,700);
		guiFrame.setLocation(50, 20);//.setLocationRelativeTo(null); //center the frame in the window
		guiFrame.setVisible(true);
		
		//load background images
		try { 
			img = ImageIO.read(new File("images/bg.jpg"));
			iconBG = new ImageIcon(img);
			img = ImageIO.read(new File("images/bg2.jpg"));
			iconBG2 = new ImageIcon(img);
			img = ImageIO.read(new File("images/bg3.jpg"));
			iconBG3 = new ImageIcon(img);
			img = ImageIO.read(new File("images/bg4.jpg"));
			iconBG4 = new ImageIcon(img);
			img = ImageIO.read(new File("images/bg5.jpg"));
			iconBG5 = new ImageIcon(img);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JButton colors = new JButton("Colors");
		colors.setBounds(169, 196, 160, 107);
		guiFrame.getContentPane().setLayout(null);
		colors.setForeground(Color.BLACK);
		colors.setBackground(new Color(255, 0, 51));
		colors.setFont(new Font("KG Primary Whimsy", Font.BOLD, 33));
		guiFrame.getContentPane().add(colors);
		
		Question = new JTextField();
		Question.setEditable(false);
		Question.setForeground(Color.BLACK);
		Question.setBackground(Color.WHITE);
		Question.setFont(new Font("Eraser", Font.PLAIN, 30));
		Question.setHorizontalAlignment(SwingConstants.CENTER);
		Question.setBounds(237, 26, 427, 52);
		guiFrame.getContentPane().add(Question);
		Question.setColumns(10);
		
		response = new JTextField();
		response.setEditable(false);
		response.setHorizontalAlignment(SwingConstants.CENTER);
		response.setFont(new Font("Eraser", Font.PLAIN, 30));
		response.setBackground(new Color(255, 255, 255));
		response.setBounds(326, 544, 250, 52);
		guiFrame.getContentPane().add(response);
		response.setColumns(10);
		
		next = new JButton("NEXT");
		next.setForeground(new Color(0, 0, 0));
		next.setBackground(new Color(255, 51, 51));
		next.setFont(new Font("KG Primary Whimsy", Font.BOLD, 18));
		next.setBounds(586, 544, 78, 52);
		guiFrame.getContentPane().add(next);
		
		instruction = new JTextArea();
		instruction.setEditable(false);
		instruction.setLineWrap(true);
		instruction.setForeground(Color.BLACK);
		instruction.setFont(new Font("KG Primary Whimsy", Font.BOLD, 26));
		instruction.setWrapStyleWord(true);
		instruction.setBounds(42, 26, 167, 377);
		guiFrame.getContentPane().add(instruction);
		
		JButton shapes = new JButton("Shapes");
		shapes.setForeground(Color.BLACK);
		shapes.setFont(new Font("KG Primary Whimsy", Font.BOLD, 33));
		shapes.setBackground(new Color(51, 255, 51));
		shapes.setBounds(169, 337, 160, 107);
		guiFrame.getContentPane().add(shapes);		
		
		JButton numbers = new JButton("Numbers");
		numbers.setForeground(Color.BLACK);
		numbers.setFont(new Font("KG Primary Whimsy", Font.BOLD, 33));
		numbers.setBackground(new Color(255, 255, 0));
		numbers.setBounds(348, 196, 167, 107);
		guiFrame.getContentPane().add(numbers);
		
		alphabets = new JButton("Alphabets");
		alphabets.setForeground(new Color(0, 0, 0));
		alphabets.setFont(new Font("KG Primary Whimsy", Font.BOLD, 33));
		alphabets.setBackground(new Color(0, 153, 255));
		alphabets.setBounds(348, 337, 167, 107);
		guiFrame.getContentPane().add(alphabets);
		
		JLabel label2 = new JLabel("");
		label2.setForeground(new Color(255, 255, 51));
		label2.setBounds(326, 298, 250, 250);
		guiFrame.getContentPane().add(label2);
		label2.setVisible(false);
		
		label1 = new JLabel();
		label1.setBounds(326, 100, 250, 250);
		guiFrame.getContentPane().add(label1);
		label1.setVisible(false);
		
		JButton back = new JButton("BACK");
		back.setForeground(Color.BLACK);
		back.setBackground(new Color(0, 206, 209));
		back.setFont(new Font("KG Primary Whimsy", Font.BOLD, 18));
		back.setBounds(227, 544, 89, 52);
		guiFrame.getContentPane().add(back);
		
		JLabel bg5 = new JLabel("bgalphabets");
		bg5.setBounds(0, 0, 694, 671);
		bg5.setIcon(iconBG5);
		guiFrame.getContentPane().add(bg5);
		
		JLabel bg4 = new JLabel("bgnumber");
		bg4.setBounds(0, 0, 694, 671);
		bg4.setIcon(iconBG4);
		guiFrame.getContentPane().add(bg4);
		
		JLabel bg3 = new JLabel("bgshape");
		bg3.setBounds(0, 0, 694, 671);
		bg3.setIcon(iconBG3);
		guiFrame.getContentPane().add(bg3);
		
		bg2 = new JLabel("bgcolor");
		bg2.setBounds(0, 0, 694, 671);
		bg2.setIcon(iconBG2);	
		guiFrame.getContentPane().add(bg2);
		
		JLabel bg = new JLabel("bgmain");
		bg.setForeground(new Color(255, 255, 51));
		bg.setBounds(0, 0, 694, 671);
		bg.setIcon(iconBG);		
		guiFrame.getContentPane().add(bg);

		Question.setVisible(false);
		response.setVisible(false);		
		instruction.setVisible(false);
		next.setVisible(false);
		back.setVisible(false);
		bg2.setVisible(false);
		bg3.setVisible(false);
		bg4.setVisible(false);
		bg5.setVisible(false);
		
		
		alphabets.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				bgmusic = "music2.wav";
			    
			    //create an audiostream from the inputstream
			    try {
			    	clip.close(); //close the previous music
					audioStream = AudioSystem.getAudioInputStream(new File(bgmusic));
					clip = AudioSystem.getClip();
				    clip.open(audioStream);
				    clip.loop(Clip.LOOP_CONTINUOUSLY); //play continuously
				    
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				CanvasFrame canvas = new CanvasFrame("");
				canvas.setSize(500, 415);
				canvas.setLocation(760, 120);
				canvas.setResizable(false);
				canvas.requestFocus();
				
				colors.setVisible(false);
				shapes.setVisible(false);
				numbers.setVisible(false);
				alphabets.setVisible(false);
				back.setVisible(true);
				bg.setVisible(false);
				bg5.setVisible(true);

				lesson4 = true; //set flag
				
				instruction.setVisible(true);
				instruction.setText("");
				instruction.append(" _______________\n INSTRUCTION \n"
						+ " _______________\n   Write down \n the first letter\n"
						+ "  of the object\n   shown in the \n   picture on a\n"
						+ "  piece of white\n   paper. Press\n"
						+ "   spacebar to\n   capture the\n       image.");
				
				canvas.addKeyListener(new KeyListener() {

					@Override
					public void keyPressed(KeyEvent e) {
						
						if (e.getKeyCode() == KeyEvent.VK_SPACE) {
							
							cvSaveImage("capture.jpg",image);	
							empty = false;
							canvas.requestFocus();
						}						
					}
					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub						
					}
					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub						
					}
			
		}); //end canvas addListener
				
				Thread webcam = new Thread(){ //start a new thread
					
					public void run(){
								
						try {
							if(backPressed4==false)
								grabber.start();
							else
								grabber.restart();
							
							CvMemStorage storage = CvMemStorage.create();
							Thread.sleep(200);
									
							while(canvas.isVisible() && (image = grabber.grab()) != null && lesson4==true) {
								
								cvClearMemStorage(storage);
		
								Question.setVisible(true);
								response.setVisible(true);
								label1.setVisible(true);
								
								/* When user presses the back button */								
								back.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent arg0) {
										
										//go back to main menu										
										
										bg.setVisible(true);
										bg5.setVisible(false);
										
										colors.setVisible(true);
										shapes.setVisible(true);
										numbers.setVisible(true);				
										alphabets.setVisible(true);
										
										response.setText("");
										Question.setText("");
										backPressed4 = true; //mark
										nextPressed4 = false; //reset
										
										Question.setVisible(false);
										response.setVisible(false);				
										instruction.setVisible(false);
										next.setVisible(false);
										back.setVisible(false);
										label1.setVisible(false);
										label1.setIcon(null);
										label2.setVisible(false);
										
										//reset the flag
										lesson1 = false;
										lesson2 = false;
										lesson3 = false;
										lesson4 = false;
										
										/*bgmusic = "music.wav";
									    
									    //create an audiostream from the inputstream
									    try {
									    	clip.close(); //close the previous music
											audioStream = AudioSystem.getAudioInputStream(new File(bgmusic));
											clip = AudioSystem.getClip();
										    clip.open(audioStream);
										    clip.loop(Clip.LOOP_CONTINUOUSLY); //play continuously
										    
										} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}*/
										
										canvas.dispose(); //exit
										try {
											grabber.stop();
										} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
											e.printStackTrace();
										}
									
									}								
								}); //end back actionListener
								

								//set the initial picture question for A
								if(chara.isA()==false) {
									Question.setText("First letter of this fruit:");									
									
									//display picture word
									try { 
										img = ImageIO.read(new File("images/apple.png"));
										Thread.sleep(200); //wait
										thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
										icon1 = new ImageIcon(thumbnail);
										label1.setIcon(icon1);
										
									} catch (IOException | InterruptedException e1) {
										e1.printStackTrace();
									}
											
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.A(captured);
									empty = true;
									
									if(chara.isA()==true) {
										
										next.setVisible(true);
										next.requestFocus();
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("A-PPLE!");
										
										//actions when the next button is clicked
										next.addActionListener(new ActionListener() {

											@Override
											public void actionPerformed(ActionEvent arg0) {
												
												label2.setVisible(false);
												next.setVisible(false);
												response.setText("");
												canvas.requestFocus();
												nextPressed4 = true; //mark
												backPressed4 = false; //reset
												
												
												/**** For Alphabet Recognition Part ****/
												
				if(lesson4==true) {
												
												if(chara.isA()==true && chara.isB()==false) {
																									
													Question.setText("First letter of this toy:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/ball.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}													
												} //end A
												
												else if(chara.isB()==true && chara.isC()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/cat.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 210); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end B
												
												else if(chara.isC()==true && chara.isD()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/duck.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end C
												
												else if(chara.isD()==true && chara.isE()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/elephant.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end D
												
												else if(chara.isE()==true && chara.isF()==false) {
													
													Question.setText("First letter of this object:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/flower.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end E
												
												else if(chara.isF()==true && chara.isG()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/giraffe.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 240); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end F
												
												else if(chara.isG()==true && chara.isH()==false) {
													
													Question.setText("First letter of this object:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/heli.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 250); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end G
												
												else if(chara.isH()==true && chara.isI()==false) {
													
													Question.setText("First letter of this object:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/island.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end H
												
												else if(chara.isI()==true && chara.isJ()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/jelly.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end I
												
												else if(chara.isJ()==true && chara.isK()==false) {
													
													Question.setText("First letter of this toy:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/kite.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end J
												
												else if(chara.isK()==true && chara.isL()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/lion.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end K
												
												else if(chara.isL()==true && chara.isM()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/mouse.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 240); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end L
												
												else if(chara.isM()==true && chara.isN()==false) {
													
													Question.setText("First letter of this object:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/necklace.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end M
												
												else if(chara.isN()==true && chara.isO()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/ostrich.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end N
												
												else if(chara.isO()==true && chara.isP()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/penguin.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end O
												
												else if(chara.isP()==true && chara.isQ()==false) {
													
													Question.setText("First letter of this person:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/queen.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end P
												
												else if(chara.isQ()==true && chara.isR()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/rabbit.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end Q
												
												else if(chara.isR()==true && chara.isS()==false) {
													
													Question.setText("First letter of this object:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/ship.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 225); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end R
												
												else if(chara.isS()==true && chara.isT()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/turtle.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end S
												
												else if(chara.isT()==true && chara.isU()==false) {
													
													Question.setText("First letter of this object:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/umbrella.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end T
												
												else if(chara.isU()==true && chara.isV()==false) {
													
													Question.setText("First letter of this instrument:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/violin.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 215); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end U
												
												else if(chara.isV()==true && chara.isW()==false) {
													
													Question.setText("First letter of this person:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/wizard.gif"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end V
												
												else if(chara.isW()==true && chara.isX()==false) {
													
													Question.setText("First letter of this instrument:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/xylophone.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end W
												
												else if(chara.isX()==true && chara.isY()==false) {
													
													Question.setText("First letter of this toy:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/yoyo.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end X
												
												else if(chara.isY()==true && chara.isZ()==false) {
													
													Question.setText("First letter of this animal:");
													
													//display picture word
													try { 
														img = ImageIO.read(new File("images/zebra.png"));
														Thread.sleep(200); //wait
														thumbnail = Scalr.resize(img, 230); //resize and fit the image into the icon area
														icon1 = new ImageIcon(thumbnail);
														label1.setIcon(icon1);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end Y
												
												else if(chara.isZ()==true) {
													
													Question.setText("Congratulations!");	
													response.setVisible(false);
													canvas.dispose(); //exit
													
													try {
														img = ImageIO.read(new File("images/end.png"));
														Thread.sleep(100); //wait
														thumbnail = Scalr.resize(img, 250);
														icon1 = new ImageIcon(thumbnail);
														label1.setVisible(true);
														label1.setIcon(icon1);
														
														img = ImageIO.read(new File("images/finished.png"));
														Thread.sleep(100);
														icon2 = new ImageIcon(img);
														label2.setVisible(true);
														label2.setIcon(icon2);
														response.setVisible(false);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												} //end Z	
												canvas.requestFocus();
				}//end if lesson4
											}
										}); //end next actionListener
																													
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
								}
								}//end if A is false								

								if(chara.isA()==true && chara.isB()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this toy:");
										backPressed4 = false; //reset								
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.B(captured);
									empty = true;
									
									if(chara.isB()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("B-ALL!");																				
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if B is false
								
								if(chara.isB()==true && chara.isC()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset	
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/cat.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}		
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.C(captured);
									empty = true;
									
									if(chara.isC()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("C-AT!");									
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if C is false
								
								if(chara.isC()==true && chara.isD()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset	
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/duck.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.D(captured);
									empty = true;
									
									if(chara.isD()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("D-UCK!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if D is false
								
								if(chara.isD()==true && chara.isE()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset		
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/elephant.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.E(captured);
									empty = true;
									
									if(chara.isE()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("E-LEPHANT!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if E is false
								
								if(chara.isE()==true && chara.isF()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this object:");
										backPressed4 = false; //reset		
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/flower.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.F(captured);
									empty = true;
									
									if(chara.isF()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("F-LOWER!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if F is false
								
								if(chara.isF()==true && chara.isG()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/giraffe.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.G(captured);
									empty = true;
									
									if(chara.isG()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("G-IRAFFE!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if G is false
								
								if(chara.isG()==true && chara.isH()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this object:");
										backPressed4 = false; //reset				
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/heli.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.H(captured);
									empty = true;
									
									if(chara.isH()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("H-ELICOPTER!");										
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if H is false
								
								if(chara.isH()==true && chara.isI()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this object:");
										backPressed4 = false; //reset		
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/island.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.I(captured);
									empty = true;
									
									if(chara.isI()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("I-SLAND!");																			
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if I is false
								
								if(chara.isI()==true && chara.isJ()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset	
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/jelly.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.J(captured);
									empty = true;
									
									if(chara.isJ()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("J-ELLYFISH!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if J is false
								
								if(chara.isJ()==true && chara.isK()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this toy:");
										backPressed4 = false; //reset	
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/kite.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.K(captured);
									empty = true;
									
									if(chara.isK()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("K-ITE!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if K is false
								
								if(chara.isK()==true && chara.isL()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset		
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/lion.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.L(captured);
									empty = true;
									
									if(chara.isL()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("L-ION!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if L is false
								
								if(chara.isL()==true && chara.isM()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset	
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/mouse.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.M(captured);
									empty = true;
									
									if(chara.isM()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("M-OUSE!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if M is false
								
								if(chara.isM()==true && chara.isN()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this object:");
										backPressed4 = false; //reset			
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/necklace.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.N(captured);
									empty = true;
									
									if(chara.isN()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("N-ECKLACE!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if N is false
								
								if(chara.isN()==true && chara.isO()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset			
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/ostrich.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.O(captured);
									empty = true;
									
									if(chara.isO()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("O-STRICH!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if O is false
								
								if(chara.isO()==true && chara.isP()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset	
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/penguin.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.P(captured);
									empty = true;
									
									if(chara.isP()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("P-ENGUIN!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if P is false
								
								if(chara.isP()==true && chara.isQ()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this person:");
										backPressed4 = false; //reset	
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/queen.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.Q(captured);
									empty = true;
									
									if(chara.isQ()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("Q-UEEN!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if Q is false
								
								if(chara.isQ()==true && chara.isR()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset		
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/rabbit.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.R(captured);
									empty = true;
									
									if(chara.isR()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("R-ABBIT!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if R is false
								
								if(chara.isR()==true && chara.isS()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this object:");
										backPressed4 = false; //reset	
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/ship.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.S(captured);
									empty = true;
									
									if(chara.isS()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("S-HIP!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if S is false
								
								if(chara.isS()==true && chara.isT()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset		
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/turtle.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.T(captured);
									empty = true;
									
									if(chara.isT()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("T-URTLE!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if T is false
								
								if(chara.isT()==true && chara.isU()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this object:");
										backPressed4 = false; //reset			
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/umbrella.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.U(captured);
									empty = true;
									
									if(chara.isU()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("U-MBRELLA!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if U is false
								
								if(chara.isU()==true && chara.isV()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this instrument:");
										backPressed4 = false; //reset
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/violin.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.V(captured);
									empty = true;
									
									if(chara.isV()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("V-IOLIN!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if V is false
								
								if(chara.isV()==true && chara.isW()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this person:");
										backPressed4 = false; //reset	
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/wizard.gif"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.W(captured);
									empty = true;
									
									if(chara.isW()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("W-IZARD!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if W is false
								
								if(chara.isW()==true && chara.isX()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this instrument:");
										backPressed4 = false; //reset		
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/xylophone.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.X(captured);
									empty = true;
									
									if(chara.isX()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("X-YLOPHONE!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if X is false
								
								if(chara.isX()==true && chara.isY()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this toy:");
										backPressed4 = false; //reset			
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/yoyo.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.Y(captured);
									empty = true;
									
									if(chara.isY()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("Y-OYO!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if Y is false
								
								if(chara.isY()==true && chara.isZ()==false) {
									
									if(backPressed4==true) {
										Question.setText("First letter of this animal:");
										backPressed4 = false; //reset
										
										//display picture word
										try { 
											img = ImageIO.read(new File("images/zebra.png"));
											Thread.sleep(200); //wait
											thumbnail = Scalr.resize(img, 220); //resize and fit the image into the icon area
											icon1 = new ImageIcon(thumbnail);
											label1.setIcon(icon1);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}	
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam		
									chara.Z(captured);
									empty = true;
									
									if(chara.isZ()==true) {
										
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										response.setText("Z-EBRA!");
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end if Z is false
								
								if(chara.isZ()==true && backPressed4==true && nextPressed4==false) {
									//when playing a game that has been completed
									
									Question.setText("Congratulations!");
									response.setVisible(false);
									backPressed1 = false; //reset
									canvas.dispose(); //exit
								
									try {
										img = ImageIO.read(new File("images/end.png"));
										Thread.sleep(100); //wait
										thumbnail = Scalr.resize(img, 250);
										icon1 = new ImageIcon(thumbnail);
										label1.setVisible(true);
										label1.setIcon(icon1);
										
										img = ImageIO.read(new File("images/finished.png"));
										Thread.sleep(100);
										icon2 = new ImageIcon(img);
										label2.setVisible(true);
										label2.setIcon(icon2);
										
									} catch (IOException | InterruptedException e1) {
										e1.printStackTrace();
									}					
								}//end letter question
										
				canvas.showImage(image);
				
			}//end while canvas!=null
									
			} catch (Exception e) {
					e.printStackTrace();
			}
						
		}//end run
		};//end Thread		
		
						webcam.start();
				
	}//end ActionPerformed	
	});//end addActionListener
		
				
		numbers.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				bgmusic = "music3.wav";
			    
			    //create an audiostream from the inputstream
			    try {
			    	clip.close(); //close the previous music
					audioStream = AudioSystem.getAudioInputStream(new File(bgmusic));
					clip = AudioSystem.getClip();
				    clip.open(audioStream);
				    clip.loop(Clip.LOOP_CONTINUOUSLY); //play continuously
				    
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				CanvasFrame canvas = new CanvasFrame("");
				canvas.setSize(500, 415);
				canvas.setLocation(760, 120);
				canvas.setResizable(false);
				canvas.requestFocus();
				
				colors.setVisible(false);
				shapes.setVisible(false);
				numbers.setVisible(false);
				alphabets.setVisible(false);
				
				back.setVisible(true);
				bg.setVisible(false);
				bg4.setVisible(true);

				lesson3 = true; //set flag
				
				instruction.setVisible(true);
				instruction.setText("");
				instruction.append(" _______________\n INSTRUCTION \n"
						+ " _______________\n   Write down \n    the number \n"
						+ "   on a piece of \n   white paper.\n Press "
						+ "spacebar\n to capture the\n       image.");
				
				canvas.addKeyListener(new KeyListener() {
					
					@Override
					public void keyPressed(KeyEvent e) {
						
						if (e.getKeyCode() == KeyEvent.VK_SPACE) {
								cvSaveImage("capture.jpg",image);
								try {
									img = ImageIO.read(new File("capture.jpg"));
									Thread.sleep(100); //wait
									thumbnail = Scalr.resize(img, 250);
									icon1 = new ImageIcon(thumbnail);
									label1.setVisible(true);
									label1.setIcon(icon1);
									
								} catch (IOException | InterruptedException e1) {
									e1.printStackTrace();
								}									
							empty = false;
						}						
					}
					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub						
					}
					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub						
					}							
				});
				
				Thread webcam = new Thread(){ //start a new thread
					
					public void run(){
						try {
							if(backPressed3==false)
								grabber.start();
							else
								grabber.restart();
							
							CvMemStorage storage = CvMemStorage.create();
							Thread.sleep(200);
							
							while(canvas.isVisible() && (image=grabber.grab()) != null && lesson3==true) {
								
								cvClearMemStorage(storage);
	
								Question.setVisible(true);
								response.setVisible(true);
								
								/* When user presses the back button */								
								back.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent arg0) {
										
										//go back to main menu										
										
										bg.setVisible(true);
										bg4.setVisible(false);
										
										colors.setVisible(true);
										shapes.setVisible(true);
										numbers.setVisible(true);				
										alphabets.setVisible(true);
										
										response.setText("");
										Question.setText("");
										backPressed3 = true; //mark
										nextPressed3 = false; //reset
										
										Question.setVisible(false);
										response.setVisible(false);				
										instruction.setVisible(false);
										next.setVisible(false);
										back.setVisible(false);
										label1.setVisible(false);
										label2.setVisible(false);
										
										lesson1 = false;
										lesson2 = false;
										lesson3 = false;
										lesson4 = false;
										
										/*bgmusic = "music.wav";
									    
									    //create an audiostream from the inputstream
									    try {
									    	clip.close(); //close the previous music
											audioStream = AudioSystem.getAudioInputStream(new File(bgmusic));
											clip = AudioSystem.getClip();
										    clip.open(audioStream);
										    clip.loop(Clip.LOOP_CONTINUOUSLY); //play continuously
										    
										} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}*/
										
										canvas.dispose(); //exit
										try {
											grabber.stop();
										} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
											e.printStackTrace();
										}								
									}								
								}); //end back actionListener
								
								//set initial number question
								if(num.is1()==false) {
									Question.setText("Write the number ONE");
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam									
									num.Number1(captured);
									empty = true;
									
									if(num.is1()==true) {
										
										response.setText("That's correct!");
										next.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setVisible(true);
										label2.setIcon(icon2);
										
										//actions when the next button is clicked
										next.addActionListener(new ActionListener() {

											@Override
											public void actionPerformed(ActionEvent arg0) {
												
												label1.setVisible(false);
												label2.setVisible(false);
												next.setVisible(false);
												response.setText("");
												canvas.requestFocus();
												nextPressed3 = true; //mark
												backPressed3 = false; //reset
												
												
												/**** For Number Recognition Part ****/
												
				if(lesson3==true) {
												
												if(num.is1()==true && num.is2()==false)
													Question.setText("Write the number TWO");
												
												else if(num.is2()==true && num.is3()==false)
													Question.setText("Write the number THREE");
												
												else if(num.is3()==true && num.is4()==false)
													Question.setText("Write the number FOUR");
												
												else if(num.is4()==true && num.is5()==false)
													Question.setText("Write the number FIVE");
												
												else if(num.is5()==true && num.is6()==false)
													Question.setText("Write the number SIX");
												
												else if(num.is6()==true && num.is7()==false)
													Question.setText("Write the number SEVEN");
												
												else if(num.is7()==true && num.is8()==false)
													Question.setText("Write the number EIGHT");
												
												else if(num.is8()==true && num.is9()==false)
													Question.setText("Write the number NINE");
												
												else if(num.is9()==true && num.is10()==false)
													Question.setText("Write the number TEN");
												
												else if(num.is10()==true) {
													
													Question.setText("Congratulations!");
													response.setVisible(false);
													canvas.dispose(); //exit
													
													try {
														img = ImageIO.read(new File("images/end.png"));
														Thread.sleep(100); //wait
														thumbnail = Scalr.resize(img, 250);
														icon1 = new ImageIcon(thumbnail);
														label1.setVisible(true);
														label1.setIcon(icon1);
														
														img = ImageIO.read(new File("images/finished.png"));
														Thread.sleep(100);
														icon2 = new ImageIcon(img);
														label2.setVisible(true);
														label2.setIcon(icon2);
														response.setVisible(false);
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}												
												}
												canvas.requestFocus();
				}//end if lesson3
											}
										}); //end next actionListener
									}
									else {
										response.setText("Sorry...try again");
										canvas.requestFocus();
									}
								}
								}//end Q number 1
								
								if(num.is1()==true && num.is2()==false) {
									
									if(backPressed3==true) {
										Question.setText("Write the number TWO");
										backPressed3 = false; //reset								
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam									
									num.Number2(captured);
									empty = true;
									
									if(num.is2()==true) {
										
										response.setText("That's correct!");
										next.setVisible(true);
										label2.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setIcon(icon2);
									}
									else {
										response.setText("Sorry...try again");
										canvas.requestFocus();
									}
									}
								}//end Q number 2
								
								if(num.is2()==true && num.is3()==false) {
									
									if(backPressed3==true) {
										Question.setText("Write the number THREE");
										backPressed3 = false; //reset								
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam									
									num.Number3(captured);
									empty = true;
									
									if(num.is3()==true) {
						
										response.setText("That's correct!");
										next.setVisible(true);
										label2.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setIcon(icon2);
									}
									else {
										response.setText("Sorry...try again");
										canvas.requestFocus();
									}
									}
								}//end Q number 3
								
								if(num.is3()==true && num.is4()==false) {
									
									if(backPressed3==true) {
										Question.setText("Write the number FOUR");
										backPressed3 = false; //reset								
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam									
									num.Number4(captured);
									empty = true;
									
									if(num.is4()==true) {
					
										response.setText("That's correct!");
										next.setVisible(true);
										label2.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setIcon(icon2);
									}
									else {
										response.setText("Sorry...try again");
										canvas.requestFocus();
									}
									}
								}//end Q number 4
								
								if(num.is4()==true && num.is5()==false) {
									
									if(backPressed3==true) {
										Question.setText("Write the number FIVE");
										backPressed3 = false; //reset								
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam									
									num.Number5(captured);
									empty = true;
									
									if(num.is5()==true) {
			
										response.setText("That's correct!");
										next.setVisible(true);
										label2.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setIcon(icon2);
									}
									else {
										response.setText("Sorry...try again");
										canvas.requestFocus();
									}
									}
								}//end Q number 5
								
								if(num.is5()==true && num.is6()==false) {
									
									if(backPressed3==true) {
										Question.setText("Write the number SIX");
										backPressed3 = false; //reset								
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam									
									num.Number6(captured);
									empty = true;
									
									if(num.is6()==true) {
			
										response.setText("That's correct!");
										next.setVisible(true);
										label2.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setIcon(icon2);
									}
									else {
										response.setText("Sorry...try again");
										canvas.requestFocus();
									}
									}
								}//end Q number 6
								
								if(num.is6()==true && num.is7()==false) {
									
									if(backPressed3==true) {
										Question.setText("Write the number SEVEN");
										backPressed3 = false; //reset								
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam									
									num.Number7(captured);
									empty = true;
									
									if(num.is7()==true) {
			
										response.setText("That's correct!");
										next.setVisible(true);
										label2.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setIcon(icon2);
									}
									else {
										response.setText("Sorry...try again");
										canvas.requestFocus();
									}
									}
								}//end Q number 7
								
								if(num.is7()==true && num.is8()==false) {
									
									if(backPressed3==true) {
										Question.setText("Write the number EIGHT");
										backPressed3 = false; //reset								
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam									
									num.Number8(captured);
									empty = true;
									
									if(num.is8()==true) {
							
										response.setText("That's correct!");
										next.setVisible(true);
										label2.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setIcon(icon2);
									}
									else {
										response.setText("Sorry...try again");
										canvas.requestFocus();
									}
									}
								}//end Q number 8
								
								if(num.is8()==true && num.is9()==false) {
									
									if(backPressed3==true) {
										Question.setText("Write the number NINE");
										backPressed3 = false; //reset								
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam									
									num.Number9(captured);
									empty = true;
									
									if(num.is9()==true) {
								
										response.setText("That's correct!");
										next.setVisible(true);
										label2.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setIcon(icon2);
									}
									else {
										response.setText("Sorry...try again");
										canvas.requestFocus();
									}
									}
								}//end Q number 9
								
								if(num.is9()==true && num.is10()==false) {
									
									if(backPressed3==true) {
										Question.setText("Write the number TEN");
										backPressed3 = false; //reset								
									}
									
									if(empty==false) {
									
									IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam									
									num.Number10(captured);
									empty = true;
									
									if(num.is10()==true) {
								
										response.setText("That's correct!");
										next.setVisible(true);
										label2.setVisible(true);
										
										img = ImageIO.read(new File("match.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //show resized resulting image
										icon2 = new ImageIcon(thumbnail); //to fit in the window
										label2.setIcon(icon2);										
									}
									else {
										response.setText("Sorry... try again");
										canvas.requestFocus();
									}
									}
								}//end Q number 10
								
								if(num.is10()==true && backPressed3==true && nextPressed3==false) {
									//when playing a game that has been completed
									
									Question.setText("Congratulations!");
									response.setVisible(false);
									backPressed1 = false; //reset
									canvas.dispose(); //exit
								
									try {
										img = ImageIO.read(new File("images/end.png"));
										Thread.sleep(100); //wait
										thumbnail = Scalr.resize(img, 250);
										icon1 = new ImageIcon(thumbnail);
										label1.setVisible(true);
										label1.setIcon(icon1);
										
										img = ImageIO.read(new File("images/finished.png"));
										Thread.sleep(100);
										icon2 = new ImageIcon(img);
										label2.setVisible(true);
										label2.setIcon(icon2);
										
									} catch (IOException | InterruptedException e1) {
										e1.printStackTrace();
									}				
								}//end Number Question
								
							canvas.showImage(image); 
							} //end while canvas != null
						} //end try
						
						catch (Exception e) {
							e.printStackTrace();
						} //end catch
						} //end run
					}; //end thread
					
					webcam.start();
			}
		}); // end numbers addActionListener
		
				
		shapes.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				bgmusic = "music2.wav";
			    
			    //create an audiostream from the inputstream
			    try {
			    	clip.close(); //close the previous music
					audioStream = AudioSystem.getAudioInputStream(new File(bgmusic));
					clip = AudioSystem.getClip();
				    clip.open(audioStream);
				    clip.loop(Clip.LOOP_CONTINUOUSLY); //play continuously
				    
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
						
				CanvasFrame canvas = new CanvasFrame("");
				canvas.setSize(500, 415);
				canvas.setLocation(760, 120);
				canvas.setResizable(false);
				canvas.requestFocus();
				
				colors.setVisible(false);
				shapes.setVisible(false);
				numbers.setVisible(false);
				alphabets.setVisible(false);
				
				back.setVisible(true);
				bg.setVisible(false);
				bg3.setVisible(true);

				lesson2 = true;
				
				instruction.setVisible(true);
				instruction.setText("");
				instruction.append(" _______________\n INSTRUCTION \n"
						+" _______________\n Place object in\n"
						+ "   front of the\n   camera and \n press spacebar\n"
						+ " to capture the \n image."
						+ " Hold for\n     5 seconds \n  before moving the object away.");
						
					canvas.addKeyListener(new KeyListener() {
		
					@Override
					public void keyPressed(KeyEvent e) {
						
						if (e.getKeyCode() == KeyEvent.VK_SPACE) {
							cvSaveImage("capture.jpg",image);
								try {
									img = ImageIO.read(new File("capture.jpg"));
									Thread.sleep(100); //wait
									thumbnail = Scalr.resize(img, 250);
									icon1 = new ImageIcon(thumbnail);
									label1.setVisible(true);
									label1.setIcon(icon1);
									canvas.requestFocus();
											
									} catch (IOException | InterruptedException e1) {
										e1.printStackTrace();
									}									
							empty = false;
								}						
					}
						@Override
						public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub						
						}
						@Override
						public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub						
						}							
				}); //end canvas KeyListener
						
						Thread webcam = new Thread(){ //start a new thread
							
						public void run(){
							
							try {
								if(backPressed2==false)
									grabber.start();
								else {
									grabber.restart();
									Thread.sleep(200);
								}
								
								CvMemStorage storage = CvMemStorage.create();
								Thread.sleep(200);
								
								while(canvas.isVisible() && (image=grabber.grab()) != null && lesson2==true) {
									cvClearMemStorage(storage);
	
									Question.setVisible(true);
									response.setVisible(true);
									
									/* When user presses the back button */								
									back.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(ActionEvent arg0) {
											
											//go back to main menu										
											
											bg.setVisible(true);
											bg3.setVisible(false);
											
											colors.setVisible(true);
											shapes.setVisible(true);
											numbers.setVisible(true);				
											alphabets.setVisible(true);
											
											response.setText("");
											Question.setText("");
											backPressed2 = true; //mark
											nextPressed2 = false; //reset
											
											Question.setVisible(false);
											response.setVisible(false);				
											instruction.setVisible(false);
											next.setVisible(false);
											back.setVisible(false);
											label1.setVisible(false);
											label2.setVisible(false);
											
											lesson1 = false;
											lesson2 = false;
											lesson3 = false;
											lesson4 = false;
											
											/*bgmusic = "music.wav";
										    
										    //create an audiostream from the inputstream
										    try {
										    	clip.close(); //close the previous music
												audioStream = AudioSystem.getAudioInputStream(new File(bgmusic));
												clip = AudioSystem.getClip();
											    clip.open(audioStream);
											    clip.loop(Clip.LOOP_CONTINUOUSLY); //play continuously
											    
											} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}*/
											
											canvas.dispose(); //exit
											try {
												grabber.stop();
											} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
												e.printStackTrace();
											}								
										}								
									}); //end back actionListener
									
				if(lesson2==true) {
									
									//set the initial question
									if(shape.isRect()==false) {
										Question.setText("Find the rectangle!");
									
										if(empty==false) {
										
										IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam
										
										//detect rectangle									
										shape.Rectangle(captured);
										empty = true;	
										
										if(shape.isRect()==true) {
											
											response.setText("Correct!");
											next.setVisible(true);
											
											img = ImageIO.read(new File("rect.jpg"));
											Thread.sleep(100);
											thumbnail = Scalr.resize(img, 250); //show resized resulting image
											icon2 = new ImageIcon(thumbnail); //to fit in the window
											label2.setVisible(true);
											label2.setIcon(icon2);
											
											//actions when the next button is clicked
											next.addActionListener(new ActionListener() {

												@Override
												public void actionPerformed(ActionEvent arg0) {
													
													label1.setVisible(false);
													label2.setVisible(false);
													next.setVisible(false);
													response.setText("");
													canvas.requestFocus();
													nextPressed2 = true; //mark
													backPressed2 = false; //reset
													
													/**** For Shape Recognition Part ****/
													
					if(lesson2==true) {
													
													if(shape.isRect()==true && shape.isTri()==false)
														Question.setText("Find the triangle!");
													
													else if(shape.isTri()==true && shape.isCirc()==false)
														Question.setText("Find the circle!");
													
													else if(shape.isCirc()==true && shape.isStar()==false)
														Question.setText("Find the star!");
													
													else if(shape.isStar()==true) {
														
														Question.setText("Congratulations!");
														response.setVisible(false);
														canvas.dispose(); //exit
														
														try {
															img = ImageIO.read(new File("images/end.png"));
															Thread.sleep(100); //wait
															thumbnail = Scalr.resize(img, 250);
															icon1 = new ImageIcon(thumbnail);
															label1.setVisible(true);
															label1.setIcon(icon1);
															
															img = ImageIO.read(new File("images/finished.png"));
															Thread.sleep(100);
															icon2 = new ImageIcon(img);
															label2.setVisible(true);
															label2.setIcon(icon2);
															
															response.setVisible(false);
															
														} catch (IOException | InterruptedException e1) {
															e1.printStackTrace();
														}
														response.setVisible(false);
													}
					}//end if lesson2
												}											
											});	//end next button action listener																															
										}
										else if(shape.isRect()==false) {
											response.setText("Oops...Try again");
											canvas.requestFocus();
										}
									}
									}//end if rect = false
									
									if(shape.isRect()==true && shape.isTri()==false) {
										
										if(backPressed2==true) {
											Question.setText("Find the triangle!");
											backPressed2 = false; //reset								
										}
										
										if(empty==false) {
											
										IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam
										
										//detect triangle
										shape.Triangle(captured);
										empty = true;
										
										if(shape.isTri()==true) {
											
											response.setText("Correct!");
											next.setVisible(true);
											
											img = ImageIO.read(new File("triangle.jpg"));
											Thread.sleep(100);
											thumbnail = Scalr.resize(img, 250); //show resized resulting image
											icon2 = new ImageIcon(thumbnail); //to fit in the window
											label2.setVisible(true);
											label2.setIcon(icon2);											
										}
										else if(shape.isTri()==false) {
											response.setText("Oops...Try again");
											canvas.requestFocus();
										}
										}
									}//end if triangle = false
									
									if(shape.isTri()==true && shape.isCirc()==false) {
										
										if(backPressed2==true) {
											Question.setText("Find the circle!");
											backPressed2 = false; //reset								
										}
										
										if(empty==false) {

										IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam
										
										//detect circle
										shape.Circle(captured);
										empty = true;
										
										if(shape.isCirc()==true) {
											response.setText("Correct!");
											next.setVisible(true);
											
											img = ImageIO.read(new File("circle.jpg"));
											Thread.sleep(100);
											thumbnail = Scalr.resize(img, 250); //show resized resulting image
											icon2 = new ImageIcon(thumbnail); //to fit in the window
											label2.setVisible(true);
											label2.setIcon(icon2);										
										}
										else if(shape.isCirc()==false) {
											response.setText("Oops...Try again");
											canvas.requestFocus();
										}
										}
									}//end if circle = false
									
									if(shape.isCirc()==true && shape.isStar()==false) {
										
										if(backPressed2==true) {
											Question.setText("Find the star!");
											backPressed2 = false; //reset								
										}
										
										if(empty==false) {
										
										IplImage captured = cvLoadImage("capture.jpg"); //load the image captured from the webcam
										
										//detect star
										shape.Star(captured);
										empty = true;
										
										if(shape.isStar()==true) {
											response.setText("Correct!");
											next.setVisible(true);
											
											img = ImageIO.read(new File("star.jpg"));
											Thread.sleep(100);
											thumbnail = Scalr.resize(img, 250); //show resized resulting image
											icon2 = new ImageIcon(thumbnail); //to fit in the window
											label2.setVisible(true);
											label2.setIcon(icon2);										
										}
										else if(shape.isStar()==false) {
											response.setText("Oops...Try again");
											canvas.requestFocus();
										}	
										}
									}//end if star = false
									
									if(shape.isStar()==true && backPressed2==true && nextPressed2==false) {
										//when playing a game that has been completed
										
										Question.setText("Congratulations!");
										response.setVisible(false);
										backPressed2 = false; //reset
										canvas.dispose(); //exit
									
										try {
											img = ImageIO.read(new File("images/end.png"));
											Thread.sleep(100); //wait
											thumbnail = Scalr.resize(img, 250);
											icon1 = new ImageIcon(thumbnail);
											label1.setVisible(true);
											label1.setIcon(icon1);
											
											img = ImageIO.read(new File("images/finished.png"));
											Thread.sleep(100);
											icon2 = new ImageIcon(img);
											label2.setVisible(true);
											label2.setIcon(icon2);
											
										} catch (IOException | InterruptedException e1) {
											e1.printStackTrace();
										}																	
									}//end if star = true
				}//end if lesson2 = true
									
									canvas.showImage(image); 
									
								}//end while canvas!=null
																
							} catch (Exception e) {
								e.printStackTrace();
							}
							} //end run
						}; //end thread
						webcam.start();
						
					} //end actionPerformed					
				}); //end addActionListener
				
		
		colors.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {	
			
			/* Play Music */
			
		    bgmusic = "music3.wav"; //open the sound file as a Java input stream
		    
		    //create an audiostream from the inputstream
		    try {
		    	 clip.close(); //close the previous music
				 audioStream = AudioSystem.getAudioInputStream(new File(bgmusic));
				 clip = AudioSystem.getClip();
				 clip.open(audioStream);
				 clip.loop(Clip.LOOP_CONTINUOUSLY); //play continuously
				 
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			CanvasFrame canvas = new CanvasFrame("");
			canvas.setSize(500, 415);
			canvas.setLocation(760, 120);
			canvas.setResizable(false);
			canvas.requestFocus();
			
			colors.setVisible(false);
			shapes.setVisible(false);
			numbers.setVisible(false);
			alphabets.setVisible(false);
			
			back.setVisible(true);			
			bg.setVisible(false);
			bg2.setVisible(true);
			
			lesson1 = true; //set flag
			
			instruction.setVisible(true);
			instruction.setText("");
			instruction.append(" _______________\n INSTRUCTION \n"
					+" _______________\n Place object in\n"
					+ "   front of the\n   camera and \n press spacebar\n"
					+ " to capture the \n image."
					+ " Hold for\n     5 seconds \n  before moving the object away.");
			
			canvas.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent e) {
					
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						cvSaveImage("capture.jpg",image);
							try {
								img = ImageIO.read(new File("capture.jpg"));
								Thread.sleep(100); //wait
								thumbnail = Scalr.resize(img, 250);
								icon1 = new ImageIcon(thumbnail);
								label1.setVisible(true);
								label1.setIcon(icon1);
								canvas.requestFocus();
										
								} catch (IOException | InterruptedException e1) {
									e1.printStackTrace();
								}									
						empty = false;
						}
					}//end keyPressed
				@Override
					public void keyReleased(KeyEvent arg0) {	
					}
				@Override
					public void keyTyped(KeyEvent arg0) {	
					}
				}); //end canvas.addKeyListener
					
			Thread webcam = new Thread(){ //start a new thread
				public void run(){
							
					try {
						if(backPressed1==false)
							grabber.start();
						else {
							grabber.restart();
							Thread.sleep(200); 
						}
						
						CvMemStorage storage = CvMemStorage.create();
						Question.setVisible(true);
						response.setVisible(true);
						Thread.sleep(200); 
								
						while(canvas.isVisible() && (image = grabber.grab()) != null && lesson1==true) {
							cvClearMemStorage(storage);

							/* When user presses the back button */								
							back.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									
									//go back to main menu	
									
									/*bgmusic = "music.wav";
								    
								    //create an audiostream from the inputstream
								    try {
								    	clip.close(); //close the previous music
										audioStream = AudioSystem.getAudioInputStream(new File(bgmusic));
										clip = AudioSystem.getClip();
									    clip.open(audioStream);
									    clip.loop(Clip.LOOP_CONTINUOUSLY); //play continuously
									    
									} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}*/
								
									bg.setVisible(true);
									bg2.setVisible(false);
									
									colors.setVisible(true);
									shapes.setVisible(true);
									numbers.setVisible(true);				
									alphabets.setVisible(true);
									
									response.setText("");	
									Question.setText("");
									
									Question.setVisible(false);
									response.setVisible(false);				
									instruction.setVisible(false);
									next.setVisible(false);
									back.setVisible(false);
									label1.setVisible(false);
									label2.setVisible(false);
									
									backPressed1 = true; //mark 
									nextPressed1 = false; //reset
									
									lesson1 = false;
									lesson2 = false;
									lesson3 = false;
									lesson4 = false;
									
									canvas.dispose(); //exit
									try {
										grabber.stop();
										
									} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
										e.printStackTrace();
									}							
								}								
							}); //end back actionListener							
							
			if(lesson1==true) {
								
							
							if(color.redDetected()==false) {
								Question.setText("Show me something red...");
														
								if(empty==false) {								
							    try {
									img = ImageIO.read(new File("capture.jpg")); //read the captured image
									Thread.sleep(300);
									color.DetectColorRed(img); //perform color detection
									img = ImageIO.read(new File("ColouredImg.jpg"));
									Thread.sleep(100);
									thumbnail = Scalr.resize(img, 250); //show resized thresholded image
									icon2 = new ImageIcon(thumbnail); //to fit in the window
									label2.setVisible(true);
									label2.setIcon(icon2);
									empty = true;
									
									if(color.redDetected()==true && color.greenDetected()==false) { //next color=green
										
								    	response.setText("Correct!");
										next.setVisible(true);	
										
										
										//actions when the next button is clicked
										next.addActionListener(new ActionListener() {

											@Override
											public void actionPerformed(ActionEvent arg0) {
												
												label1.setVisible(false);
												label2.setVisible(false);
												next.setVisible(false);
												response.setText("");
												canvas.requestFocus();
												nextPressed1 = true; //mark
												backPressed1 = false; //reset
											
												
												/**** For Color Detection Part ****/
												
			if(lesson1==true) {
												
												if(color.redDetected()==true && color.greenDetected()==false) {
													Question.setText("Show me something green...");												
												}
												
												else if(color.greenDetected()==true && color.blueDetected()==false) {
													Question.setText("Show me something blue...");												
												}
												
												else if(color.blueDetected()==true && color.yellowDetected()==false) {
													Question.setText("Show me something yellow...");
												}
												
												else if(color.yellowDetected()==true && color.purpleDetected()==false) {
													Question.setText("Show me something purple...");
												}
												
												if(color.purpleDetected()==true) {
													
													Question.setText("Congratulations!");
													response.setVisible(false);
													canvas.dispose(); //exit
													
													try {
														img = ImageIO.read(new File("images/end.png"));
														Thread.sleep(100); //wait
														thumbnail = Scalr.resize(img, 250);
														icon1 = new ImageIcon(thumbnail);
														label1.setVisible(true);
														label1.setIcon(icon1);
														
														img = ImageIO.read(new File("images/finished.png"));
														Thread.sleep(100);
														icon2 = new ImageIcon(img);
														label2.setVisible(true);
														label2.setIcon(icon2);							
														
													} catch (IOException | InterruptedException e1) {
														e1.printStackTrace();
													}
												}
			}//end if lesson1
											}											
										});	//end next button action listener	
										
									}
									else {
										response.setText("Please try again");
										canvas.requestFocus();
									}
									
							    } catch (IOException e1) {
									System.out.print("");
								}
							}							
							} //end main if
							
							if(color.redDetected()==true && color.greenDetected()==false) {								
								
								if(backPressed1==true) {
									Question.setText("Show me something green...");
									backPressed1 = false; //reset								
								}								
									if(empty==false) {
									try {
										img = ImageIO.read(new File("capture.jpg")); //read the captured image
										Thread.sleep(300);
										color.DetectColorGreen(img); //perform color detection
										img = ImageIO.read(new File("ColouredImg.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //scale image
										icon2 = new ImageIcon(thumbnail);
										label2.setVisible(true);
										label2.setIcon(icon2);
										empty = true;
										
										if(color.redDetected()==true && color.greenDetected()==true) { //next color=green
										
											response.setText("Correct!");
											next.setVisible(true);											
										}
										else {
											response.setText("Please try again");
											canvas.requestFocus();
										}
											
								    } catch (IOException e1) {
										System.out.print("");
									}
									}
							}//end main if
							
							if(color.greenDetected()==true && color.blueDetected()==false) {
								
								if(backPressed1==true) {
									Question.setText("Show me something blue...");
									backPressed1 = false; //reset
									
								}								
									if(empty==false) {
									try {
										img = ImageIO.read(new File("capture.jpg")); //read the captured image
										Thread.sleep(300);
										color.DetectColorBlue(img); //perform color detection
										img = ImageIO.read(new File("ColouredImg.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //scale image
										icon2 = new ImageIcon(thumbnail);
										label2.setVisible(true);
										label2.setIcon(icon2);
										empty = true;
										
										if(color.blueDetected()==true) { 
							
											response.setText("Correct!");
											next.setVisible(true);										
										}
										else {
											response.setText("Please try again");
											canvas.requestFocus();
										}
											
								    } catch (IOException e1) {
										System.out.print("");
									}
									}							    	
							}//end main if
							
							if(color.blueDetected()==true && color.yellowDetected()==false) {
								
								if(backPressed1==true) {
									Question.setText("Show me something yellow...");
									backPressed1 = false; //reset
									
								}								
									if(empty==false) {
									try {
										img = ImageIO.read(new File("capture.jpg")); //read the captured image
										Thread.sleep(300);
										color.DetectColorYellow(img); //perform color detection
										img = ImageIO.read(new File("ColouredImg.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //scale image
										icon2 = new ImageIcon(thumbnail);
										label2.setVisible(true);
										label2.setIcon(icon2);
										empty = true;
										
										if(color.yellowDetected()==true) { 
							
											response.setText("Correct!");
											next.setVisible(true);									
										}
										else {
											response.setText("Please try again");
											canvas.requestFocus();
										}
											
								    } catch (IOException e1) {
										System.out.print("");
									}
									}							    	
							}//end main if
							
							if(color.yellowDetected()==true && color.purpleDetected()==false) {
								
								if(backPressed1==true) {
									Question.setText("Show me something purple...");
									backPressed1 = false; //reset
									
								}								
									if(empty==false) {
									try {
										img = ImageIO.read(new File("capture.jpg")); //read the captured image
										Thread.sleep(300);
										color.DetectColorPurple(img); //perform color detection
										img = ImageIO.read(new File("ColouredImg.jpg"));
										Thread.sleep(100);
										thumbnail = Scalr.resize(img, 250); //scale image
										icon2 = new ImageIcon(thumbnail);
										label2.setVisible(true);
										label2.setIcon(icon2);
										empty = true;
										
										if(color.purpleDetected()==true) { 
							
											response.setText("Correct!");
											next.setVisible(true);										
										}
										else {
											response.setText("Please try again");
											canvas.requestFocus();
										}
											
								    } catch (IOException e1) {
										System.out.print("");
									}
									}							    	
							}//end main if
							
							if(color.purpleDetected()==true && nextPressed1==false && backPressed1==true) { 
								//when playing a game that has been completed
								
									Question.setText("Congratulations!");
									response.setVisible(false);
									backPressed1 = false; //reset
									canvas.dispose(); //exit
								
									try {
										img = ImageIO.read(new File("images/end.png"));
										Thread.sleep(100); //wait
										thumbnail = Scalr.resize(img, 250);
										icon1 = new ImageIcon(thumbnail);
										label1.setVisible(true);
										label1.setIcon(icon1);
										
										img = ImageIO.read(new File("images/finished.png"));
										Thread.sleep(100);
										icon2 = new ImageIcon(img);
										label2.setVisible(true);
										label2.setIcon(icon2);
										
									} catch (IOException | InterruptedException e1) {
										e1.printStackTrace();
									}
							}
			}//end while lesson1 = true
							
			canvas.showImage(image);
			
		}//end while canvas!=null
								
		} catch (Exception e) {
				e.printStackTrace();
		}
					
			}//end run
			};//end Thread
							webcam.start();
			
		}//end ActionPerformed	
		});//end addActionListener
		
		
	}//end constructor
	
    public static void main(String[] args) throws Exception {
	    
	    Main start = new Main(); //construct the GUI
	    
	    /* Play beginning song */
	    
		    //open the sound file as a Java input stream
		    bgmusic = "music.wav";
		    
		    //create an audiostream from the inputstream
		    audioStream = AudioSystem.getAudioInputStream(new File(bgmusic));
		    
		    clip = AudioSystem.getClip();
		    clip.open(audioStream);
		    clip.loop(Clip.LOOP_CONTINUOUSLY); //play continuously
	
    }
}//end class
