package bears;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;

/*
 * Subclass of BearDecorator which decorates the base bear with a heart logo
 */
public class HeartDecorator extends BearDecorator {

	public HeartDecorator(BearInterface base, PVector p, double s) {
		super(base, p, s);
		// TODO Auto-generated constructor stub
		
		img = util.ImageLoader.loadImage("assets/images/heart.png");
	}
	
	public void decorate(Graphics2D g) {

		super.decorate(g);
		decorateWithHeart(g);
	}
	
	public void decorateWithHeart(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x, pos.y + 80*scale);
		g.scale(scale*0.15, scale*0.15);

		g.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, img.getWidth(), img.getHeight(), null);

		g.setTransform(af);
	}

}
