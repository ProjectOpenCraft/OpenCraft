/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * interface IBlock
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world.block;

import opencraft.lib.INamed;
import opencraft.world.EntityWorld;

public interface IBlock extends INamed {
	
	/*
	 * Return unique string-id for each blocks
	 */
	String getId();
	IBlock onChunkTick(EntityWorld world, int x, int y, int z);
	boolean isFullCube();
	
}
