package bears;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;

/*
 * Subclass of BearDecorator which decorates the base bear with a bucket hat
 */
public class BucketHatDecorator extends BearDecorator {

	public BucketHatDecorator(BearInterface base, PVector p, double s) {
		super(base, p, s);
		// TODO Auto-generated constructor stub
		
		img = util.ImageLoader.loadImage("assets/images/bucket-hat.png");
	}
	
	public void decorate(Graphics2D g) {

		super.decorate(g);
		decorateWithBucketHat(g);
	}
	
	public void decorateWithBucketHat(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x - 2*scale, pos.y - 575*scale);
		g.scale(scale, scale);

		g.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, img.getWidth(), img.getHeight(), null);

		g.setTransform(af);
	}


}
