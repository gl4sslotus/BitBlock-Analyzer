package bba.controller;

import javafx.scene.control.*;
import javafx.scene.canvas.*;
//import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import parser.Input;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bba.MainApp;
//import bba.model.*;

/*
 * @author Josh Thorsson
 */

public class BitBlockGuiController {

	/////MENU BAR IDs
	@FXML
	private MenuItem close;		// declaring close
	@FXML 
	private MenuItem save; 		// declaring Save
	@FXML 
	private MenuItem saveAs;		// Declaring SaveAs
	@FXML
	private MenuItem open;		// Declaring Open
	@FXML
	//////LEFT PANEL
	private TextArea docTextArea;// Declaring docTextArea
	@FXML
	//////MIDDLE PANEL
	private Button start;
	@FXML
	private Button refresh;
	@FXML
	//////RIGHT PANEL
	private Canvas canvas;
	@FXML
	private TextField codeField;
	@FXML
	private TextField statTextArea;
	////variables for the controller class
	private File dataFile = null;	//temporarily hold a file
	////variables for storing the inputs and id'ing them
	private int idIndex = 0;			//start at 0
	private List<Input> inputM = new ArrayList<Input>();
	
	//private GraphicsContext gc;
	//private MainApp mainApp;
	
	public BitBlockGuiController()
	{
		
	}
	
	@FXML
	private void initialize()
	{
		
	}
	
	/**
	 * Method to use close click on Menubar 
	 * @param event
	 */
	@FXML
	void quitAction(ActionEvent event) {
		Platform.exit(); 
		System.exit(0);
	}
	
	/**
	 * Handle event for Start button 
	 * @param event
	 */
	@FXML
	void startAction(ActionEvent event) {
		
	}
	
	/**
	 * Handle event for Refresh button 
	 * @param event
	 */
	@FXML
	void refreshAction(ActionEvent event) {
		
	}
	
	/**
	 * Handle event for Open option 
	 * @param event
	 */
	@FXML
	void openAction(ActionEvent event) {
		handleOpenClick();
	}
	
	/**
	 * Handle event for SaveAs
	 * @param event
	 */
	@FXML 
	void saveAsAction(ActionEvent event) {
		handleSaveAsClick();
	}
	
	/**
	 * Handle for Save
	 * @param event
	 */
	
	@FXML
	void saveAction(ActionEvent event) {
		handleSaveClick();
	}
	
	/**
	 * Method to open a window which can load Text files 
	 */
	private void handleOpenClick()  {
		//creating JavaFX file chooser 
		FileChooser fc = new FileChooser();
		fc.setTitle("Get Text");
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"), new ExtensionFilter("All Files", "*.*"));
		
		File file = fc.showOpenDialog(MainApp.getStage());
		
		// checking the file choosen by user 
		
		if (file != null) {
			try (Scanner scan = new Scanner(file)) {
				String content = scan.useDelimiter("\\Z").next();
				docTextArea.setText(content);
				
				// saving the file for use by the saveMi
				dataFile = file;
				
				save.setDisable(false);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			//add the file into an input instance and add it to the input-array
			Input input = null;
			try {
				input = new Input(file, idIndex);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			inputM.add(idIndex, input);
			idIndex++;
			open.setDisable(true); //TODO: until we can handle multiple files, the app is only allowed to open a single file.
		}
	}

	/**
	 * Method to do save As click 
	 */
	private void handleSaveAsClick() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Save Text");
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"), new ExtensionFilter("All Files", "*.*"));
		
		File file = fc.showSaveDialog(MainApp.getStage());
		
		if (file != null) {
			try (PrintStream ps = new PrintStream(file)) {
				ps.print(docTextArea.getText());
				// saving the file for use by the save
				save.setDisable(false);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleSaveClick() {
		try (PrintStream ps = new PrintStream(dataFile)) {
			ps.print(docTextArea.getText());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}