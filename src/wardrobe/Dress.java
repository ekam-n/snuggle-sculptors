package wardrobe;

import bears.*;
import processing.core.PVector;

import static main.BuildBearPanel.W_HEIGHT;

/*
 * A "Top" item; a dress which can be picked up by the user and create a subclass of BearDecorator
 */
public class Dress extends Top {

	public Dress() {
		super(new PVector(1191/2 - 325, W_HEIGHT - 215/2), 0.4, "assets/images/dress.png");
	}

	@Override
	public BearInterface equipToBear(BearInterface bear) {
		// TODO Auto-generated method stub
		return new DressDecorator(bear, bear.getPos(), bear.getScale());
	}

}
