package bears;

import static main.BuildBearPanel.W_HEIGHT;
import static main.BuildBearPanel.W_WIDTH;

import processing.core.PVector;

/*
 * Factory for creating different coloured bears to be later used as bases for bear decorations.
*/

public class BearFactory {

	public Bear createBear(String type) {
		
		if (type == "BLACK") {
			
			return new BlackBear(new PVector(275, W_HEIGHT/2 - 45), 0.25);
		}
		
		else if (type == "BROWN") {
			
			return new BrownBear(new PVector(W_WIDTH/2, W_HEIGHT/2 - 45), 0.25);
		}
		
		else if (type == "WHITE") {
			
			return new WhiteBear(new PVector(1175, W_HEIGHT/2 - 45), 0.25);
		}
		
		else return null;
	}
}
