package screens;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import bears.BearInterface;
import main.BuildBearPanel;
import processing.core.PVector;
import util.ImageLoader;

import static main.BuildBearPanel.W_WIDTH;
import static main.BuildBearPanel.W_HEIGHT;

/*
 * The screen at the end where the user views their bear and has the option to restart 
 */
public class RestartScreen extends Screen {
	
	private BufferedImage background;
	private BearInterface finishedBear;
	
	public RestartScreen(BearInterface bear) {
		
		finishedBear = bear;
		if (finishedBear != null) { 
			
			finishedBear.setPos(new PVector(W_WIDTH/2, W_HEIGHT/2 + 20));
			finishedBear.setScale(0.4);
		}
		
		
		textPos = new PVector(W_WIDTH/2, W_HEIGHT/2 - 350);
		text = new TextButton("And... TADA! Your Customized Teddy Bear!", textPos, 64);
		
		changePos = new PVector((int)W_WIDTH/2 + 500, W_HEIGHT/2 + 350);
		change = new TextButton("Start Over", changePos, 70);
		
		background = ImageLoader.loadImage("assets/images/snowy-background.jpg");
	}

	@Override
	public void drawScreen(Graphics2D g) {
		// TODO Auto-generated method stub
		
		// draw background
		g.drawImage(background, 0, 0, BuildBearPanel.W_WIDTH, BuildBearPanel.W_HEIGHT, null);
		
		// draw finished Bear
//		AffineTransform af = g.getTransform();
//		g.scale(2,  2);
		
		if (finishedBear != null) finishedBear.decorate(g);
		
//		g.setTransform(af);
		
		// draw title
		text.drawButton(g);
		
		// draw the start over button
		change.drawButton(g);
		
	}

}
