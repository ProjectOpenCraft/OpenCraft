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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.google.gson.Gson;

import opencraft.OpenCraft;
import opencraft.event.world.EventDecorateChunk;
import opencraft.event.world.EventGenerateChunk;
import opencraft.lib.entity.file.EntityLoader;
import opencraft.lib.tick.ITickable;
import opencraft.server.OpenCraftServer;
import opencraft.world.EntityWorld;

public class ChunkLoadManager implements ITickable {
	
	public static Gson gson = new Gson();
	
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
			chunk = gson.fromJson(new BufferedReader(new FileReader(new File(new File(OpenCraft.worldDir, req.world), req.coord.toString()))), EntityChunk.class);
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
