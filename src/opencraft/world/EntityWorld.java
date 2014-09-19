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

import opencraft.event.object.EventObjectSpawn;
import opencraft.lib.INamed;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventListener;
import opencraft.lib.tick.ITickable;
import opencraft.server.OpenCraftServer;
import opencraft.world.block.IBlock;
import opencraft.world.chunk.ChunkManager;
import opencraft.world.chunk.EntityChunk;
import opencraft.world.object.living.player.Player;

public abstract class EntityWorld extends Entity implements ITickable, INamed {
	
	public ChunkManager chunkManager = new ChunkManager(this);
	
	public DoubleXYZ spawnPoint;
	
	public EntityWorld() {
		OpenCraftServer.instance().getTickManager().addTick(chunkManager);
		event().addListener(new IEventListener() {

			@Override
			public Class<? extends IEvent> getEventClass() {
				return EventObjectSpawn.class;
			}

			@Override
			public IEvent handleEvent(IEvent event) {
				EventObjectSpawn e = (EventObjectSpawn) event;
				if (e.obj instanceof Player) {
					
				}
				return e;
			}
			
		});
	}
	
	public ChunkManager getChunkManager() {
		return this.chunkManager;
	}
	
	public abstract DoubleXYZ getSpawnPoint();
	public abstract void setSpawnPoint(DoubleXYZ spawn);
	
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
	
	public EntityChunk getChunkByCoord(IntXYZ coord) {
		return this.chunkManager.getChunk(new IntXYZ(coord.x /32, coord.y /32, coord.z /32));
	}
	
	public EntityChunk getChunkByCoord(DoubleXYZ coord) {
		return this.chunkManager.getChunk(new IntXYZ(((Double)Math.floor(coord.x /32)).intValue(), ((Double)Math.floor(coord.y /32)).intValue(), ((Double)Math.floor(coord.z /32)).intValue()));
	}
}
