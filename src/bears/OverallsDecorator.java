package bears;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;
import util.ImageLoader;

/*
 * Subclass of BearDecorator which decorates the base bear with overalls
 */
public class OverallsDecorator extends BearDecorator {

	public OverallsDecorator(BearInterface base, PVector p, double s) {
		super(base, p, s);
		// TODO Auto-generated constructor stub

		img = ImageLoader.loadImage("assets/images/overalls-equipped.png");
	}

	public void decorate(Graphics2D g) {

		super.decorate(g);
		decorateWithOveralls(g);
	}

	public void decorateWithOveralls(Graphics2D g) {

		AffineTransform af = g.getTransform();
		g.translate(pos.x, pos.y + 200*scale);
		g.scale(scale*1.7, scale*1.7);

		g.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, img.getWidth(), img.getHeight(), null);

		g.setTransform(af);

	}

}
