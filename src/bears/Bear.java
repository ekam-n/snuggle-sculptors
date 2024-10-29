package bears;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import processing.core.PVector;

/*
 * Abstract class for the different coloured bears. Will be used as the base for the decorator pattern.
 */
public abstract class Bear implements BearInterface{
	
	protected PVector pos; 
	protected double scale;
	protected static final int SPEED = 3;
	protected BufferedImage img;
	
	public Bear(PVector pos, double scale) {
		
		this.pos = pos;
		this.scale = scale;
		// img must be initialized by subclasses
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
	
	public void setScale(double s) {
		
		scale = s;
	}
	
	public double getScale() {
		// TODO Auto-generated method stub
		return scale;
	}
	
	public void setPos(PVector p) {
		
		pos = p;
	}
	
	public PVector getPos() {
		
		return pos;
	}
	 
	public void decorate(Graphics2D g) {
		// TODO Auto-generated method stub
		
		AffineTransform af = g.getTransform();
		
		g.translate(pos.x,  pos.y);
		g.scale(scale, scale);
		g.drawImage(img, -img.getWidth()/2, -img.getHeight()/2, null);
		
		g.setTransform(af);
		
	}
	
	public void shiftRight() {
		
		pos.x += SPEED;
	}

}
