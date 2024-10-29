package wardrobe;

import bears.BearInterface;
import bears.CloudsDecorator;
import processing.core.PVector;

import static main.BuildBearPanel.W_HEIGHT;

/*
 * A "Logo" item; an icon of clouds which can be picked up by the user and create a subclass of BearDecorator
 */
public class Clouds extends Logo {

	public Clouds() {
		super(new PVector(1191/2 - 20, W_HEIGHT - 215/2), 0.15, "assets/images/clouds.png");
		// TODO Auto-generated constructor stub
	}

	@Override
	public BearInterface equipToBear(BearInterface bear) {
		// TODO Auto-generated method stub
		return new CloudsDecorator(bear, bear.getPos(), bear.getScale());
	}

}
