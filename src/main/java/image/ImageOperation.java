package image;

import java.awt.image.BufferedImage;
import java.io.File;

public interface ImageOperation {
	public BufferedImage execute(File imageFile);
}
