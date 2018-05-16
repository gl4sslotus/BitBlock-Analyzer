package bba.controller;

import bba.model.BitBlock;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * DisplayTextOnClick displays the token associated with a color on the text field
 * @author Josh Thorsson
 * @author Triston Scallan
 * 
 * 
 */
public class DisplayTextOnClick {
	
	/**
	 * @param textField The text field responsible for displaying token
	 * @param canvas canvas associated with clicks
	 * @param bb BitBlock associated with text
	 * @return the TextField to be printed out
	 */
	public static TextField writeText(TextField textField, Canvas canvas, BitBlock bb)
	{		
		//grab coordinates from canvas when you click
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) { 
                double x = e.getX();
                double y = e.getY();
                
                x = (int) (x /8);
                y = (int) (y /8);
                
                //x * y = area
                int side = bb.getDimension()[0];
                int area = (int) ((y * side) - side + x);
                String tToken = bb.getPixelList().get(area).getToken();
                
                //set the field
                textField.setText("Pixel: " + tToken);
            }
        });
		
		return textField;
	}	
}

