package wardrobe;

import static main.BuildBearPanel.W_HEIGHT;

import bears.BearInterface;
import bears.RibbonDecorator;
import processing.core.PVector;

/*
 * An "Accessory" item; a ribbon which can be picked up by the user and create a subclass of BearDecorator
 */
public class Ribbon extends Accessory {

	public Ribbon() {
		super(new PVector(1191/2 - 20, W_HEIGHT - 215/2), 0.4, "assets/images/ribbon.png");
		// TODO Auto-generated constructor stub
	}

	@Override
	public BearInterface equipToBear(BearInterface bear) {
		// TODO Auto-generated method stub
		return new RibbonDecorator(bear, bear.getPos(), bear.getScale());
	}

}
