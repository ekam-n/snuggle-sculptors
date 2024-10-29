package screens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import processing.core.PVector;

/*
 * The base of the Koch Snowflake, which is a single line segment
 */
public class SnowflakeSegment {
	
	private PVector a; 
	private PVector b;
	
	private PVector pos;
	private double scale;
	
	public SnowflakeSegment(PVector a, PVector b, PVector p, double s) {
		
		this.a = a.copy();
		this.b = b.copy();
		pos = p;
		scale = s;
	}
	
	public SnowflakeSegment[] generate() {
		
		SnowflakeSegment[] children = new SnowflakeSegment[4];
		
		// split the segment into 3
		PVector v = PVector.sub(b, a);
		v.div(3);
		
		// sub-segment 1
		PVector b1 = PVector.add(a, v);
		children[0] = new SnowflakeSegment(a, b1, pos, scale);
		
		// sub-segment 4
		PVector a1 = PVector.sub(b, v);
		children[3] = new SnowflakeSegment(a1,b, pos, scale);
		
		v.rotate((float) (-Math.PI/3));
		PVector c = PVector.add(b1,  v);
		
		// sub-segment 2
		children[1] = new SnowflakeSegment(b1, c, pos,scale);
		
		// sub-segment 3
		children[2] = new SnowflakeSegment(c, a1, pos, scale);
		
		return children;
	}
	
	public void drawSegment(Graphics2D g) {
		
		g.setStroke(new BasicStroke(5));
		g.setColor(new Color(2, 60, 85));
		
//		AffineTransform af = g.getTransform();
//		g.translate(pos.x,  pos.y);
//		g.scale(scale, scale);
		
		g.draw(new Line2D.Double(a.x, a.y, b.x, b.y));
		
//		g.setTransform(af);
	}

}
