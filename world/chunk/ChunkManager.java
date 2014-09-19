package opencraft.world.chunk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import opencraft.OpenCraft;
import opencraft.event.world.EventDecorateChunk;
import opencraft.event.world.EventGenerateChunk;
import opencraft.event.world.chunk.EventLoadChunk;
import opencraft.event.world.chunk.EventUnloadChunk;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.entity.file.EntityLoader;
import opencraft.lib.entity.storage.EntityStorageIntXYZ;
import opencraft.lib.entity.storage.EntityStorageList;
import opencraft.lib.tick.ITickable;
import opencraft.server.OpenCraftServer;
import opencraft.world.EntityWorld;

public class ChunkManager implements ITickable {
	
	private EntityWorld world;
	
	private EntityStorageIntXYZ chunks = new EntityStorageIntXYZ();
	
	private EntityStorageList loadedChunkAddresses = new EntityStorageList();
	private EntityStorageList loadingChunkAddresses = new EntityStorageList();
	
	public ChunkManager(EntityWorld world) {
		this.world = world;
	}
	
	public void loadChunk(EntityChunk chunk) {
		this.loadingChunkAddresses.add(chunk);
	}
	
	public EntityChunk getChunk(IntXYZ address) {
		return (EntityChunk) this.chunks.get(address);
	}

	@Override
	public void tick() {
		EntityStorageList loadingChunkAddressesCopy = (EntityStorageList) this.loadingChunkAddresses.copy();
		Collection<IEntity> oldChunks = this.loadedChunkAddresses.values();
		Collection<IEntity> newChunks = this.loadingChunkAddresses.values();
		newChunks.removeAll(this.loadedChunkAddresses.values());
		oldChunks.removeAll(this.loadingChunkAddresses.values());
		
		File worldDir = new File(OpenCraft.worldDir, world.getName());
		
		for (IEntity oldChunk : oldChunks) {
			EntityChunk chunk = (EntityChunk) this.chunks.get(oldChunk);
			chunk.event().emit(new EventUnloadChunk());
			OpenCraftServer.instance().getTickManager().removeTick(chunk);
			try {
				EntityLoader.saveEntity(chunk, new File(worldDir, chunk.address.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.chunks.removeValue(chunk);
		}
		for (IEntity newChunk : newChunks) {
			EntityChunk chunk;
			try {
				JSONObject json = (JSONObject) JSONValue.parse(new BufferedReader(new FileReader(new File(worldDir, ((IntXYZ)newChunk).toString()))));
				chunk = (EntityChunk) Entity.registry.getEntity(json);
			} catch (FileNotFoundException e) {
				EventGenerateChunk event1 = (EventGenerateChunk) world.event().emit(new EventGenerateChunk(new EntityChunk(this.world.getName(), (IntXYZ)newChunk)));
				EventDecorateChunk event2 = (EventDecorateChunk) world.event().emit(new EventDecorateChunk(event1.chunk));
				chunk = event2.chunk;
			}
			this.chunks.put(chunk.address, chunk);
			OpenCraftServer.instance().getTickManager().addTick(chunk);
			chunk.event().emit(new EventLoadChunk());
		}
		
		this.loadedChunkAddresses = loadingChunkAddressesCopy;
		this.loadingChunkAddresses = new EntityStorageList();
	}
}
