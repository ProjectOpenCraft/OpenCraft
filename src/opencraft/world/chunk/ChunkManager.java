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
