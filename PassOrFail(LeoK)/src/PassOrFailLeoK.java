/*
 * Created by: Leo Kay
 * Created on: 10 March 2020
 * Created for: ICS4U Programming
 * Assignment 2 - Pass or Fail
 * This program calculates if a student in a class passed or failed 
 * the course, and displays the highest and lowest mark of the class
*/

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.Arrays;
import java.math.*;

public class PassOrFailLeoK {

	protected Shell shlPassOrFail;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PassOrFailLeoK window = new PassOrFailLeoK();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlPassOrFail.open();
		shlPassOrFail.layout();
		while (!shlPassOrFail.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	//This function returns the max mark
	public double GetMaxMark(double finalMarksCopy[]) {
		//variables
		double max = finalMarksCopy[0];
		double maxTemp;
		
		//get the max num
		for (int i = 1; i < finalMarksCopy.length; i++) {
			//set the temp max to the mark at the counter
			maxTemp = finalMarksCopy[i];
			
			//check to see if the temp is greater than the actual
			if (maxTemp > max) {
				//new max
				max = maxTemp;
			}
		}
		
		//return the max mark
		return max;
	}
	
	//This function returns the min mark
	public double GetMinMark(double finalMarksCopy[]) {
		//variables
		double min = finalMarksCopy[0];
		double minTemp;
		
		//get the min num
		for (int i = 1; i < finalMarksCopy.length; i++) {
			//set the temp min to the mark at the counter
			minTemp = finalMarksCopy[i];
			
			//check to see if the temp is less than the actual
			if (minTemp < min) {
				//new max
				min = minTemp;
			}
		}
		
		//return the min mark
		return min;
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlPassOrFail = new Shell();
		shlPassOrFail.setSize(301, 150);
		shlPassOrFail.setText("Pass or Fail by Leo Kay");
		
		Button btnPassOrFail = new Button(shlPassOrFail, SWT.NONE);
		btnPassOrFail.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//variables
				double[] weights = new double[4];
				String[] weightsString = new String[4];
				double[] marks = new double[4];
				String[] marksString = new String[4];
				double[] finalMarks;
				double finalMark;
				double max;
				double min;
				
				int numStudents;
				int pass;
				int fail;
			
				BufferedReader reader;
				BufferedWriter writer;
				
				//try to get the text in the files and get the output files
				try {
					//read the input file and get the output file
					reader = new BufferedReader(new FileReader("Input.txt"));
					writer = new BufferedWriter(new FileWriter("Output.txt"));
					
					//read the first line
					String line = reader.readLine();
					
					//read the next while they are not equal to null
					while (line != null) {
						//initialize variables
						pass = 0;
						fail = 0;
						max = 0;
						min = 0;
						
						//get the weights
						weightsString = line.split("\\s+");
						
						//parse the strings to doubles 
						for (int i = 0; i < 4; i++) {
							weights[i] = Double.parseDouble(weightsString[i]);
						}
						
						//get the number of student
						line = reader.readLine();
						numStudents = Integer.parseInt(line);
						
						//set the length of finalMarks
						finalMarks = new double[numStudents];
						
						//loop through the students
						for (int i = 0; i < numStudents; i++) {
							//get the marks of the student
							line = reader.readLine();
							marksString = line.split("\\s+");
							
							//parse the marks to doubles
							for (int x = 0; x < 4; x++) {
								marks[x] = Double.parseDouble(marksString[x]);
							}
							
							//get the final marks of the student
							finalMark = (marks[0]*(weights[0] / 100)) + (marks[1]*(weights[1] / 100)) + (marks[2]*(weights[2] / 100)) + (marks[3]*(weights[3] / 100));
							
							//check to see if the student passed or failed
							if (finalMark < 50) {
								//failed
								fail = fail + 1;
							}
							else {
								//passed
								pass = pass + 1;
							}
							
							//add the final mark to an array of the class' marks
							finalMarks[i] = finalMark; 
						}
						//get the max and min marks
						max = GetMaxMark(finalMarks);
						min = GetMinMark(finalMarks);
						
						//display the answer
						writer.write("Passed: " + pass + "   Fail: " + fail + "   Max: " + Math.round(max) + "   Min: " + Math.round(min));
						writer.newLine();
						
						//read the next line, this is the weights for the next set of students
						line = reader.readLine();
					}
					//close the reader and writer
					reader.close();
					writer.close();
				}catch (Exception e1) {
					//display an error box
					JOptionPane.showMessageDialog(null, "Please enter the correct information", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnPassOrFail.setBounds(89, 58, 102, 43);
		btnPassOrFail.setText("Pass or Fail");
		
		Label lblClickTheButton = new Label(shlPassOrFail, SWT.NONE);
		lblClickTheButton.setBounds(10, 10, 265, 42);
		lblClickTheButton.setText("Click the button below to see how many students \r\npassed and failed:");

	}
}
