package bears;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;

/*
 * Subclass of BearDecorator which decorates the base bear with a logo of a kitty paw
 */
public class KittyPawDecorator extends BearDecorator {

	public KittyPawDecorator(BearInterface base, PVector p, double s) {
		super(base, p, s);
		// TODO Auto-generated constructor stub
		
		img = util.ImageLoader.loadImage("assets/images/kitty-paw.png");
	}
	
	public void decorate(Graphics2D g) {

		super.decorate(g);
		decorateWithKittyPaw(g);
	}
	
	public void decorateWithKittyPaw(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x + 1*scale, pos.y + 70*scale);
		g.scale(scale*0.2, scale*0.2);

		g.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, img.getWidth(), img.getHeight(), null);

		g.setTransform(af);
	}

}
