package wardrobe;

import static main.BuildBearPanel.W_HEIGHT;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import bears.*;
import processing.core.PVector;

/*
 * A "Top" item; a t-shirt which can be picked up by the user and create a subclass of BearDecorator
 */
public class TShirt extends Top {
	
	public TShirt() {
		
		super(new PVector(1191/2 - 20, W_HEIGHT - 215/2), 0.4, "assets/images/tshirt.png");
		
	}

	@Override
	public BearInterface equipToBear(BearInterface bear) {
		// TODO Auto-generated method stub
		
		return new TShirtDecorator(bear, bear.getPos(), bear.getScale());
//		System.out.println("equip");
	}
	
}
