package workshop;

import static main.BuildBearPanel.W_HEIGHT;
import static main.BuildBearPanel.W_WIDTH;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import bears.*;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import main.*;
import processing.core.PVector;
import screens.MainScreen;
import util.ImageLoader;
import util.MinimHelper;
import wardrobe.*;

/*
 * The entire workshop. This is where the items interact with each other and changes are made. 
 * This is where the main part of the simulation is controlled from.
 */
public class WorkshopEnvironment {
	
	private Minim minim;
	private AudioPlayer selectObject;
	private AudioPlayer equipObject;

	private WardrobeUI wardrobe = new WardrobeUI(new PVector(1191 / 2 - 20, W_HEIGHT - 215 / 2));
	private ArrayList<ConveyorBelt> belts = new ArrayList<ConveyorBelt>();
	private Lever lever = new Lever(new PVector(W_WIDTH - 175, W_HEIGHT - 35), 1);
	private ArrayList<WardrobeItem> items = new ArrayList<WardrobeItem>();
	private ArrayList<Window> windows = new ArrayList<Window>();
	private ArrayList<Gear> gears = new ArrayList<Gear>();

	private Line2D.Double trainTrack = new Line2D.Double(-100, W_HEIGHT / 2 - 30, 5000, W_HEIGHT / 2 - 30);
	private Train train = new Train(new PVector(-1000, W_HEIGHT / 2 - 180), 0.25);

	private Rectangle2D.Double wall = new Rectangle2D.Double(0, 0, W_WIDTH, W_HEIGHT);
	private Color wallColor = new Color(194, 181, 155);

	private Rectangle2D.Double table = new Rectangle2D.Double(0, W_HEIGHT / 2 + 100, W_WIDTH, 500);
	private Color tableColor = new Color(89, 74, 66);

	// store the bears at each stage in case we need to reset to them
	private BearInterface bearAtTop;
	private BearInterface bearAtAccess;
	private BearInterface bearAtLOGO;

	private boolean shiftingRight = false;
	private int shiftRightIndex = 0;

	private boolean shiftingLeft = false;
	private int shiftLeftIndex = 0;
	private static final double SHIFT_SPEED = 4.5;

	private boolean slowTrain = false;

	private boolean dragging = false;
	private WardrobeItem dragged = null;

	private static int state = 0;
	private static final int SELBEAR = 0;
	private static final int SELTOP = 1;
	private static final int SELACCESS = 2;
	private static final int SELLOGO = 3;

	public WorkshopEnvironment() {

		items.add(new TShirt());
		items.add(new Dress());
		items.add(new Overalls());

		items.add(new Ribbon());
		items.add(new Sunglasses());
		items.add(new BucketHat());
		
		items.add(new Heart());
		items.add(new KittyPaw());
		items.add(new Clouds());
		
		for (int i = 0; i > -3; i--) {
			
			belts.add(new ConveyorBelt(new PVector(W_WIDTH / 2 + (ConveyorBelt.getWidth()*i), W_HEIGHT / 2 + 100)));
		}

		for (int i = -1; i < 6; i++) {

			windows.add(new Window(new PVector(W_WIDTH / 2 + Window.WIDTH * i, W_HEIGHT / 2 - 200), 0.2));
		}

		for (int i = 0; i < 12; i++) {

			gears.add(new PinkGear(new PVector(50 + 450 * i, W_HEIGHT / 2 + 155), 0.05));
			gears.add(new YellowGear(new PVector(200 + 450 * i, W_HEIGHT / 2 + 155), 0.05));
			gears.add(new GreenGear(new PVector(350 + 450 * i, W_HEIGHT / 2 + 155), 0.05));
		}
		
		minim = new Minim(new MinimHelper());
		
		selectObject = minim.loadFile("Minimalist6.mp3");
		
		equipObject = minim.loadFile("Wood-Block1.mp3");

	}

	public void drawWorkshop(Graphics2D g, BearInterface bear) {

		g.setColor(wallColor);
		g.fill(wall);

		g.setColor(tableColor);
		g.fill(table);

		for (Window w : windows)
			w.drawWindow(g);
		for (ConveyorBelt b : belts) b.drawBelt(g);
		wardrobe.drawUI(g);
		lever.drawLever(g);

		
		train.drawTrain(g);
		g.setStroke(new BasicStroke(4));
		g.setColor(Color.black);
		g.draw(trainTrack);
		g.setStroke(new BasicStroke(1));

		if (bear != null)
			bear.decorate(g);

		for (Gear gear : gears)
			gear.drawGear(g);

		if (state == SELTOP) {

			for (WardrobeItem i : items) {

				if (i instanceof Top && !(shiftingLeft || shiftingRight))
					i.drawItem(g);
			}
		}

		else if (state == SELACCESS) {

			for (WardrobeItem i : items) {

				if (i instanceof Accessory && !(shiftingLeft || shiftingRight))
					i.drawItem(g);
			}
		}

		else if (state == SELLOGO) {

			for (WardrobeItem i : items) {

				if (i instanceof Logo && !(shiftingLeft || shiftingRight))
					i.drawItem(g);
			}
		}
	}

