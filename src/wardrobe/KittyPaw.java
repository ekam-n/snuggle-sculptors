package wardrobe;

import bears.BearInterface;
import bears.KittyPawDecorator;
import processing.core.PVector;

import static main.BuildBearPanel.W_HEIGHT;

/*
 * A "Logo" item; an icon of a kitty paw which can be picked up by the user and create a subclass of BearDecorator
 */
public class KittyPaw extends Logo {

	public KittyPaw() {
		super(new PVector(1191/2 - 310, W_HEIGHT - 215/2), 0.15, "assets/images/kitty-paw.png");
		// TODO Auto-generated constructor stub
	}

	@Override
	public BearInterface equipToBear(BearInterface bear) {
		// TODO Auto-generated method stub
		return new KittyPawDecorator(bear, bear.getPos(), bear.getScale());
	}

}
