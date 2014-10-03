package opencraft.item.recipe;

import opencraft.item.EntityItem;

public interface IRecipe {
	
	boolean match(EntityItem[][] grid);
	EntityItem result();
}
