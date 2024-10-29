package bears;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;

/*
 * Subclass of BearDecorator which decorates the base bear with a logo of clouds
 */
public class CloudsDecorator extends BearDecorator {

	public CloudsDecorator(BearInterface base, PVector p, double s) {
		super(base, p, s);
		// TODO Auto-generated constructor stub
		
		img = util.ImageLoader.loadImage("assets/images/clouds.png");
	}
	
	public void decorate(Graphics2D g) {

		super.decorate(g);
		decorateWithClouds(g);
	}
	
	public void decorateWithClouds(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x, pos.y + 70*scale);
		g.scale(scale*0.21, scale*0.21);

		g.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, img.getWidth(), img.getHeight(), null);

		g.setTransform(af);
	}

}
