package bba.controller;

import bba.model.BitBlock;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * DrawPixels is used to take the list of pixels obtained from SCParser, and create an image that is drawn onto the canvas
 * @author Josh Thorsson
 */

public class DrawPixels {
	//TODO: consolidate to a "ControllerPixelUtil" class
	/**
	 * @param canvas The canvas we will work on to draw the BitBlock
	 * @param bitblock the BitBlock containing PixelList and dimensions
	 * @param scale 
	 * @return 
	 */
	public static Canvas drawPixels(Canvas canvas, BitBlock bitblock, int scale)
	{

		GraphicsContext gc = canvas.getGraphicsContext2D();
		int[] cArea = bitblock.getDimension();
		int iSquareSize = cArea[0];


		int j = 0;
		Color cPixel = bitblock.getPixelList().get(j).getColor();
		int listSize = bitblock.getPixelList().size();

		for (int y = 1; y <= iSquareSize;y++)
		{
			for (int x = 1; x <= iSquareSize;x++)
			{
				if (j < listSize)
				{
					cPixel = bitblock.getPixelList().get(j).getColor();
				}

				gc.setFill(cPixel);
				gc.fillRect(x*scale, y*scale, scale, scale);
				if (!(j < listSize))
				{
					cPixel = Color.BLACK;
				} else {
					j++;
				}
			}
		}
		return canvas;
	}	
}
