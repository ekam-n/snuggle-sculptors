package screens;

import static main.BuildBearPanel.W_WIDTH;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;

/*
 * The abstract class which is the superclass for Screens, representing different phases in the application
 */
public abstract class Screen {

	protected PVector textPos;
	protected TextButton text;
	protected PVector changePos;
	protected TextButton change;
	private static ArrayList<KochSnowflake> flakes = new ArrayList<KochSnowflake>();

	public Screen() {

		for (int i = 0; i < W_WIDTH; i += 100) {

			flakes.add(createSnowflake());
		}
	}

	public abstract void drawScreen(Graphics2D g);

	public TextButton getChange() {

		return change;
	}

	public static void drawFlakes(Graphics2D g) {

		for (KochSnowflake s : flakes)
			s.drawSnowflake(g);
	}

	public static void moveFlakes() {
		List<KochSnowflake> flakesToRemove = new ArrayList<>();
		List<KochSnowflake> flakesToAdd = new ArrayList<>();

		for (KochSnowflake s : flakes) {
			s.move();

			if (s.detectBounds()) {
				flakesToRemove.add(s); // Mark snowflake for removal
				flakesToAdd.add(createSnowflake()); // Mark new snowflake for addition
			}
		}

		// Perform all removals and additions outside the loop to avoid concurrent
		// modification
		flakes.removeAll(flakesToRemove);
		flakes.addAll(flakesToAdd);
	}

	private static KochSnowflake createSnowflake() {

		return new KochSnowflake(new PVector(util.Util.random(-100, W_WIDTH), -100), util.Util.random(1, 5), 0.1);
	}

}
