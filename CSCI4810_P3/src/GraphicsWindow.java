import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class GraphicsWindow {
	
	static int[] xCoord;
	static int[] yCoord;
	static int[] xCoordSC;	// array of clipped lines (x coordinates)
	static int[] yCoordSC;	// array of clipped lines (y coordinates)
	static int numLines;
	static BresenhamAlg lineDrawer = new BresenhamAlg();
	static CohenSutherlandLineClipping lineClipper = new CohenSutherlandLineClipping();
	static MatrixFunctions matF = new MatrixFunctions();
	
	public static void main(String[] args) {
		Frame window = new Frame("Simple Graphics Program");
		window.setBackground(Color.PINK);
		
		// Clear screen
		Button clearPixB = new Button("Clear Pixels");
		clearPixB.setBounds(740, 40, 200, 30);
		clearPixB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				for (int i = 0; i < 720; i++) {
					for (int j = 0; j < 480; j++) {
						MyCanvas.Image.setRGB(i, j, 0x000000);
					} // for
				} // for
			}
		});
		window.add(clearPixB);
		
		// Display
		Button displayPixB = new Button("Display Pixels");
		displayPixB.setBounds(740, 80, 200, 30);
		// displayPixB button action handler
		displayPixB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				for (int i = 0; i < numLines; i = i + 2) {
					double[] temp = {(double) xCoord[i], (double) yCoord[i], (double) xCoord[i + 1], (double) yCoord[i + 1]};
					int[] temp2 = lineClipper.lineClip(temp);
					xCoordSC[i] = temp2[0];
					yCoordSC[i] = temp2[1];
					xCoordSC[i + 1] = temp2[2];
					yCoordSC[i + 1] = temp2[3];
					lineDrawer.brz(xCoordSC[i], yCoordSC[i], xCoordSC[i + 1], yCoordSC[i + 1]);
					System.out.println("Iteration " + i);
					System.out.println(Arrays.toString(temp));
					System.out.println(Arrays.toString(temp2));
					System.out.println(Arrays.toString(xCoordSC));
					System.out.println(Arrays.toString(yCoordSC) + "\n");
				} // for
			}
		});
		window.add(displayPixB);
		
		// Output to file
		Button outputLinesB = new Button("Output Lines");
		TextField outputNameTF = new TextField("File name here (make sure to add .txt)");
		outputLinesB.setBounds(740, 120, 200, 30);
		outputNameTF.setBounds(980, 120, 220, 30);
		outputLinesB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				try {
					String newFileName = outputNameTF.getText();
					File myFile = new File(newFileName);
					FileOutputStream outputStream = new FileOutputStream(newFileName);
					String numLinesAsString = Integer.toString((numLines / 2));
					String coordsAsString = " ";
					
					for (int i = 0; i < xCoord.length; i++) {
						coordsAsString = coordsAsString + " " + Integer.toString(xCoord[i]) + " " + Integer.toString(yCoord[i]) + " \n";
					} // for
					
					String fileString = numLinesAsString + coordsAsString;
					byte[] fileStringToBytes = fileString.getBytes();
					outputStream.write(fileStringToBytes);
					
					outputStream.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} // try
			}
		});
		window.add(outputLinesB);
		window.add(outputNameTF);
		
		// Translate
		Button translateB = new Button("Translate");
		TextField translateTx = new TextField("x");
		TextField translateTy = new TextField("y");
		translateB.setBounds(740, 160, 200, 30);
		translateTx.setBounds(980, 160, 30, 30);
		translateTy.setBounds(1020, 160, 30, 30);
		translateB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				for (int i = 0; i < numLines; i = i + 2) {
					int[] temp = {xCoord[i], yCoord[i], xCoord[i + 1], yCoord[i + 1]};
					int[] temp2 = matF.translate(temp, Integer.parseInt(translateTx.getText()), Integer.parseInt(translateTy.getText()));
					xCoord[i] = temp2[0];
					yCoord[i] = temp2[1];
					xCoord[i + 1] = temp2[2];
					yCoord[i + 1] = temp2[3];
				} // for
			}
		});
		window.add(translateB);
		window.add(translateTx);
		window.add(translateTy);
		
		// Basic Scale
		Button basicScB = new Button("Basic Scale");
		TextField basicScSx = new TextField("x");
		TextField basicScSy = new TextField("y");
		basicScB.setBounds(740, 200, 200, 30);
		basicScSx.setBounds(980, 200, 30, 30);
		basicScSy.setBounds(1020, 200, 30, 30);
		basicScB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				for (int i = 0; i < numLines; i = i + 2) {
					int[] temp = {xCoord[i], yCoord[i], xCoord[i + 1], yCoord[i + 1]};
					int[] temp2 = matF.basicSc(temp, Integer.parseInt(basicScSx.getText()), Integer.parseInt(basicScSy.getText()));
					xCoord[i] = temp2[0];
					yCoord[i] = temp2[1];
					xCoord[i + 1] = temp2[2];
					yCoord[i + 1] = temp2[3];
				} // for
			}
		});
		window.add(basicScB);
		window.add(basicScSx);
		window.add(basicScSy);
		
		// Basic Rotate
		Button basicRotateB = new Button("Basic Rotate");
		TextField basicRotateAngle = new TextField("deg");
		basicRotateB.setBounds(740, 240, 200, 30);
		basicRotateAngle.setBounds(980, 240, 30, 30);
		basicRotateB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				for (int i = 0; i < numLines; i = i + 2) {
					int[] temp = {xCoord[i], yCoord[i], xCoord[i + 1], yCoord[i + 1]};
					int[] temp2 = matF.basicRot(temp, Double.parseDouble(basicRotateAngle.getText()));
					xCoord[i] = temp2[0];
					yCoord[i] = temp2[1];
					xCoord[i + 1] = temp2[2];
					yCoord[i + 1] = temp2[3];
				} // for
			}
		});
		window.add(basicRotateB);
		window.add(basicRotateAngle);
		
		// General Scale
		Button genScaleB = new Button("General Scale");
		TextField genScaleSx = new TextField("Sx");
		TextField genScaleSy = new TextField("Sy");
		TextField genScaleCx = new TextField("Cx");
		TextField genScaleCy = new TextField("Cy");
		genScaleB.setBounds(740, 280, 200, 30);
		genScaleSx.setBounds(980, 280, 30, 30);
		genScaleSy.setBounds(1020, 280, 30, 30);
		genScaleCx.setBounds(1060, 280, 30, 30);
		genScaleCy.setBounds(1100, 280, 30, 30);
		genScaleB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				for (int i = 0; i < numLines; i = i + 2) {
					int[] temp = {xCoord[i], yCoord[i], xCoord[i + 1], yCoord[i + 1]};
					int[] temp2 = matF.scale(temp, 
											Integer.parseInt(genScaleSx.getText()), 
											Integer.parseInt(genScaleSy.getText()), 
											Integer.parseInt(genScaleCx.getText()), 
											Integer.parseInt(genScaleCy.getText()));
					xCoord[i] = temp2[0];
					yCoord[i] = temp2[1];
					xCoord[i + 1] = temp2[2];
					yCoord[i + 1] = temp2[3];
				} // for
			}
		});
		window.add(genScaleB);
		window.add(genScaleSx);
		window.add(genScaleSy);
		window.add(genScaleCx);
		window.add(genScaleCy);
		
		// General Rotate
		Button genRotateB = new Button("General Rotate");
		TextField genRotateAngle = new TextField("deg");
		TextField genRotateCx = new TextField("Cx");
		TextField genRotateCy = new TextField("Cy");
		genRotateB.setBounds(740, 320, 200, 30);
		genRotateAngle.setBounds(980, 320, 30, 30);
		genRotateCx.setBounds(1020, 320, 30, 30);
		genRotateCy.setBounds(1060, 320, 30, 30);
		genRotateB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				for (int i = 0; i < numLines; i = i + 2) {
					int[] temp = {xCoord[i], yCoord[i], xCoord[i + 1], yCoord[i + 1]};
					int[] temp2 = matF.rotate(temp, 
											Math.round(Double.parseDouble(genRotateAngle.getText())),
											Math.round(Double.parseDouble(genScaleCx.getText())), 
											Math.round(Double.parseDouble(genScaleCy.getText())));
					xCoord[i] = temp2[0];
					yCoord[i] = temp2[1];
					xCoord[i + 1] = temp2[2];
					yCoord[i + 1] = temp2[3];
				} // for
			}
		});
		window.add(genRotateB);
		window.add(genRotateAngle);
		window.add(genRotateCx);
		window.add(genRotateCy);
		
		// Input file
		Button readFileB = new Button("Read File");
		TextField inputFileName = new TextField("File name of datalines here (case sensitive, including file extension)");
		readFileB.setBounds(740, 440, 100, 30);
		inputFileName.setBounds(740, 400, 360, 30);
		
		// readFileB button action handler
		readFileB.addActionListener(new ActionListener() {  
		    public void actionPerformed(ActionEvent e) {  
		    	String fileName = inputFileName.getText();
				try {
					File datalines = new File(fileName);
					Scanner scanner = new Scanner(datalines);
					numLines = 2 * (scanner.nextInt());
					xCoord = new int[numLines];
					yCoord = new int[numLines];
					xCoordSC = new int[numLines];
					yCoordSC = new int[numLines];
					
					for (int i = 0; i < numLines; i++) {
						xCoord[i] = scanner.nextInt();
						yCoord[i] = scanner.nextInt();
					} // for
					
					scanner.close();
				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				} // try
				System.out.println(Arrays.toString(xCoord));
				System.out.println(Arrays.toString(yCoord));
	        }  
	    });
		window.add(readFileB);
		window.add(inputFileName);
		
		// Handling closing window
		window.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent we) {
	            window.dispose();
	            System.exit(0);
	         }
	     });
		
		Canvas pic = new MyCanvas();
		window.add("Center", pic);
		
		window.setSize(1280, 480);
		window.setVisible(true);
		
		// Repaint picture
		pic.repaint();
		
	} // main method

} // GraphicsWindow class
