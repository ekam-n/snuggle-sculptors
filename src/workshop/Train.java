package workshop;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PVector;
import util.MinimHelper;

import static main.BuildBearPanel.W_WIDTH;

/*
 * The train which goes around the workshop. Movement matches the camera physics of when the conveyor belt is 
 * moving.
 */
public class Train {
	
	private Minim minim;
	private AudioPlayer trainSound;
	
	private PVector pos;
	private final PVector INITIAL_POS;
	private double scale;
	private int speed = 3;
	
	private double angle = 0;
	private int angleInc = 1;
	
	private double wheelAngle = 0;
	private double attachmentAngle = 0;
	
	private int attachmentDisplacement = 0;
	private int attachmentDisplInc = -1;
	private double attachmentRotInc = 1;
	
	private BufferedImage train = util.ImageLoader.loadImage("assets/images/train.png");
	private BufferedImage conductorArm = util.ImageLoader.loadImage("assets/images/conductor-arm.png");
	private BufferedImage wheel = util.ImageLoader.loadImage("assets/images/train-wheel.png");
	private BufferedImage wheelAttachment = util.ImageLoader.loadImage("assets/images/wheel-attachment.png");
	
	private ArrayList<Smoke> smokes = new ArrayList<Smoke>();
	
	private int smokeTimer = -1;
	
	public Train(PVector p, double s) {
		
		pos = p;
		INITIAL_POS = p;
		scale = s;
		
		minim = new Minim(new MinimHelper());
		
		trainSound = minim.loadFile("train.mp3");
		trainSound.setGain(-30);
	}
	
	public void drawTrain(Graphics2D g) {
		
		drawConductorArm(g);
		for (Smoke s : smokes) s.drawSmoke(g);
		AffineTransform af = g.getTransform();
		g.translate(pos.x, pos.y);
		g.scale(scale, scale);
		
		
		
		g.drawImage(train,  -train.getWidth()/2, -train.getHeight()/2, train.getWidth(), train.getHeight(), null);
		
		g.setTransform(af);
		
		drawBigWheel(g);
		drawSmallWheels(g);
		drawWheelAttachment(g);
		
	}
	
	public void playSound() {
		
		if (pos.x + 600 > 0 && pos.x - 600 < W_WIDTH) {
			
			if (!trainSound.isPlaying()) { 
				
				trainSound.rewind();
				trainSound.play();
			}
		}
		
		else { 
			
			trainSound.pause();
		}
	}
	
	public void moveSmokes() {
	    List<Smoke> smokesToRemove = new ArrayList<>();
	    List<Smoke> smokesToAdd = new ArrayList<>();
	    
	    for (Smoke s : smokes) {
	        s.move();
	        
	        if (s.detectBounds()) {
	            smokesToRemove.add(s);   // Mark smoke for removal
	        }
	    }

	    // Perform all removals and additions outside the loop to avoid concurrent modification
	    smokes.removeAll(smokesToRemove);
	    smokes.addAll(smokesToAdd);
	}

	
	public Smoke createSmoke() {
		
		return new Smoke(pos, -2, scale);
	}
	
	public void removeSmokes() {
		
		while (smokes.size() > 0) {
			
			smokes.remove(0);
		}
	}
	
	public void reset() {
		
		pos = INITIAL_POS;
	}
	
	public void moveSlow() {
		
		pos = new PVector(pos.x + speed/2, pos.y);
		
		moveSmokes();
		if (smokeTimer < 0) { 
			
			smokes.add(createSmoke());
			smokeTimer = 10;
		}
		
		else smokeTimer--;
	}
	
	public void move() {
		
		pos = new PVector(pos.x + speed, pos.y);
		
		moveSmokes();
		if (smokeTimer < 0) { 
			
			smokes.add(createSmoke());
			smokeTimer = 10;
		}
		
		else smokeTimer--;
	}
	
	public void checkBounds() {
		
		if (pos.x > 3000) pos = INITIAL_POS;
	}
	
	public void setDoubleSpeed() {
		
		speed = 6;
	}
	
	public void setNormalSpeed() {
		
		speed = 3;
	}
	
	public void drawConductorArm(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x + 330,  pos.y - 70);
		g.scale(scale, scale);
		
		if (angle < -0.3 || angle > 0.5) angleInc *= -1;
		angle += 0.01 * angleInc;
		g.rotate(angle);
		
		g.drawImage(conductorArm, 0, -conductorArm.getHeight()/2, conductorArm.getWidth(), conductorArm.getHeight(), null);
		
