package bba.model;

import java.util.*;

import parser.ColorPalette;
import parser.SCParser;
import parser.TokenizedPixel;

/**
 * BitBlock type, a visual representation of SourceCode. 
 * The BitBlock is comprised of colored pixels, a pixel for each literal and expression.
 * The color of each pixel within the list is defined on construction.
 * @author Triston Scallan
 *
 */
public class BitBlock {
	
	/** the arrayList of TokenizedPixel type to construct a BitBlock */
	private final List<TokenizedPixel> pixelList;
	/** an integer array of {width, height, remainder} in pixel units. 
	 * @see #calcDimension() 
	 */
	private final int[] dimensions;
	
	/** The constructor. <p> takes an input object and creates a pixelList from 
	 * the palette's tuples of the token-color relation.
	 * @param input given data input
	 * @param palette the palette associated with this BitBlock instance
	 */
	public BitBlock(Input input, ColorPalette palette) {
		this.pixelList = SCParser.parse(input, palette);
		this.dimensions = calcDimension();
	}

	/**
	 * @return the pixelList
	 */
	public List<TokenizedPixel> getPixelList() {
		return pixelList;
	}
	
	
	/**
	 * @return the dimensions
	 */
	public int[] getDimension() {
		return dimensions;
	}
	
	/**
	 * Calculates the dimensions of the BitBlock based on the size of this.pixelList.
	 * result would display x pixels to a row, y total rows, and r added to the y-th row 
	 * <p>
	 * NOTE: If the returned value's r == 0, then the
	 * final row should be filled, otherwise the final row should have "r" many pixels. <p>
	 * x is width, y is height, r is remainder.
	 * @return an integer array of {x, y, r}
	 */
	private int[] calcDimension() {
		int x = 0; 	//width
		int y = 0; 	//height
		int r = 0;	//remainder
		int area = this.pixelList.size(); //area of our BitBlock
		
		//dimensions of a square is described by the formula: ( x * y ) + r = area
		//finding x is easily done as a true square is x^2 = area, so x = area^0.5 
		//we must make sure x is an integer to make later calculations safer
		assert Math.floor(Math.sqrt(area)) % 1 == 0; 
		int temp = (int) Math.floor(Math.sqrt(area));
		
		//if area <= x^2 + x then x = area^0.5, otherwise x = x + 1
		x = (area <= Math.pow(temp, 2) + temp) ? temp : temp + 1; 
		
		//if area is a perfect square then y = x, otherwise y = x + 1 if there is a remainder
		y = (this.pixelList.size() == Math.pow(temp, 2)) ? temp : temp + 1;
		
		//the remainder will be area - (filled rows). 
		r = area - temp * x;
		
		//result would display x pixels to a row, y total rows, and r added to the y-th row.
		int[] result = {x, y, r}; 
		return result;
	}
	
}
