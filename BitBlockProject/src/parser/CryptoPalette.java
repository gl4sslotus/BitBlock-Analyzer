package parser;

import javafx.scene.paint.Color;

/**
 * @author Triston Scallan
 *
 */
public class CryptoPalette implements ColorPalette {

	/* (non-Javadoc)
	 * @see parser.ColorPalette#getColor(java.lang.String)
	 */
	@Override
	public Color getColor(String token) {
		return null;
	}

	/* (non-Javadoc)
	 * @see parser.ColorPalette#setColor(java.lang.String)
	 */
	@Override
	public boolean setColor(String token) {
		return false;
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
		return false;
	}

}
