package wardrobe;

import bears.BearInterface;
import bears.BucketHatDecorator;
import processing.core.PVector;

import static main.BuildBearPanel.W_HEIGHT;

/*
 * An "Accessory" item; a bucket hat which can be picked up by the user and create a subclass of BearDecorator
 */
public class BucketHat extends Accessory {

	public BucketHat() {
		super(new PVector(1191/2 + 275, W_HEIGHT - 215/2), 0.3, "assets/images/bucket-hat.png");
		// TODO Auto-generated constructor stub
	}

	@Override
	public BearInterface equipToBear(BearInterface bear) {
		// TODO Auto-generated method stub
		return new BucketHatDecorator(bear, bear.getPos(), bear.getScale());
	}

}
