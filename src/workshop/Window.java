package workshop;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import processing.core.PVector;

/*
 * The background windows. 
 */
public class Window {
	
	private PVector pos;
	private PVector INITIAL_POS;
	private double scale;
	private static BufferedImage window = util.ImageLoader.loadImage("assets/images/window.png");
	
	public static final int WIDTH = (int)(window.getWidth() * 0.2);
	
	public Window(PVector p, double s) {
		
		pos = p;
		INITIAL_POS = p;
		scale = s;
	}
	
	public void drawWindow(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x, pos.y);
		g.scale(scale,  scale);
		
		g.drawImage(window,  -window.getWidth()/2,  -window.getHeight()/2, window.getWidth(), window.getHeight(), null);
		
		g.setTransform(af);
		
		
	}
	
	public PVector getPos() {
		
		return pos;
	}
	
	public void reset() {
		
		pos = INITIAL_POS;
	}
	
	public void shiftLeft() {
		
		pos = new PVector(pos.x - 3, pos.y);
	}
	
	public void shiftRight() {
		
		pos = new PVector(pos.x + 3, pos.y);
	}

}