	public BearInterface checkShifts(MainScreen main, BearInterface bear) {

		if (shiftingLeft) {

			if (shiftLeftIndex < W_WIDTH) {

				if (state != SELLOGO) {

					for (Gear gear : gears) {

						gear.shiftLeft();
						gear.rotateRight();
					}
					for (Window w : windows)
						w.shiftLeft();
					shiftLeftIndex += SHIFT_SPEED;
					slowTrain = true;

				}
				
				else if (state == SELLOGO) {
					
					for (Gear gear : gears) {
						
						gear.rotateRight();
					}
					shiftLeftIndex += SHIFT_SPEED;
					
					bear.shiftRight();
					for (ConveyorBelt b : belts) b.shiftRight();
				}

			}

			else {

				slowTrain = false;
				shiftingLeft = false;
				shiftLeftIndex = 0;
				for (Gear gear : gears)
					gear.stopRotating();

				if (state == SELBEAR) {

					changeStateSelTop();
					main.changeStateSelTop();
					main.changeText("Drag a Top Onto Your Bear!");
					setBearAtTop(bear);
				}

				else if (state == SELTOP) {

					changeStateSelAccess();
					main.changeStateSelAccess();
					main.changeText("Drag an Accessory! Or Shift the Lever Left to go Back!");
					setBearAtAccess(bear);
				}

				else if (state == SELACCESS) {

					changeStateSelLOGO();
					main.changeStateSelLogo();
					main.changeText("Drag a Logo Onto Your Bear!");
					setBearAtLOGO(bear);
				}
				
				else if (state == SELLOGO) {
					
					main.restart();
					main.setBearUnselected();
					changeStateSelBear();
					train.reset();
					train.removeSmokes();
					lever.reset();
					for (Window w : windows) w.reset();
					for (Gear gear : gears) gear.reset();
					for (ConveyorBelt b : belts) b.reset();
					main.changeText("Click A Bear to Begin Customizing it!");
				}
			}

			return bear;
		}

		else if (shiftingRight) {

			if (shiftRightIndex < W_WIDTH) {

				for (Gear gear : gears) {

					gear.shiftRight();
					gear.rotateLeft();
				}
				for (Window w : windows)
					w.shiftRight();
				shiftRightIndex += SHIFT_SPEED;
				train.setDoubleSpeed();

				return bear;
			}

			else {

				train.setNormalSpeed();
				shiftingRight = false;
				shiftRightIndex = 0;
				for (Gear gear : gears)
					gear.stopRotating();

				if (state == SELTOP) {

					changeStateSelBear();
					main.changeStateSelBear();
					main.changeText("Click A Bear to Begin Customizing it!");
					main.setBearUnselected();
					bear = resetBear(bear);
					main.resetAllBears();
				}

				else if (state == SELACCESS) {

					changeStateSelTop();
					main.changeStateSelTop();
					main.changeText("Drag a Top Onto Your Bear!");
					bear = resetBear(bear);
					return bear;
				}
				
				else if (state == SELLOGO) {
					
					changeStateSelAccess();
					main.changeStateSelAccess();
					main.changeText("Drag an Accessory! Or Shift the Lever Left to go Back!");
					bear = resetBear(bear);
					return bear;
				}

				return bear;
			}
		}

		else
			return bear;
	}

	public void shiftLeft(BearInterface bear) {

		if (state == SELBEAR || state == SELTOP || state == SELACCESS || state == SELLOGO) {

			// there is a bear selected
			if (bear != null) {

				shiftingLeft = true;
			}
		}
	}

	public void shiftRight(BearInterface bear) {

		if (state == SELTOP || state == SELACCESS || state == SELLOGO) {

			// there is a bear selected
			if (bear != null) {

				shiftingRight = true;
			}
		}

	}
	
	public void resetTrain() {
		
		train.reset();
	}

	public void moveTrain() {

		if (!slowTrain)
			train.move();
		
		else train.moveSlow();
		
		train.checkBounds();
		train.playSound();
	}

	public void moveLever(double mouseX, double mouseY, BearInterface bear) {

		if (!dragging) lever.moveLever(mouseX, mouseY, this, bear);
	}

