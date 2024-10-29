package wardrobe;

import bears.BearInterface;
import bears.HeartDecorator;
import processing.core.PVector;

import static main.BuildBearPanel.W_HEIGHT;

/*
 * A "Logo" item; an icon of a heart which can be picked up by the user and create a subclass of BearDecorator
 */
public class Heart extends Logo {

	public Heart() {
		super(new PVector(1191/2 + 280, W_HEIGHT - 215/2), 0.1, "assets/images/heart.png");
		// TODO Auto-generated constructor stub
	}

	@Override
	public BearInterface equipToBear(BearInterface bear) {
		// TODO Auto-generated method stub
		return new HeartDecorator(bear, bear.getPos(), bear.getScale());
	}

}
