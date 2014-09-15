/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
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
import opencraft.lib.tick.ITickable;
import opencraft.server.OpenCraftServer;
import opencraft.world.chunk.ChunkManager;

public abstract class EntityWorld extends Entity implements ITickable, INamed {
	
	static {
		ENTITY_ID = "world|OpenCraft|base";
	}
	public ChunkManager chunkManager = new ChunkManager(this);
	
	public EntityWorld() {
		OpenCraftServer.instance().getTickManager().addTick(chunkManager);
	}
}
