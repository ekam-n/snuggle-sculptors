package screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static main.BuildBearPanel.W_HEIGHT;

import processing.core.PVector;

/*
 * The fractal known as a Koch snowflake, displayed on all screens except the "main" screen
 */
public class KochSnowflake {
	
	private ArrayList<SnowflakeSegment> segments = new ArrayList<SnowflakeSegment>();
	private PVector pos;
	private double speedY;
	private double scale;
	
	private PVector p1;
	private PVector p2;
	private PVector p3;
	
	private SnowflakeSegment s1;
	private SnowflakeSegment s2;
	private SnowflakeSegment s3;
	
	private SnowflakeSegment[] children = new SnowflakeSegment[4];
	
	public KochSnowflake(PVector p, double speedY, double s) {
		
		pos = p;
		this.speedY = speedY;
		scale = s;
		
		p1 = new PVector(-50, 50);
		p2 = new PVector(550, 50);
		p3 = new PVector(250, 550);
		
		s1 = new SnowflakeSegment(p1,p2, pos,scale);
		s2 = new SnowflakeSegment(p2, p3, pos, scale);
		s3 = new SnowflakeSegment(p3,p1, pos, scale);
		
		segments.add(s1);
		segments.add(s2);
		segments.add(s3);
		
		for (int i = 0; i < 4; i++) {
			
			ArrayList<SnowflakeSegment> nextGen = new ArrayList<SnowflakeSegment>();
			
			for (SnowflakeSegment seg : segments) {
				
				SnowflakeSegment[] children = seg.generate();
				addAll(children, nextGen);
			}
			
			segments = nextGen;
		}
	}
	
	public void move() {
		
		pos.y += speedY;
	}
	
	public boolean detectBounds() {
		
		return (pos.y - 100 > W_HEIGHT);
	}
	
	public void drawSnowflake(Graphics2D g) {
		
		AffineTransform af = g.getTransform();
		g.translate(pos.x,  pos.y);
		g.scale(scale, scale);
		
		for (SnowflakeSegment s : segments) s.drawSegment(g);
		
		g.setTransform(af);
	}
	
	private void addAll(SnowflakeSegment[] arr, ArrayList<SnowflakeSegment> list) {
		
		for (SnowflakeSegment s : arr) {
			
			list.add(s);
		}
	}
	
}