package bears;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;
import util.ImageLoader;

/*
 * Subclass of Bear which loads up an image of a white bear
 */
public class WhiteBear extends Bear {

	public WhiteBear(PVector pos, double scale) {
		super(pos, scale);
		// TODO Auto-generated constructor stub
		
		img = ImageLoader.loadImage("assets/images/white-teddy.png");
	}
}
