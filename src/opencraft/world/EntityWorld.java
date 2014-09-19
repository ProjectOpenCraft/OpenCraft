/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityWorld
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world;

import opencraft.lib.INamed;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.tick.ITickable;
import opencraft.server.OpenCraftServer;
import opencraft.world.block.IBlock;
import opencraft.world.chunk.ChunkManager;
import opencraft.world.chunk.EntityChunk;

public abstract class EntityWorld extends Entity implements ITickable, INamed {
	
	public ChunkManager chunkManager = new ChunkManager(this);
	
	public EntityWorld() {
		OpenCraftServer.instance().getTickManager().addTick(chunkManager);
	}
	
	public ChunkManager getChunkManager() {
		return this.chunkManager;
	}
	
	public IBlock getBlock(IntXYZ coord) {
		IntXYZ chunkCoord = new IntXYZ(coord.x/32, coord.y/32, coord.z/32);
		IntXYZ blockCoord = new IntXYZ(coord.x%32, coord.y%32, coord.z%32);
		EntityChunk chunk = this.chunkManager.getChunk(chunkCoord);
		if (chunk == null) return null;
		return chunk.getBlock(blockCoord);
	}
	
	public boolean setBlock(IBlock block, IntXYZ coord) {
		IntXYZ chunkCoord = new IntXYZ(coord.x/32, coord.y/32, coord.z/32);
		IntXYZ blockCoord = new IntXYZ(coord.x%32, coord.y%32, coord.z%32);
		EntityChunk chunk = this.chunkManager.getChunk(chunkCoord);
		if (chunk == null) return false;
		return chunk.setBlock(block, blockCoord);
	}
}
