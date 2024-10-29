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

import bears.Bear;
import bears.BearFactory;
import bears.BearInterface;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import main.BuildBearPanel;
import processing.core.PVector;
import util.ImageLoader;
import util.MinimHelper;
import wardrobe.WardrobeUI;
import workshop.WorkshopEnvironment;

import static main.BuildBearPanel.W_WIDTH;
import static main.BuildBearPanel.W_HEIGHT;

/*
 * The main screen which displays the workshop as the user interacts with it
 */
public class MainScreen extends Screen {
	
	// main object factories
	private BearFactory bearFactory = new BearFactory();
	
	private Minim minim;
	private AudioPlayer selectObject;
	
	private boolean bearSelected = false;
		
	// main objects
	private ArrayList<BearInterface> bears = new ArrayList<BearInterface>(); // initial collection of bears
	private WorkshopEnvironment workshop = new WorkshopEnvironment(); 
	private BearInterface selectedBear = null; // bear that has been selected and we are working with
	
	// states
	private static int state = 0;
	private static final int SELBEAR = 0;
	private static final int SELTOP = 1;
	private static final int SELACCESS = 2;
	private static final int SELLOGO = 3;
	private static final int RESTART = 4;
	
	private PVector restartPos = new PVector((int)W_WIDTH/2, W_HEIGHT/2 + 300);
	private TextButton restartButton = new TextButton("Restart", restartPos, 70);
	
	public MainScreen() {
		
		textPos = new PVector((int)(W_WIDTH/2), (int)(W_HEIGHT/2 - 380));
		text = new TextButton("Click A Bear to Begin Customizing it!", textPos, 64);
		
		bears.add(bearFactory.createBear("BLACK"));
		bears.add(bearFactory.createBear("BROWN"));
		bears.add(bearFactory.createBear("WHITE"));
		
		minim = new Minim(new MinimHelper());
		
		selectObject = minim.loadFile("Minimalist6.mp3");
	}

	@Override
	public void drawScreen(Graphics2D g) {
		
		workshop.drawWorkshop(g, selectedBear);
		text.drawButton(g);
		
		if (state == SELBEAR) for (BearInterface b : bears) b.decorate(g);
		
	}
	
	public void checkInteractions(double mouseX, double mouseY) {
		
		if (state == SELBEAR) checkBearSel(mouseX, mouseY);
		
		else if (state == SELTOP) { 
			
			dragItems(mouseX, mouseY);
			checkTops(mouseX, mouseY);
		}
		
		else if (state == SELACCESS) {
			
			dragItems(mouseX, mouseY);
			checkAccessories(mouseX, mouseY);
		}
		
		else if (state == SELLOGO) {
			
			dragItems(mouseX, mouseY);
			checkLogos(mouseX, mouseY);
		}
		
		// check for lever movement at all times it's needed
		if (state == SELBEAR || state == SELTOP || state == SELACCESS || state == SELLOGO) {
		
			moveLever(mouseX, mouseY);
		}
	}
	
	public void checkBearSel(double mouseX, double mouseY) {
		
	    Iterator<BearInterface> iterator = bears.iterator();
	    while (iterator.hasNext()) {
	    	
	        BearInterface bj = iterator.next();
	        if (bj.clickedBear(mouseX, mouseY)) {
	        	
	            // Set selected bear and break
	            selectedBear = bj;
	            break;
	        }
	    }
	    
	    
	    // if a bear is clicked, remove all others
	    if (selectedBear != null) {
	    	
		    iterator = bears.iterator();
		    while (iterator.hasNext()) {
		        BearInterface bj = iterator.next();
		        if (bj != selectedBear) {
		            iterator.remove();
		        }
		    }
		    
		    selectedBear.setScale(0.25);
		    selectedBear.setPos(new PVector(W_WIDTH/2, W_HEIGHT/2 - 45));
		    
		    text = new TextButton("Shift the Lever Right to Progress!", textPos, 64);
		    
		    if (!bearSelected) {
		    	
		    	selectObject.rewind();
			    selectObject.play();
			    bearSelected = true;
		    }
		    
//		    state = SELTOP;
		    
//		    workshop.setBearAtTop(selectedBear);
//		    workshop.changeStateSelTop();
	    }
	   
	}
	
	public void setBearUnselected() {
		
		bearSelected = false;
	}
	
	public boolean checkRestart() {
		
		return state == RESTART;
	}
	
	public void restart() {
		
		state = RESTART;
	}
	
	public BearInterface getFinishedBear() {
		
		return selectedBear;
	}
	
	public TextButton getRestartButton() {
		
		return restartButton;
	}
	
	public void moveTrain() {
		
		workshop.moveTrain();
	}
	
	public void checkShifts() {
		
		selectedBear = workshop.checkShifts(this, selectedBear);
	}
	
	public void moveLever(double mouseX, double mouseY) {
		
		workshop.moveLever(mouseX, mouseY, selectedBear);
	}
	
	public void releaseLever() {
		
		workshop.releaseLever();
	}
	
	public void dragItems(double mouseX, double mouseY) {
		
		workshop.dragItems(mouseX, mouseY);
	}
	
	public void resetItems(double mouseX, double mouseY) {
		
		workshop.resetItems(mouseX, mouseY);
	}
	
	public void resetAllBears() {
		
		while (bears.size() > 0) bears.remove(0);
		selectedBear = null;
		
		bears.add(bearFactory.createBear("BLACK"));
		bears.add(bearFactory.createBear("BROWN"));
		bears.add(bearFactory.createBear("WHITE"));

	}
	
	public void checkTops(double mouseX, double mouseY) {
		
		selectedBear = workshop.checkTops(mouseX, mouseY, selectedBear);
		
	}
	
	public void checkAccessories(double mouseX, double mouseY) {
		
		selectedBear = workshop.checkAccessories(mouseX, mouseY, selectedBear);
	}
	
	public void checkLogos(double mouseX, double mouseY) {
		
		selectedBear = workshop.checkLogos(mouseX, mouseY, selectedBear);
	}
	
	public ArrayList<BearInterface> getBears() {
		
		return bears;
	}
	
	public void changeStateSelBear() {
		
		state = SELBEAR;
	}
	
	public void changeStateSelTop() {
		
		state = SELTOP;
	}
	
	public void changeStateSelAccess() {
		
		state = SELACCESS;
	}
	
	public void changeStateSelLogo() {
		
		state = SELLOGO;
	}
	
	public void changeText(String type) {
		
		text = new TextButton(type, textPos, 64);
	}
	
	
}
