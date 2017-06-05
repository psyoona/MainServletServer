package img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;


public class Resacle {
	public static boolean scale(BufferedImage srcImage, String dest, String imageFormat, int destWidth,
			int destHeight) {
		boolean result = true;
		try {
			ResampleOp resampleOp = new ResampleOp(destWidth, destHeight);
			resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.None);
			BufferedImage rescaledImage = resampleOp.filter(srcImage, null);
			ImageIO.write(rescaledImage, imageFormat, new File(dest));
			System.out.println(dest);
		} catch (IOException e) {
			result = false;
		}
		return result;
	}
}
