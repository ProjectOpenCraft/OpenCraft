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
		if (grid.length != 1 || grid[0].length != 1) return false;
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
	public EntityItem result(EntityItem[][] grid) {
		grid[0][0].take(1);
		if (grid[0][0].amount <= 0) grid[0][0] = null;
		return output;
	}

}
