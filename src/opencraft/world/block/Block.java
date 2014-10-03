/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class Block
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world.block;

import opencraft.world.EntityWorld;

public abstract class Block implements IBlock {
	
	public static BlockRegistry registry = new BlockRegistry();
	
	public IBlock onChunkTick(EntityWorld world, int x, int y, int z) {
		return this;
	}
	
	public boolean isAir() {
		return false;
	}
	
	public boolean isTransparent() {
		return false;
	}
	
	public boolean isReplaceable() {
		return false;
	}
	
	public boolean isFluid() {
		return false;
	}
}
