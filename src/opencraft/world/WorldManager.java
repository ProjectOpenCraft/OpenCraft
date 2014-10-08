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

package opencraft.world;

import java.io.File;

import opencraft.OpenCraft;
import opencraft.lib.entity.storage.EntityStorageString;
import opencraft.server.OpenCraftServer;
import opencraft.world.chunk.ChunkLoadManager;

public class WorldManager {
	
	private EntityStorageString worlds = new EntityStorageString();
	private ChunkLoadManager chunkLoadManager;
	
	public WorldManager(int chunkLoadPerTick, double chunkSaveDelay) {
		this.chunkLoadManager = new ChunkLoadManager(chunkLoadPerTick, chunkSaveDelay);
		OpenCraftServer.instance().getTickManager().addTick(chunkLoadManager);
	}
	
	public ChunkLoadManager getChunkLoadManager() {
		return this.chunkLoadManager;
	}
	
	public EntityWorld getWorld() {
		if (worlds.values().size() < 1) return null;
		return (EntityWorld) worlds.values().toArray()[0];
	}
	
	public EntityWorld getWorld(String name) {
		return (EntityWorld) this.worlds.get(name);
	}
	
	public void registerWorld(EntityWorld world) {
		OpenCraft.log.info("Registering world " + world.getName());
		File worldFolder = new File(OpenCraft.worldDir, world.getName());
		if (!worldFolder.exists()) OpenCraft.log.debug("Making world folder? " + worldFolder.mkdirs()); 
		this.worlds.put(world.getName(), world);
		OpenCraftServer.instance().getTickManager().addTick(world);
	}
}
