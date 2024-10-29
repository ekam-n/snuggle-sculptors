package wardrobe;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import bears.Bear;
import bears.BearDecorator;
import bears.BearInterface;
import processing.core.PVector;

/*
 * The abstract class which is the superclass for all wardrobe items, holds common properties of them
 */
public abstract class WardrobeItem {
	
	protected PVector pos;
	protected PVector initialPos;
	protected double scale;
	protected BufferedImage img;
	
	public WardrobeItem(PVector p, double s, String filepath) {
		
		pos = p;
		initialPos = p;
		scale = s;
		img = util.ImageLoader.loadImage(filepath);
	}
	
	public void drawItem(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x,  pos.y);
		g.scale(scale, scale);
		
		g.drawImage(img, -img.getWidth()/2, -img.getHeight()/2, img.getWidth(), img.getHeight(), null);
		
		g.setTransform(af);
	}
	
	public PVector getPos() {
		
		return pos;
	}
	
	public void resetPos() {
		
		pos = initialPos;
	}
	
	public boolean clicked(double mouseX, double mouseY) {
		
		boolean clicked = false;
		
		if (Math.abs(pos.x - mouseX) < img.getWidth()/2 * scale && Math.abs(pos.y - mouseY) < img.getHeight()/2 * scale) clicked = true;
		
		return clicked;
	}
	
	public void setPos(double mouseX, double mouseY) {
		
		pos = new PVector((int)mouseX, (int)mouseY);
	}
	
	public abstract BearInterface equipToBear(BearInterface bear);

}
