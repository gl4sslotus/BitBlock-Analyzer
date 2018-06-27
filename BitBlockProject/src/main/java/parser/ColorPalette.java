package parser;

import javafx.scene.paint.Color;

/**
 * The ColorPalette interface is only concerns that all children
 * can effectively store the token->color relation as tuples.
 * <p>
 * All distinct tokens must have a non-null color, but not all colors must have a token.
 * <br>
 * All child classes <b>must</b> implement a null-color tuple or else it will be 
 * incompatible. This tuple should be `null` : `Color.BLACK`.
 * @author Triston Scallan
 *
 */
public interface ColorPalette {
	//TODO: convert into a functional interface?
	
	/**
	 * Gets the Color object associated with the token.
	 * Assumed to always return the correct color as 
	 * described by the child class. 
	 * <p>
	 * Any tokens without an association will return the null-color tuple.
	 * @param token the token key
	 * @return Color associated with token
	 */
	public abstract Color getColor(String token);
	
	/**
	 * Sets the Color object associated with the token.
	 * This process should be automatic and implementation specific.
	 * If a tuple already exists, this method should not make any changes
	 * and then return false.
	 * @see #updateColor(String, Color)
	 * @param token the token key
	 * @param color the color value
	 * @return if the color was added to the table
	 */
	public abstract boolean setColor(String token);
	
	/**
	 * Manually sets the token to the specific color value within the table
	 * @param token the token key
	 * @param color the color value
	 * @return if the token was updated
	 */
	public abstract boolean updateColor(String token, Color color); 
	
	/**
	 * Checks if the token exists in the palette.
	 * @param token the key to check for
	 * @return true if the token has tuple
	 */
	public abstract boolean containsToken(String token);
}