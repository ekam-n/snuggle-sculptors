package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import ddf.minim.*;
import processing.core.PVector;
import screens.MainScreen;
import screens.StartScreen;
import util.MinimHelper;
import workshop.*;
import bears.*;
import screens.*;

/*
 * One of the main class which handles the main execution flow of the program and drawing everything as necessary
 */
public class BuildBearPanel extends JPanel implements ActionListener {

	public static final int W_WIDTH = 1440;
	public static final int W_HEIGHT = 870;
	
	private Minim minim;
	private AudioPlayer song;
	private AudioPlayer buttonClick;

	// variables for holding mouse position
	private double mouseX;
	private double mouseY;

//	private BearFactory bearFactory = new BearFactory();

	// Screen objects
	private StartScreen start = new StartScreen();
	private HelpScreen help = new HelpScreen();
	private MainScreen main = new MainScreen();
	private RestartScreen restart;

	// simulation states;
	private static int state = 0;
	private static final int START = 0;
	private static final int HELP = 1;
	private static final int MAIN = 2;
	private static final int RESTART = 3;

	// boolean to track whether mouse pressed or not, for mouse motion
//	private boolean mousePressed = false;

	private Timer timer;

	BuildBearPanel(JFrame frame) {

		this.setBackground(Color.white);

		setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));

		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);

		MyMouseMotionListener m2 = new MyMouseMotionListener();
		addMouseMotionListener(m2);

		int delay = 1000 / 90;

		timer = new Timer(delay, this);
		timer.start();
		
		minim = new Minim(new MinimHelper());
		
		song = minim.loadFile("cute-pack-3.wav");
		song.setGain(-15);;
		song.loop();
		
		
		buttonClick = minim.loadFile("Wood-Block1.mp3");
		

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (state == START)
			start.drawScreen(g2);

		else if (state == HELP)
			help.drawScreen(g2);

		else if (state == MAIN)
			main.drawScreen(g2);

		else if (state == RESTART)
			restart.drawScreen(g2);

		if (state == START || state == HELP || state == RESTART) {

			Screen.drawFlakes(g2);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

//		System.out.println(state);

		if (state == START || state == HELP || state == RESTART) {

			Screen.moveFlakes();
		}

		else if (state == MAIN) {

			main.moveLever(mouseX, mouseY);
			main.moveTrain();
			main.checkShifts();

			if (main.checkRestart()) {

				state = RESTART;
				restart = new RestartScreen(main.getFinishedBear());
				main.changeStateSelBear();
			}
		}

		repaint();
	}

	public class MyMouseListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {

//			mousePressed = true;

			mouseX = e.getX();
			mouseY = e.getY();

			if (state == START && start.getChange().clickedButton(mouseX, mouseY)) {

				state = MAIN;
				buttonClick.rewind();
				buttonClick.play();

			}

			else if (state == START && start.getHelp().clickedButton(mouseX, mouseY)) {

				state = HELP;
				buttonClick.rewind();
				buttonClick.play();
			}

			else if (state == HELP && help.getChange().clickedButton(mouseX, mouseY)) {

				state = START;
				buttonClick.rewind();
				buttonClick.play();
			}

			else if (state == MAIN) {

				main.checkInteractions(mouseX, mouseY);
			}

			else if (state == RESTART) {

				if (restart.getChange().clickedButton(mouseX, mouseY)) {

					state = MAIN;
					main.resetAllBears();
					buttonClick.rewind();
					buttonClick.play();
				}
			}

			main.resetItems(mouseX, mouseY);

		}

		public void mouseReleased(MouseEvent e) {

//			mousePressed = false;
			mouseX = e.getX();
			mouseY = e.getY();

			main.resetItems(mouseX, mouseY);
			main.releaseLever();
		}

	}

	public class MyMouseMotionListener extends MouseMotionAdapter {

		public void mouseDragged(MouseEvent e) {

			mouseX = e.getX();
			mouseY = e.getY();

			// check for tops being equipped
			if (state == MAIN)
				main.checkInteractions(mouseX, mouseY);

		}

	}

	public static void setRestartScreen() {

		state = RESTART;
	}

}
