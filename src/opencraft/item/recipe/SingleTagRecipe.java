package opencraft.item.recipe;

import opencraft.item.EntityItem;

public class SingleTagRecipe implements IRecipe {
	
	String input;
	EntityItem output;
	
	public SingleTagRecipe(EntityItem result, String tag) {
		this.input = tag;
		this.output = result;
	}

	@Override
	public boolean match(EntityItem[][] grid) {
		EntityItem in = null;
		boolean hasElem = false;
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[i].length; j++) {
				if(hasElem) {
					return false;
				} else {
					in = grid[i][j];
				}
			}
		}
		if (in == null) return false;
		return in.getTags().contains(input);
	}

	@Override
	public EntityItem result() {
		return output;
	}

}
