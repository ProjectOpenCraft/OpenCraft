package opencraft.world.chunk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import opencraft.OpenCraft;
import opencraft.event.world.EventDecorateChunk;
import opencraft.event.world.EventGenerateChunk;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.file.EntityLoader;
import opencraft.lib.tick.ITickable;
import opencraft.server.OpenCraftServer;
import opencraft.world.EntityWorld;

public class ChunkLoadManager implements ITickable {
	
	private int chunkLoadPerTick;
	private double chunkSaveDelay;
	
	private Queue<ChunkAddress> requests = new LinkedList<ChunkAddress>();
	private Queue<EntityChunk> buffer = new LinkedList<EntityChunk>();
	
	public ChunkLoadManager(int chunkLoadPerTick, double chunkSaveDelay) {
		this.chunkLoadPerTick = chunkLoadPerTick;
		this.chunkSaveDelay = chunkSaveDelay;
	}
	
	public EntityChunk request(ChunkAddress req) {
		EntityChunk chunk = search(req);
		if (chunk == null)
			this.requests.add(req);
		return chunk;
	}
	
	public EntityChunk forceLoad(ChunkAddress req) {
		EntityChunk chunk = search(req);
		if (chunk != null) return chunk;
		else return loadChunk(req);
	}
	
	public void save(EntityChunk chunk) {
		this.buffer.add(chunk);
	}
	
	private EntityChunk search(ChunkAddress req) {
		for (EntityChunk chunk : buffer) {
			if (chunk.address.equals(req)) return chunk;
		}
		return null;
	}
	
	private EntityChunk loadChunk(ChunkAddress req) {
		EntityChunk chunk = null;
		try {
			JSONObject json = (JSONObject) JSONValue.parse(new BufferedReader(new FileReader(new File(new File(OpenCraft.worldDir, req.world), req.coord.toString()))));
			chunk = (EntityChunk) Entity.registry.getEntity(json);
		} catch (FileNotFoundException e) {
			EntityWorld world = OpenCraftServer.instance().getWorldManager().getWorld(req.world);
			EventGenerateChunk event1 = (EventGenerateChunk) world.event().emit(new EventGenerateChunk(new EntityChunk(req)));
			EventDecorateChunk event2 = (EventDecorateChunk) world.event().emit(new EventDecorateChunk(event1.chunk));
			chunk = event2.chunk;
		}
		return chunk;
	}
	
	public void tick() {
		for (int i=0; i<this.chunkLoadPerTick; i++) {
			if (this.requests.isEmpty()) break;
			ChunkAddress req = this.requests.poll();
			EntityChunk chunk = search(req);
			if (chunk == null) {
				chunk = loadChunk(req);
			}
			if (chunk != null)
				OpenCraftServer.instance().getWorldManager().getWorld(req.world).getChunkManager().receiveChunk(chunk);
		}
		for (int i=0; i<(this.buffer.size()/this.chunkSaveDelay) +1; i++) {
			EntityChunk chunk = this.buffer.poll();
			try {
				EntityLoader.saveEntity(chunk, new File(new File(OpenCraft.worldDir, chunk.address.world), chunk.address.coord.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
