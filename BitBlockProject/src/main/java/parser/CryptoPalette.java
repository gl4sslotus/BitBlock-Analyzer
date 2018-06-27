package parser;

import java.util.*;

import javafx.scene.paint.Color;

/**
 * Currently a prototype. <p>
 * Creates a cryptographic color palette for use with the bitblock.
 * The behavior of this palette type is as follows:
 * <p>
 * The parser will allocate distinct tuples of a token and a corresponding color value.
 * No two tokens may share a color. <br>
 * Every color is selected on demand as a new token is encountered, which the color 
 * generated. The palette may expand as it encounters more and more new tokens.
 * This means that each instance of a cryptoPalette should generate a reasonably 
 * distinct combination of distinct tuples such that two unique instances will not
 * be able to match if created from scratch (even if using the same source code to 
 * generate either palette instance). <p>
 * This cryptographic method is therefore similar in concept of a cipher,
 * but instead of a symbol being substituted for another, whole tokens may be 
 * designated with a random 24 bit color, with a palette holding a maximum of 
 * all color values holding a unique token. <BR>
 * Possibility space: 
 * <ul>
 * <li>The color value has a key space of 2<sup>24</sup></li>
 * <li>The token value has a key space of (2<sup>16</sup>)<sup>31</sup></li>
 * <li>A tuple is a color value associated with a token value.</li>
 * <li>The palette can hold a SET of up to 2<sup>24</sup> unique tuples.</li>
 * </ul>
 * This means that the possibility that a single palette will be able to match another's
 * independently created palette is astronomically small.
 * <p>
 * Implementation wise, encoding the mappings for encryption should be (token->color).
 * "each token has a unique color".
 * For decoding of the mappings for decryption should be (color->token)
 * "each color routes to a distinct token".
 * This is to help make hashmap lookups of a key's value faster.
 * 
 * @author Triston Scallan
 *
 */
public class CryptoPalette implements ColorPalette {

	private Map<String, Color> palette;
	
	/* (non-Javadoc)
	 * @see parser.ColorPalette#getColor(java.lang.String)
	 */
	@Override
	public Color getColor(String token) {
		return (palette.containsKey(token)) ? palette.get(token) : palette.get(null);
	}

	/* (non-Javadoc)
	 * @see parser.ColorPalette#setColor(java.lang.String)
	 */
	@Override
	public boolean setColor(String token) {
		//initial prototype to selecting a color in a random manner while also in constant time.
		
		Random rngColor = new Random();
		Random rngSelector = new Random();
		int r = rngColor.nextInt(256);
		int g = rngColor.nextInt(256);
		int b = rngColor.nextInt(256);
		int rOffset = 0;
		int bOffset = 0;
		int gOffset = 0;
		while (true) {
			//set color to a random value
			Color color = Color.rgb(
					(r + rOffset) % 255, 
					(g + gOffset) % 255, 
					(b + bOffset) % 255);
			//if color exists then try again, otherwise mark it and return
			if (palette.putIfAbsent(token, color) == null) return true;
			
			//randomly choose an offset to increment.
			int n = rngSelector.nextInt(3);
			if ((n == 0 && rOffset < 256) || (gOffset > 255 && bOffset > 255)) 
				rOffset++;
			else if ((n == 1 && gOffset < 256) || (rOffset > 255 && bOffset > 255)) 
				gOffset++;
			else if ((n == 2 && bOffset < 256) || (gOffset > 255 && rOffset > 255))
				bOffset++;
			else return false;
		}
	}

	/* (non-Javadoc)
	 * @see parser.ColorPalette#updateColor(java.lang.String, javafx.scene.paint.Color)
	 */
	@Override
	public boolean updateColor(String token, Color color) {
		return false;
	}

	/* (non-Javadoc)
	 * @see parser.ColorPalette#containsToken(java.lang.String)
	 */
	@Override
	public boolean containsToken(String token) {
		return palette.containsKey(token);
	}

}
