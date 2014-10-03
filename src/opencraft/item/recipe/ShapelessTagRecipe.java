package opencraft.item.recipe;

import java.util.HashSet;
import java.util.Set;

import opencraft.item.EntityItem;

public class ShapelessTagRecipe implements IRecipe {
	
	Set<String> items = new HashSet<String>();
	EntityItem result;
	
	public ShapelessTagRecipe(EntityItem result, String... tags) {
		this.result = result;
		for (String tag : tags) {
			this.items.add(tag);
		}
	}
	
	@Override
	public boolean match(EntityItem[][] grid) {
		Set<EntityItem> elems = new HashSet<EntityItem>();
		Set<String> tags = new HashSet<String>();
		tags.addAll(this.items);
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[i].length; j++) {
				elems.add(grid[i][j]);
			}
		}
		for (String tag : tags) {
			for (EntityItem item : elems) {
				if(item.getTags().contains(tag)) {
					tags.remove(tag);
					elems.remove(item);
					break;
				}
			}
		}
		return tags.isEmpty() && elems.isEmpty();
	}

	@Override
	public EntityItem result() {
		return this.result;
	}
}
