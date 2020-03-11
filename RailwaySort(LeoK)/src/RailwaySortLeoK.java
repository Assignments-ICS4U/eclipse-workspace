/*
 * Created by: Leo Kay
 * Created on: 10 March 2020
 * Created for: ICS4U Programming
 * Assignment 2 - Pass or Fail
 * This program sorts the carts in ascending order
 * and displays the "points" it takes to sort them
*/

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.math.*;
import java.util.Arrays;
import java.util.ArrayList;

public class RailwaySortLeoK {

	protected Shell shlRailwaySortBy;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RailwaySortLeoK window = new RailwaySortLeoK();
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
		shlRailwaySortBy.open();
		shlRailwaySortBy.layout();
		while (!shlRailwaySortBy.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlRailwaySortBy = new Shell();
		shlRailwaySortBy.setSize(328, 153);
		shlRailwaySortBy.setText("Railway Sort by Leo Kay");
		
		Label lblClickTheButton = new Label(shlRailwaySortBy, SWT.NONE);
		lblClickTheButton.setBounds(10, 10, 292, 15);
		lblClickTheButton.setText("Click the button to get the \"points\" of the sorted carts:");
		
		Button btnSort = new Button(shlRailwaySortBy, SWT.NONE);
		btnSort.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//variables
				int numCarts;
				ArrayList<Integer> carts;
				String[] cartsString;
				int nextBigNumIndex;
				int tempNum;
				int numMoves;
				
				//buffered reader and writer
				BufferedReader reader;
				BufferedWriter writer;
				
				//try and get the points of the sort
				try {
					//read the input file and get the output file
					reader = new BufferedReader(new FileReader("Input.txt"));
					writer = new BufferedWriter(new FileWriter("Output.txt"));
					
					//read the first line
					String line = reader.readLine();
					
					//loop through the text file
					while (line != null) {
						//set variables
						numMoves = 0;
						tempNum = 0;
						nextBigNumIndex = 0;
						
						//get the numCarts
						numCarts = Integer.parseInt(line);
						
						//read the next line
						line = reader.readLine();
						
						//get the numbers of the carts
						cartsString = new String[numCarts];
						cartsString = line.split("\\s+");
						carts = new ArrayList<Integer>();
						
						//convert the strings to int
						for (int i = 0; i < cartsString.length; i++) {
							carts.add(i, Integer.parseInt(cartsString[i])); 
						}
						
						//check the length of carts
						if (carts.size() == 1) {
							//num of moves is 0
							numMoves = 0;
						}
						else {
							//loop through and sort the carts
							for (int i = 1; i < carts.size(); i++) {
								//get the index of the carts[numCarts - i]
								nextBigNumIndex = carts.indexOf(numCarts - i);
								
								//set temp num to the value of the array at the index
								tempNum = carts.get(nextBigNumIndex);
								
								//remove the cart 
								carts.remove(nextBigNumIndex);
								
								//insert the cart at the start of the array
								carts.add(0, tempNum);
								
								//add the points
								numMoves = numMoves + nextBigNumIndex;
							}	
						}
						
						//display the answer
						writer.write("The number of moves is: " + numMoves + "   The carts in order are : " + carts.toString());
						writer.newLine();
						
						//read the next line
						line = reader.readLine();
					}
					//close the reader and writer
					reader.close();
					writer.close();
					
				} catch (Exception e1) {
					//display an error box
					JOptionPane.showMessageDialog(null, "Please enter the correct information", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSort.setBounds(99, 42, 116, 51);
		btnSort.setText("Sort");

	}
}
