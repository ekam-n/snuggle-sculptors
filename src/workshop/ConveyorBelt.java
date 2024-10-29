package workshop;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import processing.core.PVector;

/*
 * The conveyor belt which the bears are placed upon
 */
public class ConveyorBelt {
	
	private PVector pos;
	private PVector INITIAL_POS;
	private int speed = 3;
	private static double scale = 0.32;
	private static int width;
	private BufferedImage belt;
	
	public ConveyorBelt(PVector p) {
		
		pos = p;
		INITIAL_POS = p;
		belt = util.ImageLoader.loadImage("assets/images/conveyor-belt.png");
		width = belt.getWidth();
	}
	
	public void drawBelt(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x,  pos.y);
		g.scale(scale, scale);
		
		g.drawImage(belt, -belt.getWidth()/2, -belt.getHeight()/2, belt.getWidth(), belt.getHeight(), null);
		
		g.setTransform(af);
	}
	
	public static int getWidth() {
		
		return (int)(width*scale) -50;
	}
	
	public double getScale() {
		
		return scale;
	}
	
	public void reset() {
		
		pos = INITIAL_POS;
	}
	
	public void shiftRight() {
		
		pos = new PVector(pos.x + speed, pos.y);
	}

}
