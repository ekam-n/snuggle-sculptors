package workshop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import bears.BearInterface;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PVector;
import util.MinimHelper;

/*
 * The lever used to go between stages of the simulation. The handle and stick are Ellipse and Rectangle
 * objects, respectively. Collision between the mouse is checked for.
 */
public class Lever {
	
	private Minim minim;
	private AudioPlayer crank;
	
	private PVector pos;
	private double scale;
	private double angle = 0;
	private Ellipse2D.Double leverHandle;
	private Rectangle2D.Double leverStick;
	private Rectangle2D.Double leverBase;
	
	private boolean movingLever = false;
	private boolean shiftingWorkshop = false;
	
	private Area outline;
	
	public Lever(PVector p, double s) {
		
		pos = p;
		scale = s;
		leverHandle = new Ellipse2D.Double(-25, -180, 50, 50);
		leverStick = new Rectangle2D.Double(-10, -150, 20, 125);
		leverBase = new Rectangle2D.Double(-100,-35, 200, 70);
		
		outline = new Area(leverHandle);
		outline.add(new Area(leverStick));
		
		minim = new Minim(new MinimHelper());
		
		crank = minim.loadFile("crank.wav");
		crank.setGain(1);
	}
	
	public void drawLever(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x,  pos.y);
		g.scale(scale, scale);
		
		AffineTransform af1 = g.getTransform();
		g.rotate(angle);
		
		g.setColor(new Color(35, 31, 32));
		g.fill(leverStick);
		
		g.setColor(new Color(237, 28, 36));
		g.fill(leverHandle);
		
		g.setTransform(af1);
		
		g.setColor(new Color(109, 110, 113));
		g.fill(leverBase);
		
		g.setTransform(af);
	}
	
	private Shape getTransformedOutline() {
		
		AffineTransform af = new AffineTransform();
		af.translate(pos.x,  pos.y);
		af.rotate(angle);
		af.scale(scale, scale);
	
		return af.createTransformedShape(outline);
	
	}
	
	public void reset() {
		
		movingLever = false;
		shiftingWorkshop = false;
		angle = 0;
		
	}
	
	public void moveLever(double mouseX, double mouseY, WorkshopEnvironment workshop, BearInterface bear) {
		
//		System.out.println(moveLever);
		
		if ((clicked(mouseX, mouseY) || movingLever) && (angle > -Math.PI/4 && angle < Math.PI/4) && !shiftingWorkshop) {
			
			angle = map(mouseX, pos.x - 25, pos.x + 25, -Math.PI/12 + 0.1, Math.PI/12 - 0.1);
			
			if (!crank.isPlaying()) {
				
				crank.play();
				crank.rewind();
			}
		}
		
		else {
			
			if (angle > 0.01 || angle < -0.01) {
				
				if (angle > 0.01) {
					
					angle -= 0.01;
				}
				
				else if (angle < -0.01) {
					
					angle += 0.01;
				}
			}
			
			movingLever = false;
		}
		
		if (angle < -Math.PI/4 && !shiftingWorkshop) {
			
			shiftingWorkshop = true;
			workshop.shiftRight(bear);
			
		}
		
		else if (angle > Math.PI/4 && !shiftingWorkshop) { 
			
			shiftingWorkshop = true;
			workshop.shiftLeft(bear);
//			System.out.println("right");
		}
		
		if ((angle > -0.01 && angle < 0.01) && shiftingWorkshop) {
			
			shiftingWorkshop = false;
		}
	}
	
	public boolean getShifting() {
		
		return shiftingWorkshop;
	}
	
	public void releaseLever() {
		
		movingLever = false;
	}
	
	public boolean clicked(double mouseX, double mouseY) {
		
		boolean clicked = false;
		
		Shape outline = getTransformedOutline();
		if (outline.contains(mouseX, mouseY)) clicked = true;
		
		if (clicked == true && movingLever == false) movingLever = true;
		
		return clicked;
	}
	
	public static double map(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow)) * (toHigh - toLow);
    }

}
