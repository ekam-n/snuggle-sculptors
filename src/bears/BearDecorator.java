package bears;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import processing.core.PVector;

/*
 * The superclass of the decorator pattern for the bear decorations
 */
public class BearDecorator implements BearInterface {

	protected BearInterface baseBear;
	protected BufferedImage img;
	protected PVector pos;
	protected double scale;
	protected static final int SPEED = 3;
	
	public BearDecorator(BearInterface base, PVector p, double s) {
		
		baseBear = base;
		pos = p;
		scale = s;
	}
	
	public void decorate(Graphics2D g) {
		
		baseBear.decorate(g);
	}
	
	@Override
	public PVector getPos() {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public void setPos(PVector p) {
		// TODO Auto-generated method stub
		baseBear.setPos(p);
		pos = p;
	}

	@Override
	public void setScale(double s) {
		// TODO Auto-generated method stub
		
		baseBear.setScale(s);
		scale = s;
	}
	
	public boolean clickedBear(double x, double y) {
		// TODO Auto-generated method stub
		boolean clicked = false;
		
		if(x > (pos.x - ((double) img.getWidth())/2*scale + 60*scale) && 
				x < (pos.x + ((double) img.getWidth())/2*scale - 60*scale) 
				&& y > (pos.y - ((double) img.getHeight())/2*scale + 100*scale) && 
				y < (pos.y + ((double) img.getHeight())/2*scale - 100*scale)) 
				clicked = true;
		
//		System.out.println(clicked);
		return clicked;
	}
	
	public void shiftRight() {
		
		pos.x += SPEED;
	}

	@Override
	public double getScale() {
		// TODO Auto-generated method stub
		return scale;
	}
	
}
