/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class WorldManager
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world;

import opencraft.lib.entity.storage.EntityStorageString;
import opencraft.server.OpenCraftServer;

public class WorldManager {
	
	private EntityStorageString worlds = new EntityStorageString();
	
	public EntityWorld getWorld(String name) {
		return (EntityWorld) this.worlds.get(name);
	}
	
	public void registerWorld(EntityWorld world) {
		this.worlds.put(world.getName(), world);
		OpenCraftServer.instance().getTickManager().addTick(world);
	}
}
