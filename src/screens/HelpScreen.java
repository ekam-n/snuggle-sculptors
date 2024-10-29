package screens;

import static main.BuildBearPanel.W_HEIGHT;
import static main.BuildBearPanel.W_WIDTH;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import main.BuildBearPanel;
import processing.core.PVector;
import util.ImageLoader;

/*
 * The Screen which is displayed when in the HELP state, gives basic overview of program
 */
public class HelpScreen extends Screen {
	
	private BufferedImage background;
	
	public HelpScreen() {
		
		changePos = new PVector((int)(W_WIDTH/2 - 278), (int)(W_HEIGHT/2 + 210));
		change = new TextButton("Back", changePos, 52);
		
		background = ImageLoader.loadImage("assets/images/snowy-background.jpg");
	}

	@Override
	public void drawScreen(Graphics2D g) {
		// TODO Auto-generated method stub
		
		// draw the help text
		Font f;
		
		try { 
			
			f = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/9163-MarkerFelt-Thin-01.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        ge.registerFont(f);
	        f = f.deriveFont(50f);
			g.setFont(f);
		}
		
		catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return;
        }
		
		int frameWidth = 700;
		int frameHeight = 500;
		Color frameColor = new Color(115, 210, 240);
		Rectangle2D.Double frame = new Rectangle2D.Double(0, 0, frameWidth, frameHeight);
		
		// draw background
		g.drawImage(background, 0, 0, BuildBearPanel.W_WIDTH, BuildBearPanel.W_HEIGHT, null);
		
		AffineTransform af = g.getTransform();
		g.translate((W_WIDTH/2 - frameWidth/2), (W_HEIGHT/2 -frameHeight/2 - 100));
		
		g.setColor(Color.white);
		g.fill(frame);
		g.setColor(frameColor);
		g.setStroke(new BasicStroke(7));
		g.draw(frame);
		g.setStroke(new BasicStroke(1));
		
		g.drawString("Welcome to Snuggle Sculptors!", 20, 65);
		
		f = f.deriveFont(35f);
		g.setFont(f);
		g.drawString("Snuggle Sculptors is a simulation of a real-life", 20, 155);
		g.drawString("workshop where you can create your own", 20, 205);
		g.drawString("teddy bear!", 20, 255);
		
		g.drawString("Step through the simulation as many times as you", 20, 355);
		g.drawString("like. Once you're done, you can come to one of our", 20, 405);
		g.drawString("workshops and build that same fuzzy friend!", 20, 455);
		
		g.setTransform(af);
				
		// draw the begin button
		change.drawButton(g);
		
	}

}
