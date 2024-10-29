package workshop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import processing.core.PApplet;
import processing.core.PVector;

import util.Util.*;

/*
 * The smoke emitted from the Train. Implemented using Perlin noise. 
 */
public class Smoke {

	private PVector pos;
	private double speedY;
	private double scale;

	private float xStart;
	private float xSeed;
	private float ySeed;
	private PApplet pa;

	public Smoke(PVector p, double spe, double sca) {

		pos = p;
		speedY = spe;
		scale = sca;

		xStart = util.Util.random(10);
		xSeed = xStart;
		ySeed = util.Util.random(10);
		pa = new PApplet();
	}

	public void drawSmoke(Graphics2D g) {

		AffineTransform af = g.getTransform();
		g.translate(pos.x +520, pos.y - 95);
		g.scale(0.4, 0.4);

		float noiseFactor;
		for (int y = 0; y <= 100; y += 5) {
			ySeed += 0.1;
			xSeed = xStart; // typewriter return
			for (int x = 0; x <= 100; x += 5) {
				xSeed += 0.1;
				noiseFactor = pa.noise(xSeed, xSeed);
				AffineTransform at = g.getTransform();
				g.translate(x, y);
				g.rotate(noiseFactor * util.Util.radians(540));
				float diameter = noiseFactor * 35;
				int grey = (int) (150 + (noiseFactor * 105));
				int alph = (int) (150 + (noiseFactor * 105));
				g.setColor(new Color(grey, grey, grey, alph));
				g.fill(new Ellipse2D.Float(-diameter / 2, -diameter / 4, diameter, diameter / 2));
				g.setTransform(at);

			}

		}
		
		g.setTransform(af);
	}
	
	public void move() {
		
		pos.y += speedY;
	}
	
	public boolean detectBounds() {
		
		return pos.y + 600 < 0;
	}
	
	

}
