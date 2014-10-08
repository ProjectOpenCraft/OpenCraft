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

package opencraft.world.chunk;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import opencraft.OpenCraft;
import opencraft.event.world.chunk.EventLoadChunk;
import opencraft.event.world.chunk.EventUnloadChunk;
import opencraft.lib.tick.ITickable;
import opencraft.server.OpenCraftServer;
import opencraft.world.EntityWorld;

public class ChunkManager implements ITickable {
	
	private EntityWorld world;
	
	private Map<ChunkAddress, EntityChunk> chunks = new HashMap<ChunkAddress, EntityChunk>();
	
	private List<ChunkAddress> loadedChunkAddresses = new LinkedList<ChunkAddress>();
	private List<ChunkAddress> loadingChunkAddresses = new LinkedList<ChunkAddress>();
	
	public ChunkManager(EntityWorld world) {
		this.world = world;
	}
	
	public void loadChunk(ChunkAddress address) {
		this.loadingChunkAddresses.add(address);
	}
	
	public EntityChunk getChunk(ChunkAddress address) {
		return (EntityChunk) this.chunks.get(address);
	}
	
	public EntityChunk forceLoadChunk(ChunkAddress address) {
		this.loadChunk(address);
		if (this.chunks.get(address) == null) {
			EntityChunk chunk = OpenCraftServer.instance().getWorldManager().getChunkLoadManager().forceLoad(address);
			this.chunks.put(chunk.address, chunk);
		}
		return (EntityChunk) this.chunks.get(address);
	}
	
	void receiveChunk(EntityChunk chunk) {
		this.chunks.put(chunk.address, chunk);
		OpenCraftServer.instance().getTickManager().addTick(chunk);
		OpenCraft.log.debug("A chunk is loaded");
		chunk.event().emit(new EventLoadChunk());
	}

	@Override
	public void tick() {
		List<ChunkAddress> loadingChunkAddressesCopy = new LinkedList<ChunkAddress>();
		loadingChunkAddressesCopy.addAll(loadingChunkAddresses);
		Collection<ChunkAddress> oldChunks = this.loadedChunkAddresses;
		Collection<ChunkAddress> newChunks = this.loadingChunkAddresses;
		newChunks.removeAll(this.loadedChunkAddresses);
		oldChunks.removeAll(loadingChunkAddressesCopy);
		
		File worldDir = new File(OpenCraft.worldDir, world.getName());
		if (!worldDir.exists()) worldDir.mkdirs();
		
		for (ChunkAddress oldChunk : oldChunks) {
			EntityChunk chunk = (EntityChunk) this.chunks.get(oldChunk);
			chunk.event().emit(new EventUnloadChunk());
			OpenCraftServer.instance().getTickManager().removeTick(chunk);
			OpenCraftServer.instance().getWorldManager().getChunkLoadManager().save(chunk);
			this.chunks.remove(chunk);
			OpenCraft.log.debug("A chunk is unloaded");
		}
		for (ChunkAddress newChunk : newChunks) {
			EntityChunk chunk = OpenCraftServer.instance().getWorldManager().getChunkLoadManager().request(newChunk);
			if (chunk != null) receiveChunk(chunk);
		}
		
		this.loadedChunkAddresses = loadingChunkAddressesCopy;
		this.loadingChunkAddresses = new LinkedList<ChunkAddress>();
	}
}
