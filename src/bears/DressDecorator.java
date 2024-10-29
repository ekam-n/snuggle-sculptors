package bears;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;
import util.ImageLoader;

/*
 * Subclass of BearDecorator which decorates the base bear with a dress
 */
public class DressDecorator extends BearDecorator {

	public DressDecorator(BearInterface base, PVector p, double s) {
		super(base, p, s);
		// TODO Auto-generated constructor stub

		img = ImageLoader.loadImage("assets/images/dress-equipped.png");
	}

	public void decorate(Graphics2D g) {

		super.decorate(g);
		decorateWithDress(g);
	}

	public void decorateWithDress(Graphics2D g) {

		AffineTransform af = g.getTransform();
		g.translate(pos.x, pos.y + 160*scale);
		g.scale(scale*1.65, scale*1.65);

		g.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, img.getWidth(), img.getHeight(), null);

		g.setTransform(af);
	}

}
