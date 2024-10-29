package wardrobe;

import bears.BearInterface;
import bears.SunglassesDecorator;
import processing.core.PVector;

import static main.BuildBearPanel.W_HEIGHT;

/*
 * An "Accessory" item; sunglasses which can be picked up by the user and create a subclass of BearDecorator
 */
public class Sunglasses extends Accessory {

	public Sunglasses() {
		super(new PVector(1191/2 - 317, W_HEIGHT -215/2), 0.3, "assets/images/sunglasses.png");
		// TODO Auto-generated constructor stub
	}

	@Override
	public BearInterface equipToBear(BearInterface bear) {
		// TODO Auto-generated method stub
		return new SunglassesDecorator(bear, bear.getPos(), bear.getScale());
	}

}
