package screens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import processing.core.PVector;

/*
 * The class for a button, used to display text as well as change from screen to screen
 */
public class TextButton {
	
	private PVector buttonFramePos;
	private String buttonText;
	private Rectangle2D.Double buttonFrame;
	private int stringWidth;
	private int stringHeight;
	private int buttonFrameWidth;
	private int buttonFrameHeight;
	private float buttonTextSize;
	private Color toPrintColor = new Color(115, 210, 240);
	private int padding = 0;
	
	public TextButton(String text, PVector pos, int textSize) {
		
		buttonText = text;
		buttonFramePos = pos;
		buttonTextSize = textSize;
	}
	
	public TextButton(String text, PVector pos, int textSize, int pad) {
		
		buttonText = text;
		buttonFramePos = pos;
		buttonTextSize = textSize;
		padding = pad;
		
	}
	
	public void drawButton(Graphics2D g) { 
		
		Font f;
		
		try { 
			
			f = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/9163-MarkerFelt-Thin-01.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        ge.registerFont(f);
	        f = f.deriveFont(buttonTextSize);
			g.setFont(f);
		}
		
		catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return;
        }
   
		
		FontMetrics fm = g.getFontMetrics(f);
		
		stringWidth = fm.stringWidth(buttonText);
		stringHeight = fm.getHeight();
		
		buttonFrameWidth = stringWidth + 50;
		buttonFrameHeight = stringHeight + 25;
		buttonFrame = new Rectangle2D.Double(0, 0, buttonFrameWidth + padding, buttonFrameHeight);
		
		AffineTransform af = g.getTransform();
		g.translate(buttonFramePos.x - buttonFrameWidth/2, buttonFramePos.y - buttonFrameHeight/2);
		
		// draw the button frame
		g.setColor(Color.white);
		g.fill(buttonFrame);
		g.setColor(toPrintColor);
		g.setStroke(new BasicStroke(7));
		g.draw(buttonFrame);
		g.setStroke(new BasicStroke(1));
		
		// draw the button text
		g.drawString(buttonText, 25 + padding/2, buttonFrameHeight - 25);
		
		g.setTransform(af);
		
	}
	
	public boolean clickedButton(double x, double y) {
		
		return (x > (buttonFramePos.x - buttonFrameWidth/2) && x < buttonFramePos.x - buttonFrameWidth/2 + buttonFrameWidth ) &&
				(y > (buttonFramePos.y - buttonFrameHeight/2) && y < buttonFramePos.y - buttonFrameHeight/2 + buttonFrameHeight);
	
	}
}