	public void releaseLever() {

		lever.releaseLever();
	}

	public void dragItems(double mouseX, double mouseY) {

		if (!(shiftingLeft || shiftingRight)) {

			if (state == SELTOP) {

				for (WardrobeItem i : items) {

					if (i instanceof Top) {

						if (i.clicked(mouseX, mouseY) && !dragging) {

							i.setPos(mouseX, mouseY);
							dragging = true;
							dragged = i;
							selectObject.rewind();
							selectObject.play();
						}

						else if (i.clicked(mouseX, mouseY) && dragging && i == dragged) {

							i.setPos(mouseX, mouseY);
						}

					}
				}

			}

			else if (state == SELACCESS) {

				for (WardrobeItem i : items) {

					if (i instanceof Accessory) {

						if (i.clicked(mouseX, mouseY) && !dragging) {

							i.setPos(mouseX, mouseY);
							dragging = true;
							dragged = i;
							selectObject.rewind();
							selectObject.play();
						}

						else if (i.clicked(mouseX, mouseY) && dragging && i == dragged) {

							i.setPos(mouseX, mouseY);
						}

					}
				}
			}

			else if (state == SELLOGO) {

				for (WardrobeItem i : items) {

					if (i instanceof Logo) {

						if (i.clicked(mouseX, mouseY) && !dragging) {

							i.setPos(mouseX, mouseY);
							dragging = true;
							dragged = i;
							selectObject.rewind();
							selectObject.play();
						}

						else if (i.clicked(mouseX, mouseY) && dragging && i == dragged) {

							i.setPos(mouseX, mouseY);
						}

					}
				}
			}

		}

	}

	public void resetItems(double mouseX, double mouseY) {

		for (WardrobeItem i : items) {

			i.resetPos();
		}
		dragging = false;
		dragged = null;
	}

	private BearInterface resetBear(BearInterface bear) {
		
		if (state == SELBEAR) {
			
			bear = null;
		}

		if (state == SELTOP) {

			bear = getBearAtTop();
		}

		else if (state == SELACCESS) {

			bear = getBearAtAccess();
		}

		else if (state == SELLOGO) {

			bear = getBearAtLOGO();
		}

		return bear;
	}

	public BearInterface checkSection(ArrayList<WardrobeItem> items, double mouseX, double mouseY, BearInterface bear) {

		for (WardrobeItem i : items) {

			if ((Math.abs(i.getPos().x - bear.getPos().x) < 150 && Math.abs(i.getPos().y - bear.getPos().y) < 150)
					&& !(shiftingLeft || shiftingRight)) {

				bear = resetBear(bear);
				bear = i.equipToBear(bear);
				equipObject.rewind();
				equipObject.play();

				i.resetPos();
			}

		}

		return bear;

	}

	public BearInterface checkTops(double mouseX, double mouseY, BearInterface bear) {

		ArrayList<WardrobeItem> tops = new ArrayList<WardrobeItem>();

		for (WardrobeItem i : items) {

			if (i instanceof Top) {

				tops.add(i);
			}
		}

		return checkSection(tops, mouseX, mouseY, bear);
	}

	public BearInterface checkAccessories(double mouseX, double mouseY, BearInterface bear) {

		ArrayList<WardrobeItem> accessories = new ArrayList<WardrobeItem>();

		for (WardrobeItem i : items) {

			if (i instanceof Accessory) {

				accessories.add(i);
			}
		}

		return checkSection(accessories, mouseX, mouseY, bear);
	}

	public BearInterface checkLogos(double mouseX, double mouseY, BearInterface bear) {

		ArrayList<WardrobeItem> LOGOs = new ArrayList<WardrobeItem>();

		for (WardrobeItem i : items) {

			if (i instanceof Logo) {

				LOGOs.add(i);
			}
		}

		return checkSection(LOGOs, mouseX, mouseY, bear);
	}

	public BearInterface getBearAtTop() {

		return bearAtTop;
	}

	public void setBearAtTop(BearInterface bear) {

		bearAtTop = bear;
	}

	public BearInterface getBearAtAccess() {

		return bearAtAccess;
	}

	public void setBearAtAccess(BearInterface bear) {

		bearAtAccess = bear;
	}

	public BearInterface getBearAtLOGO() {

		return bearAtLOGO;
	}

	public void setBearAtLOGO(BearInterface bear) {

		bearAtLOGO = bear;
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

	public void changeStateSelLOGO() {

		state = SELLOGO;
	}

	public void resetWardobePositions() {

		for (WardrobeItem i : items) {

			i.resetPos();
		}
	}

}
