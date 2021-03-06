package parser;

import javax.imageio.ImageIO;
import bba.model.BitBlock;
import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * creates a png file based on the current bitblock
* @author Anthony Hoang
* @author Triston Scallan
*/
public class WritetoFile {
	
	private Color cPixel;
	
	/**
	 * creates a png file based on the current bitblock
	 * @param f file to be created
	 * @param bitblock bitblock currently in use
	 * @param scale current pixel scaler
	 * @throws IOException
	 */
	public WritetoFile(File f, BitBlock bitblock, int scale) throws IOException
	{
		
		int side = bitblock.getDimension()[0];
		BufferedImage image = new BufferedImage(side*scale, side*scale, BufferedImage.TYPE_INT_RGB);
		
		// setting up image
		int index = 0;
		for (int y = 0; y < side; y++)
		{
			for (int x = 0; x < side; x++)
			{
				// get color 
				if (index < bitblock.getPixelList().size())
					cPixel = bitblock.getPixelList().get(index).getColor();
				else // padding
					cPixel = Color.BLACK;

				index++;
				// scale each pixel
				for (int ay = y*scale; ay < (y*scale)+scale; ay++)
					for (int ax = x*scale; ax < (x*scale)+scale; ax++)
							// .hashCode() produce hex: ab12ab12ff
							// we dont need the last 8bits
							image.setRGB(ax, ay, cPixel.hashCode() >> 8);
				
			}
		}
		
		// write to file
		try {
			ImageIO.write(image, "png", f);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
}
