Guidelines to compile the source code (Using Eclipse) in a x64 Windows OS:

1. Download OpenCV from http://opencv.org/downloads.html, choose version 2.4.9 and install it on the C drive

2. Copy the folder 'libraries' to your computer's C drive


*In Eclipse or other IDEs:

3. Create a new project and import all the source code from the 'test' folder

4. Configure the Java Build Path and add ALL the external libraries from the 'javacv-bin' and 'others' folders that should already be in the C drive [step 2]

5. Include the OpenCV application extension 'opencv_java249.dll' using the corresponding native library location:
C:/opencv/opencv/build/java/x64
This can only be done after step 1 is completed!

6. Click on the run button to compile and run the program


-------------------------------------------------------------------------------------------------------------------------
This is an educational image processing application for young children where there are 4 lessons to choose from:
- Colors: Users have to pick a coloured objects and show it to the webcam
- Shapes: Users need a printed shapes with solid-defined shape structures
- Numbers: Users to to write down the specified number on a piece of paper with a soft black marker
- Alphabets: Users to to write down the required first letter on a piece of paper with a soft black marker
