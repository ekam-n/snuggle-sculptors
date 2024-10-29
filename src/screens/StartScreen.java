package screens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.BuildBearPanel;
import processing.core.PVector;
import util.ImageLoader;

import static main.BuildBearPanel.W_WIDTH;
import static main.BuildBearPanel.W_HEIGHT;

/*
 * The start screen, where the user has the option to start or get help
 */
public class StartScreen extends Screen {
	
	private TextButton help;
	private PVector helpPos;
	private BufferedImage background;
//	private ArrayList<KochSnowflake> flakes = new ArrayList<KochSnowflake>();

	public StartScreen() {
		
		textPos = new PVector((int)(W_WIDTH/2), (int)(W_HEIGHT/2 - 100));
		text = new TextButton("Snuggle Sculptors", textPos, 64);
		
		changePos = new PVector((int)(W_WIDTH/2 - 100), (int)(W_HEIGHT/2 + 20));
		change = new TextButton("Begin", changePos, 52);
		
		helpPos = new PVector((int)(W_WIDTH/2 + 85), (int)(W_HEIGHT/2 + 20));
		help = new TextButton("Help", helpPos, 52, 20);
		
		background = ImageLoader.loadImage("assets/images/snowy-background.jpg");
	
	}

	@Override
	public void drawScreen(Graphics2D g) {
		// TODO Auto-generated method stub
		
		// draw background
		g.drawImage(background, 0, 0, BuildBearPanel.W_WIDTH, BuildBearPanel.W_HEIGHT, null);
		
		// draw title
		text.drawButton(g);
		
		// draw the begin button
		change.drawButton(g);

		// draw the help button
		help.drawButton(g);
		

		
	}
	
	public TextButton getHelp() {
		
		return help;
	}

}
