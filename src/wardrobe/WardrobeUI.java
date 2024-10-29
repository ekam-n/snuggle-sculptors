package wardrobe;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import processing.core.PVector;

/*
 * A class for the UI in which the wardrobe items are placed in
 */
public class WardrobeUI {
	
	private PVector pos;
	private int width;
	private int height;
	private BufferedImage container;
	
	public WardrobeUI(PVector pos) {
		
		this.pos = pos;
		container = util.ImageLoader.loadImage("assets/images/UI.png");
		width = container.getWidth();
		height = container.getHeight();
	}
	
	public void drawUI(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x, pos.y);
		g.scale(0.32,  0.32);
		
		g.drawImage(container, -width/2, -height/2, width, height, null);
		
		g.setTransform(af);
		
	}
	
}
