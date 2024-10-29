package bears;

import java.awt.Graphics2D;

import processing.core.PVector;

/*
 * The interface which will be implemented for the bear decoration pattern.
 */
public interface BearInterface {

	public void decorate(Graphics2D g2);
	
	public PVector getPos();
	
	public void setPos(PVector p);
	
	public double getScale();
	
	public void setScale(double s);
	
	public boolean clickedBear(double x, double y);
	
	public void shiftRight();
	
}