		g.setTransform(af);
		
	}
	
	public void drawBigWheel(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x + 330,  pos.y + 100);
		g.scale(scale, scale);
		wheelAngle += Math.PI/60;
		if (wheelAngle > Math.PI ) wheelAngle = 0;
		
		g.rotate(wheelAngle);
		
		g.drawImage(wheel, -wheel.getWidth()/2, -wheel.getHeight()/2, wheel.getWidth(), wheel.getHeight(), null);
		
		g.setTransform(af);
	}
	
	public void drawWheelAttachment(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		
		if ((pos.x + 320 + wheelAttachment.getWidth()*scale*0.8 + attachmentDisplacement) < 
				(pos.x + 320 + wheelAttachment.getWidth()*scale*0.8 - 30) ||
				(pos.x + 320 + wheelAttachment.getWidth()*scale*0.8 + attachmentDisplacement) > 
				(pos.x + 320 + wheelAttachment.getWidth()*scale*0.8 + 30)) attachmentDisplInc *= -1;
			
		attachmentDisplacement += 1.5 * attachmentDisplInc;
			
		g.translate(pos.x + 320 + wheelAttachment.getWidth()*scale*0.8 + attachmentDisplacement,  pos.y + 75);
		
		g.scale(scale * 0.8, scale * 0.8);
		
		if (attachmentAngle < -0.3 || attachmentAngle > 0.3) attachmentRotInc *= -1;
		attachmentAngle += 0.0096 * attachmentRotInc;
		g.rotate(attachmentAngle);
		
		g.drawImage(wheelAttachment, -wheelAttachment.getWidth(), wheelAttachment.getHeight()/2, wheelAttachment.getWidth(), wheelAttachment.getHeight(), null);
		
		g.setTransform(af);
	}
	
	public void drawSmallWheels(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		
		// draw the second-to-right-most wheel
		g.translate(pos.x + 510, pos.y + 115);
		g.scale(scale*0.7, scale*0.7);
		g.rotate(wheelAngle);
		
		g.drawImage(wheel, -wheel.getWidth()/2, -wheel.getHeight()/2, wheel.getWidth(), wheel.getHeight(), null);
		
		g.setTransform(af);
		
		//draw the right-most wheel
		g.translate(pos.x + 510 + wheel.getWidth()*scale*0.7 + 10, pos.y + 115);
		g.scale(scale*0.7, scale*0.7);
		g.rotate(wheelAngle);
		g.drawImage(wheel, -wheel.getWidth()/2, -wheel.getHeight()/2, wheel.getWidth(), wheel.getHeight(), null);
		
		g.setTransform(af);
		
		//draw the left-side wheel on the second cart
		g.translate(pos.x + 10, pos.y + 115);
		g.scale(scale*0.7, scale*0.7);
		g.rotate(wheelAngle);
		
		g.drawImage(wheel, -wheel.getWidth()/2, -wheel.getHeight()/2, wheel.getWidth(), wheel.getHeight(), null);
		
		g.setTransform(af);
		
		//draw the right-side wheel on the second cart
		g.translate(pos.x + 185, pos.y + 115);
		g.scale(scale*0.7, scale*0.7);
		g.rotate(wheelAngle);
		
		g.drawImage(wheel, -wheel.getWidth()/2, -wheel.getHeight()/2, wheel.getWidth(), wheel.getHeight(), null);
		
		g.setTransform(af);
		
		//draw the left-side wheel on the third cart
		g.translate(pos.x -300, pos.y + 115);
		g.scale(scale*0.7, scale*0.7);
		g.rotate(wheelAngle);
		
		g.drawImage(wheel, -wheel.getWidth()/2, -wheel.getHeight()/2, wheel.getWidth(), wheel.getHeight(), null);
		
		g.setTransform(af);
		
		//draw the right-side wheel on the third cart
		g.translate(pos.x -125, pos.y + 115);
		g.scale(scale*0.7, scale*0.7);
		g.rotate(wheelAngle);
		
		g.drawImage(wheel, -wheel.getWidth()/2, -wheel.getHeight()/2, wheel.getWidth(), wheel.getHeight(), null);
		
		g.setTransform(af);
		
		//draw the left-side wheel on the fourth cart
		g.translate(pos.x -610, pos.y + 115);
		g.scale(scale*0.7, scale*0.7);
		g.rotate(wheelAngle);
		
		g.drawImage(wheel, -wheel.getWidth()/2, -wheel.getHeight()/2, wheel.getWidth(), wheel.getHeight(), null);
		
		g.setTransform(af);
		
		//draw the right-side wheel on the fourth cart
		g.translate(pos.x -435, pos.y + 115);
		g.scale(scale*0.7, scale*0.7);
		g.rotate(wheelAngle);
		
		g.drawImage(wheel, -wheel.getWidth()/2, -wheel.getHeight()/2, wheel.getWidth(), wheel.getHeight(), null);
		
		g.setTransform(af);
		
	}

}
