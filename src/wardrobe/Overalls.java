package wardrobe;

import bears.*;
import processing.core.PVector;

import static main.BuildBearPanel.W_HEIGHT;

import bears.BearInterface;

/*
 * A "Top" item; overalls which can be picked up by the user and create a subclass of BearDecorator
 */
public class Overalls extends Top {

	public Overalls() {
		super(new PVector(1191/2 + 280, W_HEIGHT - 215/2), 0.4, "assets/images/overalls.png");
	}

	@Override
	public BearInterface equipToBear(BearInterface bear) {
		// TODO Auto-generated method stub
		return new OverallsDecorator(bear, bear.getPos(), bear.getScale());
	}

}
