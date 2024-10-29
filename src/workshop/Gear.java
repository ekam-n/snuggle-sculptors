package workshop;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import processing.core.PVector;

/*
 * An abstract class which will be used by the different coloured gears on the conveyor belt
 */
public abstract class Gear {
	
	private PVector pos; 
	private PVector INITIAL_POS;
	private double scale;
	private boolean rotating = false;
	private double angle = 0;
	private int angleRotDir = 1;
	BufferedImage img;
	
	public Gear(PVector p, double s, String filepath) {
		
		pos = p;
		INITIAL_POS = p;
		scale = s;
		img = util.ImageLoader.loadImage(filepath);
	}
	
	public void drawGear(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x,  pos.y);
		g.scale(scale, scale);
		
		if (rotating) angle += Math.PI/60;
		if (angle > Math.PI || angle < -Math.PI) angle = 0;
			
		g.rotate(angle * angleRotDir);
		g.drawImage(img, -img.getWidth()/2, -img.getHeight()/2, img.getWidth(), img.getHeight(), null);
		
		g.setTransform(af);
	}
	
	public void reset() {
		
		pos = INITIAL_POS;
		rotating = false;
		angle = 0;
		angleRotDir = 1;
	}
	
	public void shiftLeft( ) {
		
		pos = new PVector(pos.x - 3, pos.y);
	}
	
	public void shiftRight() {
		
		pos = new PVector(pos.x + 3, pos.y);
	}
	
	public void rotateLeft() {
		
		angleRotDir = -1;
		rotating = true;
	}
	
	public void rotateRight() {
		
		angleRotDir = 1;
		rotating = true;
	}
	
	public void stopRotating() {
		
		rotating = false;
	}

}
