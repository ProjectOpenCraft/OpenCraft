package opencraft.item.recipe;

import opencraft.item.EntityItem;

public class ShapedTagRecipe implements IRecipe {
	
	String[][] grid;
	EntityItem result;
	
	public ShapedTagRecipe(int rows, int columns, EntityItem result, String... tags) {
		
		this.grid = new String[rows][columns];
		this.result = result;
		if (tags.length != rows * columns)
			throw new RuntimeException("Recipe's size is not matching with rows and columns!");
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				this.grid[i][j] = tags[i*columns+j];
			}
		}
	}

	@SuppressWarnings("unused")
	@Override
	public boolean match(EntityItem[][] grid) {
		if (this.grid.length > grid.length || this.grid[0].length > grid[0].length) return false;
		for (int r=this.grid.length; r<=grid.length; r++) {
			for (int c=this.grid[0].length; c<=grid[r].length;++c) {
				EntityItem[][] subgrid = new EntityItem[this.grid.length][this.grid[0].length];
				for (int p=0; p<this.grid.length; p++) {
					for (int q=0; q<this.grid[p].length; q++) {
						subgrid[p][q] = grid[r - this.grid.length + p][c - this.grid[0].length + q];
					}
				}
				bp:
				for (int i=0; i<subgrid.length; i++) {
					for (int j=0; j<subgrid[i].length; j++) {
						if (subgrid[i][j] == null) {
							if (this.grid[i][j] != null) break bp;
						} else if (!subgrid[i][j].getTags().contains(this.grid[i][j])) {
							break bp;
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public EntityItem result() {
		return this.result;
	}
}
