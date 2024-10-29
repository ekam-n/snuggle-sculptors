package bears;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;

/*
 * Subclass of BearDecorator which decorates the base bear with sunglasses
 */
public class SunglassesDecorator extends BearDecorator {

	public SunglassesDecorator(BearInterface base, PVector p, double s) {
		super(base, p, s);
		// TODO Auto-generated constructor stub
		
		img = util.ImageLoader.loadImage("assets/images/sunglasses.png");
	}
	
	public void decorate(Graphics2D g) {
		
		super.decorate(g);
		decorateWithSunglasses(g);
	}
	
	public void decorateWithSunglasses(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x,  pos.y - 370*scale);
		g.scale(scale*0.9, scale*0.9);
		
		g.drawImage(img, -img.getWidth()/2, -img.getHeight()/2, img.getWidth(), img.getHeight(), null);
		
		g.setTransform(af);
	}

}
