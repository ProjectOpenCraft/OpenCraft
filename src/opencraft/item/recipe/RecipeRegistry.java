/*
 *OpenCraft - Build your own open world!
 *
 *Author : HyeonuPark(Moonrise1275)
 *
 *--------------------------------------------------------------------------
 *
 *The MIT License (MIT)
 *
 *Copyright (c) <year> <copyright holders>
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

import java.util.ArrayList;
import java.util.List;

import opencraft.item.EntityItem;

public class RecipeRegistry {
	static List<IRecipe> craftingRegistry = new ArrayList<IRecipe>();
	static List<IRecipe> grindingRegistry = new ArrayList<IRecipe>();
	static List<IRecipe> smeltingRegistry = new ArrayList<IRecipe>();
	static List<IRecipe> alloyRegistry = new ArrayList<IRecipe>();
	
	public static void registerCrafting(IRecipe recipe) {
		craftingRegistry.add(recipe);
	}
	
	public static boolean removeCrafting(IRecipe recipe) {
		return craftingRegistry.remove(recipe);
	}
	
	public static void registerGrinding(IRecipe recipe) {
		grindingRegistry.add(recipe);
	}
	
	public static boolean removeGrinding(IRecipe recipe) {
		return grindingRegistry.remove(recipe);
	}
	
	public static void registerSmelting(IRecipe recipe) {
		smeltingRegistry.add(recipe);
	}
	
	public static boolean removeSmelting(IRecipe recipe) {
		return smeltingRegistry.remove(recipe);
	}
	
	public static void registerAlloy(IRecipe recipe) {
		alloyRegistry.add(recipe);
	}
	
	public static boolean removeAlloy(IRecipe recipe) {
		return alloyRegistry.remove(recipe);
	}
	
	public static EntityItem getCraftingResult(EntityItem[][] grid) {
		for (IRecipe recipe : craftingRegistry) {
			if (recipe.match(grid)) return recipe.result();
		}
		return null;
	}
	
	public static EntityItem getGrindingResult(EntityItem[][] grid) {
		for (IRecipe recipe : grindingRegistry) {
			if (recipe.match(grid)) return recipe.result();
		}
		return null;
	}
	
	public static EntityItem getSmeltingResult(EntityItem[][] grid) {
		for (IRecipe recipe : smeltingRegistry) {
			if (recipe.match(grid)) return recipe.result();
		}
		return null;
	}
	
	public static EntityItem getAlloyResult(EntityItem[][] grid) {
		for (IRecipe recipe : alloyRegistry) {
			if (recipe.match(grid)) return recipe.result();
		}
		return null;
	}
}
