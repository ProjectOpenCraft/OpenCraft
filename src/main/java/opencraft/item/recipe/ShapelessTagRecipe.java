/*
 *OpenCraft - Build your own open world!
 *
 *Author : HyeonuPark(Moonrise1275)
 *
 *--------------------------------------------------------------------------
 *
 *The MIT License (MIT)
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 *
 */

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
	public EntityItem result(EntityItem[][] grid) {
		for (EntityItem[] row : grid) {
			for (EntityItem elem : row) {
				if (elem != null) {
					elem.take(1);
					if (elem.amount <= 0) {
						elem = null;
					}
				}
			}
		}
		return this.result;
	}
}
