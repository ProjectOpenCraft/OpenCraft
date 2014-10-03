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
