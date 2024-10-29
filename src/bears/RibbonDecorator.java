package bears;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;

/*
 * Subclass of BearDecorator which decorates the base bear with a ribbon
 */
public class RibbonDecorator extends BearDecorator {

	public RibbonDecorator(BearInterface base, PVector p, double s) {
		super(base, p, s);
		// TODO Auto-generated constructor stub
		
		img = util.ImageLoader.loadImage("assets/images/ribbon-equipped.png");
	}
	
	public void decorate(Graphics2D g) {
		
		super.decorate(g);
		decorateWithRibbon(g);
	}
	
	public void decorateWithRibbon(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x - 160*scale,  pos.y - 500*scale);
		g.scale(scale*1.75, scale*1.75);
		
		g.drawImage(img, -img.getWidth()/2, -img.getHeight()/2, img.getWidth(), img.getHeight(), null);
		
		g.setTransform(af);
	}

}
